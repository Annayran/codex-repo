package br.com.zaya.skala;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.zaya.skala.particionamento.config.ParticionamentoConfig;
import br.com.zaya.skala.particionamento.model.Carreta;
import br.com.zaya.skala.particionamento.model.ItemPedido;
import br.com.zaya.skala.particionamento.model.ParticionamentoItemConfig;
import br.com.zaya.skala.particionamento.model.ParticionamentoRequest;
import br.com.zaya.skala.particionamento.model.PedidoInfo;
import br.com.zaya.skala.particionamento.sankhya.OEGenerator;
import br.com.zaya.skala.particionamento.sankhya.PedidoRepository;
import br.com.zaya.skala.particionamento.sankhya.RastreabilidadeRepository;
import br.com.zaya.skala.particionamento.service.ParticionamentoRequestParser;
import br.com.zaya.skala.particionamento.service.ParticionamentoService;
import br.com.zaya.skala.particionamento.util.NumericUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicReference;

public class ParticionarPedidosAction implements AcaoRotinaJava {
    private final ParticionamentoService particionamentoService = new ParticionamentoService();
    private final ParticionamentoRequestParser requestParser = new ParticionamentoRequestParser();
    private final PedidoRepository pedidoRepository = new PedidoRepository();
    private final OEGenerator oeGenerator = new OEGenerator();
    private final RastreabilidadeRepository rastreabilidadeRepository = new RastreabilidadeRepository();

    @Override
    public void doAction(ContextoAcao ctx) throws Exception {
        ParticionamentoRequest request = obterRequestModal(ctx);
        List<Integer> nuNotas = obterNuNotasParaProcessar(ctx, request);

        double capacidade = request.getCapacidadePaletes() > 0 ? request.getCapacidadePaletes() : obterCapacidade(ctx);
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade informada invalida. Informe um valor maior que zero.");
        }

        final Map<Integer, PedidoInfo> pedidos = new LinkedHashMap<Integer, PedidoInfo>();
        final Map<Integer, List<Carreta>> carretasPorNota = new LinkedHashMap<Integer, List<Carreta>>();

        for (Integer nuNota : nuNotas) {
            PedidoInfo pedidoInfo = pedidoRepository.validarPedido(nuNota);
            validarPreCondicoes(pedidoInfo);

            List<ItemPedido> itens = pedidoRepository.lerItensPedido(nuNota);
            if (itens.isEmpty()) {
                throw new IllegalStateException("Nenhum item valido encontrado no pedido " + nuNota + ".");
            }

            aplicarConfiguracaoModal(itens, request, nuNota);

            List<Carreta> carretas = particionarConformeModo(itens, capacidade, request);

            if (carretas.isEmpty()) {
                throw new IllegalStateException("Nenhuma OE gerada para o pedido " + nuNota + ". Verifique os itens selecionados e as quantidades.");
            }

            aplicarAgendamentos(carretas, request, nuNota);

            pedidos.put(nuNota, pedidoInfo);
            carretasPorNota.put(nuNota, carretas);
        }

        final AtomicReference<Map<Integer, List<Integer>>> nuNotasGeradas = new AtomicReference<Map<Integer, List<Integer>>>();
        final AtomicReference<Map<Integer, Integer>> idsPartGerados = new AtomicReference<Map<Integer, Integer>>();
        final int codUsuCriacao = ctx.getUsuarioLogado().intValue();
        final double capacidadeFinal = capacidade;

        JapeSession.execEnsuringTX(new JapeSession.TXBlock() {
            @Override
            public void doWithTx() throws Exception {
                Map<Integer, List<Integer>> notasGeradas = new LinkedHashMap<Integer, List<Integer>>();
                Map<Integer, Integer> idsGerados = new LinkedHashMap<Integer, Integer>();

                for (Map.Entry<Integer, PedidoInfo> entry : pedidos.entrySet()) {
                    int nuNota = entry.getKey();
                    List<Carreta> carretas = carretasPorNota.get(nuNota);

                    pedidoRepository.bloquearPedidoOrigem(nuNota);

                    PedidoInfo pedidoInfoAtualizado = pedidoRepository.validarPedido(nuNota);
                    validarPreCondicoes(pedidoInfoAtualizado);

                    List<Integer> oesGeradas = oeGenerator.gerarOEs(nuNota, carretas);
                    int idPart = rastreabilidadeRepository.registrarParticionamento(
                            pedidoInfoAtualizado,
                            capacidadeFinal,
                            carretas,
                            oesGeradas,
                            codUsuCriacao
                    );
                    pedidoRepository.marcarPedidoComoParticionado(nuNota);

                    notasGeradas.put(nuNota, oesGeradas);
                    idsGerados.put(nuNota, idPart);
                }

                nuNotasGeradas.set(notasGeradas);
                idsPartGerados.set(idsGerados);
            }
        });

        ctx.setMensagemRetorno(
                montarMensagemRetorno(nuNotasGeradas.get(), idsPartGerados.get(), request)
        );
    }

    private ParticionamentoRequest obterRequestModal(ContextoAcao ctx) throws Exception {
        Object payload = ctx.getParam("PART_PAYLOAD");
        if (payload == null) {
            payload = ctx.getParam("PAYLOAD_PARTICIONAMENTO");
        }
        if (payload == null) {
            payload = ctx.getParam("PAYLOAD");
        }
        return requestParser.parse(payload == null ? null : String.valueOf(payload));
    }

    private List<Carreta> particionarConformeModo(
            List<ItemPedido> itens,
            double capacidade,
            ParticionamentoRequest request
    ) {
        if (!request.temConfiguracaoItens()) {
            return particionamentoService.particionar(itens, capacidade);
        }

        if ("PLANILHA_ATUAL".equalsIgnoreCase(request.getModoParticionamento())) {
            return particionamentoService.particionarSelecionadosRegraOriginal(itens, capacidade);
        }

        return particionamentoService.particionarComConfiguracao(itens, capacidade, request.getModoParticionamento());
    }

    private List<Integer> obterNuNotasParaProcessar(ContextoAcao ctx, ParticionamentoRequest request) throws Exception {
        List<Integer> nuNotas = new ArrayList<Integer>();
        for (Integer nuNota : request.getNuNotas()) {
            if (nuNota != null && nuNota > 0 && !nuNotas.contains(nuNota)) {
                nuNotas.add(nuNota);
            }
        }

        Registro[] linhas = ctx.getLinhas();
        if (linhas != null) {
            for (Registro linha : linhas) {
                Object nuNota = linha.getCampo("NUNOTA");
                int valor = (int) NumericUtils.toDouble(nuNota);
                if (valor > 0 && !nuNotas.contains(valor)) {
                    nuNotas.add(valor);
                }
            }
        }

        if (nuNotas.isEmpty()) {
            throw new IllegalStateException("Selecione ao menos um pedido para executar o particionamento.");
        }

        return nuNotas;
    }

    private void aplicarConfiguracaoModal(List<ItemPedido> itens, ParticionamentoRequest request, int nuNota) {
        if (!request.temConfiguracaoItens()) {
            return;
        }

        for (ItemPedido item : itens) {
            ParticionamentoItemConfig config = localizarConfig(request, nuNota, item);
            if (config == null) {
                item.setSelecionado(false);
                item.setPriorizado(false);
                item.setOrdemPrioridade(0);
                continue;
            }

            item.setSelecionado(config.isSelecionado());
            item.setPriorizado(config.isPriorizado());
            item.setOrdemPrioridade(config.getOrdemPrioridade());
            item.setGrupoMix(config.getGrupo());
            if (item.isPriorizado() && item.getOrdemPrioridade() <= 0) {
                item.setOrdemPrioridade(9999);
            }
        }
    }

    private ParticionamentoItemConfig localizarConfig(ParticionamentoRequest request, int nuNota, ItemPedido item) {
        ParticionamentoItemConfig porSequencia = null;
        ParticionamentoItemConfig porProduto = null;

        for (ParticionamentoItemConfig config : request.getItens()) {
            if (config.getNuNota() > 0 && config.getNuNota() != nuNota) {
                continue;
            }

            boolean mesmoProduto = config.getCodProd() == null
                    || config.getCodProd().trim().isEmpty()
                    || config.getCodProd().trim().equals(item.getCodProd());

            if (config.getSequencia() > 0 && config.getSequencia() == item.getSequenciaOrigem() && mesmoProduto) {
                porSequencia = config;
                break;
            }

            if (mesmoProduto && config.getSequencia() <= 0) {
                porProduto = config;
            }
        }

        return porSequencia != null ? porSequencia : porProduto;
    }

    private String montarMensagemRetorno(
            Map<Integer, List<Integer>> nuNotasGeradas,
            Map<Integer, Integer> idsPartGerados,
            ParticionamentoRequest request
    ) {
        int totalOes = 0;
        StringBuilder msg = new StringBuilder();
        msg.append("Particionamento concluido com sucesso!\n");
        if (request.isOrigemModal()) {
            msg.append("Configuracao aplicada: selecao/mix/prioridade/grupo recebidos do modal HTML5.\n");
            msg.append("Modo de particionamento: ").append(descreverModo(request.getModoParticionamento())).append(".\n");
        }

        for (Map.Entry<Integer, List<Integer>> entry : nuNotasGeradas.entrySet()) {
            List<Integer> oes = entry.getValue();
            totalOes += oes == null ? 0 : oes.size();
            msg.append("Pedido origem ").append(entry.getKey())
                    .append(" -> OEs geradas: ").append(oes)
                    .append(" | Rastro: ").append(idsPartGerados.get(entry.getKey()))
                    .append("\n");
        }

        msg.append("Total de OE(s) gerada(s): ").append(totalOes);
        return msg.toString();
    }

    private String descreverModo(String modo) {
        if ("PLANILHA_ATUAL".equalsIgnoreCase(modo)) {
            return "regra original de paletes inteiros e quebras";
        }
        if ("PRIORIDADE".equalsIgnoreCase(modo)) {
            return "mix por SKU com prioridade definida";
        }
        if ("GRUPO_PERCENTUAL".equalsIgnoreCase(modo)) {
            return "mix por SKU";
        }
        return "padrao da rotina";
    }

    private int obterNuNotaSelecionada(ContextoAcao ctx) throws Exception {
        Registro[] linhas = ctx.getLinhas();
        if (linhas == null || linhas.length == 0) {
            throw new IllegalStateException("Selecione um pedido para executar o particionamento.");
        }
        if (linhas.length > 1) {
            throw new IllegalStateException("Execute o particionamento em apenas um pedido por vez.");
        }

        Object nuNota = linhas[0].getCampo("NUNOTA");
        int valor = (int) NumericUtils.toDouble(nuNota);
        if (valor <= 0) {
            throw new IllegalStateException("Nao foi possivel identificar o NUNOTA do pedido selecionado.");
        }
        return valor;
    }

    private double obterCapacidade(ContextoAcao ctx) {
        Object parametro = ctx.getParam("QTDE_PALETES");
        if (parametro == null) {
            parametro = ctx.getParam("CAPACIDADE");
        }
        double capacidade = NumericUtils.toDouble(parametro);
        return capacidade > 0 ? capacidade : ParticionamentoConfig.CAPACIDADE_PADRAO;
    }

    private void validarPreCondicoes(PedidoInfo pedidoInfo) {
        if (!"L".equalsIgnoreCase(nullToEmpty(pedidoInfo.getStatusNota()))) {
            throw new IllegalStateException("Somente pedidos liberados podem ser particionados. O pedido " + pedidoInfo.getNuNota() + " deve estar com STATUSNOTA = 'L'.");
        }

        if (!ParticionamentoConfig.VALOR_SIM.equalsIgnoreCase(nullToEmpty(pedidoInfo.getAdPermitePart()))) {
            throw new IllegalStateException("A TOP deste pedido nao permite particionamento.");
        }

        if (ParticionamentoConfig.VALOR_SIM.equalsIgnoreCase(nullToEmpty(pedidoInfo.getAdParticionado()))) {
            throw new IllegalStateException("Este pedido ja foi particionado anteriormente.");
        }
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    private void aplicarAgendamentos(List<Carreta> carretas, ParticionamentoRequest request, int nuNotaOrigem) {
        for (Carreta carreta : carretas) {
            String textoAgendamento = request.getDhAgendamentoOe(nuNotaOrigem, carreta.getNumero());
            Timestamp dhAgendamento = parseDhAgendamento(textoAgendamento, nuNotaOrigem, carreta.getNumero());
            carreta.setDhAgendamento(dhAgendamento);
        }
    }

    private Timestamp parseDhAgendamento(String value, int nuNotaOrigem, int numeroOe) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Informe o agendamento da OEP " + numeroOe + " do pedido " + nuNotaOrigem + ".");
        }

        String normalized = value.trim().replace('T', ' ');
        if (normalized.length() == 16) {
            normalized = normalized + ":00";
        }
        if (normalized.length() == 10) {
            normalized = normalized + " 00:00:00";
        }

        try {
            return Timestamp.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "Agendamento invalido para a OEP " + numeroOe + " do pedido " + nuNotaOrigem
                            + ". Use data e hora validas.",
                    e
            );
        }
    }
}
