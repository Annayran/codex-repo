package br.com.zaya.belmicro.rateio.acao;

import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import br.com.zaya.belmicro.rateio.sankhya.*;
import java.util.*;

/** Backend provider only. Does not load or open any HTML5 component. */
class RuntimeAcoesRateio {
    long usuario(){return ComandosSankhyaRateio.usuarioAtual();}
    OperacoesAcaoRateio operacoes(){
        return new ComandosSankhyaRateio(escolher(
                ServiceLoader.load(ConfiguracaoServicosRateio.class,ConfiguracaoServicosRateio.class.getClassLoader()),
                ServiceLoader.load(ConfiguracaoRateio.class,ConfiguracaoRateio.class.getClassLoader())));
    }
    static ConfiguracaoServicosRateio escolher(Iterable<ConfiguracaoServicosRateio> atuais,Iterable<ConfiguracaoRateio> legadas){
        ConfiguracaoServicosRateio configuracao=null,padrao=null;
        for(ConfiguracaoServicosRateio candidata:atuais){
            if(candidata instanceof ConfiguracaoServicosBelmicro){padrao=candidata;continue;}
            if(configuracao!=null)throw new ErroRateio("REGRA_CONFLITANTE","Mais de uma configuracao de servicos de rateio.");configuracao=candidata;
        }
        // A deployed custom/legacy provider takes precedence over the closed bundled fallback.
        if(configuracao==null){
            for(ConfiguracaoRateio candidata:legadas){
                if(configuracao!=null)throw new ErroRateio("REGRA_CONFLITANTE","Mais de uma configuracao de servicos de rateio.");configuracao=candidata;
            }
        }
        // Some module loaders expose classes without META-INF/services resources.
        if(configuracao==null)configuracao=padrao!=null?padrao:new ConfiguracaoServicosBelmicro();
        return configuracao;
    }
}
