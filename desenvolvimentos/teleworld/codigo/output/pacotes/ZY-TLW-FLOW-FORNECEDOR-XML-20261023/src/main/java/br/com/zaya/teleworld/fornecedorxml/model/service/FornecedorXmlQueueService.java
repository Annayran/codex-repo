package br.com.zaya.teleworld.fornecedorxml.model.service;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.zaya.teleworld.fornecedorxml.model.dto.FornecedorXmlData;
import br.com.zaya.teleworld.fornecedorxml.model.repository.FlowFornecedorRepository;
import br.com.zaya.teleworld.fornecedorxml.shared.config.FornecedorXmlConfig;
import br.com.zaya.teleworld.fornecedorxml.shared.utils.DocumentoUtils;

public class FornecedorXmlQueueService {
    private final ImportacaoXmlExtractor extractor = new ImportacaoXmlExtractor();
    private final FlowFornecedorRepository repository = new FlowFornecedorRepository();

    public void avaliar(DynamicVO vo) throws Exception {
        FornecedorXmlConfig config = FornecedorXmlConfig.carregar();
        FornecedorXmlData data = extractor.extrair(vo);
        if (data.getCodParc() != null
                || !config.aceitaStatus(data.getStatusXml())
                || !config.aceitaOrigem(data.getOrigem())) {
            return;
        }
        if (data.getNuArquivo() == null || data.getCodEmp() == null
                || data.getChaveAcesso() == null || data.getCnpjCpf() == null) {
            System.out.println("[Teleworld][FornecedorXmlEvent] Registro ainda incompleto. NUARQUIVO="
                    + data.getNuArquivo());
            return;
        }
        if (data.getCnpjCpf().length() != 11 && data.getCnpjCpf().length() != 14) {
            System.out.println("[Teleworld][FornecedorXmlEvent] Documento invalido ignorado: "
                    + DocumentoUtils.mascarar(data.getCnpjCpf()));
            return;
        }
        if (!repository.parceiroExiste(data.getCnpjCpf())) {
            repository.enfileirar(data, config.getCodPrn());
            System.out.println("[Teleworld][FornecedorXmlEvent] Pendencia registrada para "
                    + DocumentoUtils.mascarar(data.getCnpjCpf()));
        }
    }
}
