package br.com.zaya.apontamentoProducao.scheduleActions;

import br.com.zaya.polyvox.apontamentoproducao.sankhya.schedulers.ApontamentoProducaoScheduler;

/**
 * Adaptador de compatibilidade para o FQCN historicamente cadastrado no Sankhya.
 *
 * <p>Novos cadastros devem usar
 * {@code br.com.zaya.polyvox.apontamentoproducao.sankhya.schedulers.ApontamentoProducaoScheduler}.
 * A remocao deste adaptador depende da validacao dos cadastros no ambiente Sankhya.</p>
 */
@Deprecated
public class ApontamentoProducaoSchedule extends ApontamentoProducaoScheduler {
    public ApontamentoProducaoSchedule() {
        super();
    }
}
