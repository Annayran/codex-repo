package br.com.zaya.belmicro.rateio.sankhya;

import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import java.util.Map;

public interface OperacoesAcaoRateio {
    Map<String,Object> abrirContexto(OrigemCanonica referencia)throws Exception;
    Map<String,Object> simular(OrigemCanonica referencia,EntradaSimulacao entrada,String chave)throws Exception;
    Map<String,Object> recalcular(OrigemCanonica referencia,long id,int revisao,EntradaSimulacao entrada,String motivo,String chave)throws Exception;
    Map<String,Object> efetivar(OrigemCanonica referencia,long id,int revisao,String hash,String chave)throws Exception;
}
