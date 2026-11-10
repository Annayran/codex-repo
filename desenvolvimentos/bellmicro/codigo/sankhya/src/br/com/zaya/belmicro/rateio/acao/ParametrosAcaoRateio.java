package br.com.zaya.belmicro.rateio.acao;

import br.com.zaya.belmicro.rateio.aplicacao.dto.EntradaSimulacao;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import com.google.gson.*;
import java.math.BigDecimal;
import java.util.*;

/** Strict action payload; no user, base, competence, headers or final amounts accepted. */
final class ParametrosAcaoRateio {
    private final JsonObject json;
    ParametrosAcaoRateio(Object raw){
        if(!(raw instanceof String)||((String)raw).trim().isEmpty()||((String)raw).length()>1048576)throw invalida("Informe RATEIO_JSON pelo dashboard.");
        String texto=(String)raw;int nivel=0;boolean string=false,escape=false;
        for(int i=0;i<texto.length();i++){
            char c=texto.charAt(i);if(string){if(escape)escape=false;else if(c=='\\')escape=true;else if(c=='"')string=false;}
            else if(c=='"')string=true;else if(c=='{'||c=='['){if(++nivel>32)throw invalida("Estrutura de entrada excessiva.");}else if(c=='}'||c==']')nivel--;
        }
        try{JsonElement elemento=new JsonParser().parse(texto);if(!elemento.isJsonObject())throw invalida("Entrada deve ser objeto JSON.");json=elemento.getAsJsonObject();}
        catch(JsonParseException|IllegalStateException e){throw invalida("RATEIO_JSON invalido.");}
    }
    boolean consultar(){return json.has("modo")&&"CONSULTAR".equals(texto("modo",20));}
    void campos(String... nomes){campos(json,nomes);}
    void exigirModoSimular(){if(json.has("modo")&&!"SIMULAR".equals(texto("modo",20)))throw invalida("Modo invalido para Simular.");}
    String chave(){return texto("solicitacaoId",80);}
    String motivo(){return texto("motivo",255);}
    String hash(){String h=texto("hashEsperado",64);if(!h.matches("[0-9a-f]{64}"))throw invalida("Hash esperado invalido.");return h;}
    long simulacao(){return inteiro(json.get("simulacaoId"));}
    int revisao(){long r=inteiro(json.get("revisaoEsperada"));if(r>Integer.MAX_VALUE)throw invalida("Revisao fora do intervalo.");return(int)r;}
    EntradaSimulacao entrada(){
        JsonElement elemento=json.get("entrada");if(elemento==null||!elemento.isJsonObject())throw invalida("Informe a composicao ou criterio.");
        JsonObject e=elemento.getAsJsonObject();String modalidade=texto(e,"modalidade",10);
        if("CRITERIO".equals(modalidade)){
            campos(e,"modalidade","criterioId","criterioVersao");long versao=inteiro(e.get("criterioVersao"));
            if(versao>Integer.MAX_VALUE)throw invalida("Versao fora do intervalo.");
            return EntradaSimulacao.criterio(texto(e,"criterioId",80),(int)versao);
        }
        if(!"MANUAL".equals(modalidade))throw invalida("Modalidade invalida.");campos(e,"modalidade","percentuais");
        JsonElement lista=e.get("percentuais");if(lista==null||!lista.isJsonArray()||lista.getAsJsonArray().size()>100000)throw invalida("Composicao invalida.");
        List<EntradaSimulacao.Percentual> percentuais=new ArrayList<EntradaSimulacao.Percentual>();
        int numeroLinha=0;
        for(JsonElement linha:lista.getAsJsonArray()){
            numeroLinha++;
            if(!linha.isJsonObject())throw invalida("Destino invalido.");JsonObject p=linha.getAsJsonObject();campos(p,"codEmp","codCencus","percentual");
            long emp=inteiro(p.get("codEmp")),cr=inteiro(p.get("codCencus"));
            if(emp>Integer.MAX_VALUE||cr>Integer.MAX_VALUE)throw invalida("Empresa ou CR fora do intervalo.");
            JsonElement campoPercentual=p.get("percentual");
            if(campoPercentual==null||campoPercentual.isJsonNull())throw percentual(numeroLinha,"AUSENTE");
            if(!campoPercentual.isJsonPrimitive()||(!campoPercentual.getAsJsonPrimitive().isString()&&!campoPercentual.getAsJsonPrimitive().isNumber()))throw percentual(numeroLinha,"FORMATO");
            String valor=escalar(campoPercentual);
            if(valor.trim().isEmpty())throw percentual(numeroLinha,"AUSENTE");
            if(valor.length()>64||!valor.matches("[0-9]+(\\.[0-9]+)?"))throw percentual(numeroLinha,"FORMATO");
            if(valor.indexOf('.')>=0&&valor.length()-valor.indexOf('.')-1>6)throw percentual(numeroLinha,"PRECISAO");
            if(new BigDecimal(valor).signum()<=0)throw percentual(numeroLinha,"NAO_POSITIVO");
            if(new BigDecimal(valor).compareTo(new BigDecimal("100"))>0)throw percentual(numeroLinha,"ACIMA_100");
            percentuais.add(new EntradaSimulacao.Percentual((int)emp,(int)cr,new BigDecimal(valor)));
        }
        return EntradaSimulacao.manual(percentuais);
    }
    private String texto(String nome,int max){return texto(json,nome,max);}
    private static String texto(JsonObject objeto,String nome,int max){String valor=escalar(objeto.get(nome));if(valor.trim().isEmpty()||valor.length()>max)throw invalida("Parametro obrigatorio ou fora do limite: "+nome);return valor;}
    private static String escalar(JsonElement e){if(e==null||!e.isJsonPrimitive()||(!e.getAsJsonPrimitive().isString()&&!e.getAsJsonPrimitive().isNumber()))throw invalida("Parametro deve ser texto ou numero.");return e.getAsString();}
    private static long inteiro(JsonElement e){try{long n=new BigDecimal(escalar(e)).longValueExact();if(n<=0)throw new ArithmeticException();return n;}catch(NumberFormatException|ArithmeticException ex){throw invalida("Identificador deve ser inteiro positivo.");}}
    private static void campos(JsonObject objeto,String... nomes){Set<String> permitidos=new HashSet<String>(Arrays.asList(nomes));for(Map.Entry<String,JsonElement> e:objeto.entrySet())if(!permitidos.contains(e.getKey()))throw invalida("Entrada contem campo nao permitido.");}
    private static ErroRateio invalida(String mensagem){return new ErroRateio("COMPOSICAO_INVALIDA",mensagem);}
    private static ErroRateio percentual(int linha,String motivo){return invalida("Percentual da linha "+linha+" invalido: "+motivo+".");}
}
