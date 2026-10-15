package ReservaEstoque.service;

import ReservaEstoque.repository.ReservaRepository;
import br.com.sankhya.jape.vo.DynamicVO;

import java.math.BigDecimal;

public class ReservaEstoqueService {

    private final ReservaRepository reservaRepository = new ReservaRepository();

    public void atualizarReservado(DynamicVO vo) throws Exception {
        System.out.println("[ReservaEstoqueService] Iniciando atualização de reservado...");

        BigDecimal codProd = vo.asBigDecimal("CODPROD");
        if (codProd == null) {
            System.out.println("[ReservaEstoqueService] CODPROD nulo. Nenhuma ação realizada.");
            return;
        }

        System.out.println("[ReservaEstoqueService] Produto identificado: CODPROD=" + codProd);

        // Buscar último registro na TCSPRO
        ReservaRepository.TcProDados dados = reservaRepository.buscarUltimoRegistroTcPro(codProd);

        if (dados == null) {
            System.out.println("[ReservaEstoqueService] Nenhum registro encontrado na TCSPRO para CODPROD=" + codProd);
            return;
        }

        if (dados.getQuantidade() == null) {
            System.out.println("[ReservaEstoqueService] Quantidade nula no último registro da TCSPRO. Nenhuma ação realizada.");
            return;
        }

        System.out.println("[ReservaEstoqueService] Último registro TCSPRO encontrado → QUANTIDADE="
                + dados.getQuantidade() + ", CODLOCAL=" + dados.getCodLocal());

        // Incrementar direto o reservado na TGFEST
        reservaRepository.incrementarReservadoTgfEst(
                codProd,
                dados.getCodLocal(),
                dados.getQuantidade(),
                dados.getCodEmp()
        );

        System.out.println("[ReservaEstoqueService] Atualização concluída → Reservado incrementado em "
                + dados.getQuantidade() + " para CODPROD=" + codProd
                + " no CODLOCAL=" + dados.getCodLocal());
    }

    public void decrementarReservado(DynamicVO vo) throws Exception {
        System.out.println("[decrementarReservado] Iniciando atualização de reservado...");

        BigDecimal codProd = vo.asBigDecimal("CODPROD");
        if (codProd == null) {
            System.out.println("[decrementarReservado] CODPROD nulo. Nenhuma ação realizada.");
            return;
        }

        System.out.println("[decrementarReservado] Produto identificado: CODPROD=" + codProd);

        // Buscar último registro na TCSPRO
        ReservaRepository.TcProDados dados = reservaRepository.buscarUltimoRegistroTcPro(codProd);

        if (dados == null) {
            System.out.println("[ReservaEstoqueService] Nenhum registro encontrado na TCSPRO para CODPROD=" + codProd);
            return;
        }

        if (dados.getQuantidade() == null) {
            System.out.println("[ReservaEstoqueService] Quantidade nula no último registro da TCSPRO. Nenhuma ação realizada.");
            return;
        }

        System.out.println("[ReservaEstoqueService] Último registro TCSPRO encontrado → QUANTIDADE="
                + dados.getQuantidade() + ", CODLOCAL=" + dados.getCodLocal());

        // Incrementar direto o reservado na TGFEST
        reservaRepository.decrementarReservadoTgfEst(
                codProd,
                dados.getCodLocal(),
                dados.getQuantidade(),
                dados.getCodEmp()
        );

        System.out.println("[ReservaEstoqueService] Atualização concluída → Reservado incrementado em "
                + dados.getQuantidade() + " para CODPROD=" + codProd
                + " no CODLOCAL=" + dados.getCodLocal());
    }


}

