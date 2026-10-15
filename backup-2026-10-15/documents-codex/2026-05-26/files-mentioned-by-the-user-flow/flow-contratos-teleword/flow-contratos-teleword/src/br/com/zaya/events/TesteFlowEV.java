package br.com.zaya.events;

import br.com.sankhya.extensions.flow.ContextoEvento;
import br.com.sankhya.extensions.flow.EventoProcessoJava;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.util.Collection;

public class TesteFlowEV implements EventoProcessoJava {
    private final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    private JdbcWrapper jdbc = null;
    @Override
    public void executar(ContextoEvento contexto) throws Exception {

        Object idProcesso = contexto.getIdInstanceProcesso();
        Object idTarefa = contexto.getIdInstanceTarefa();
        String nomeTarefa = String.valueOf(contexto.getCampo("IDTAREFA"));
        String tarefa = "UserTask_0mzjwup";

        Collection<DynamicVO> apvVO = JapeFactory.dao("AD_TGFCABAPV").find("this.IDINSTPRN = ? AND IDTAREFA = ?", idProcesso, tarefa);

        if( apvVO.isEmpty() ){
            throw new Exception("<br><br>O registro de aprovação/desaprovação da tarefa não foi inserido.<br>Para prosseguir preencha os dados pertinentes na aba Aprovação.<br><br>");
        }
    }
}
