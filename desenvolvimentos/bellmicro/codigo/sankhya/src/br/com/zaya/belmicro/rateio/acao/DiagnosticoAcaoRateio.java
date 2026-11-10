package br.com.zaya.belmicro.rateio.acao;

import com.google.gson.Gson;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

/** Per-request metadata only. Never accepts payloads, SQL or exception messages. */
final class DiagnosticoAcaoRateio {
    static final String VERSAO="7.14";
    private final String referencia=UUID.randomUUID().toString(),inicio=Instant.now().toString();
    private final long nanos=System.nanoTime();
    private final List<Map<String,Object>> etapas=new ArrayList<Map<String,Object>>();
    private String operacao,etapa="INICIAR",bancoTipo;
    void bancoTipo(String tipo){if("ORACLE".equals(tipo)||"SQLSERVER".equals(tipo))bancoTipo=tipo;}
    DiagnosticoAcaoRateio(String operacao){this.operacao=operacao;}
    void operacao(String valor){operacao=valor;}
    void etapa(String valor){
        etapa=valor;Map<String,Object> item=new LinkedHashMap<String,Object>();
        item.put("etapa",valor);item.put("decorridoMs",decorrido());etapas.add(item);
    }
    Map<String,Object> concluir(String codigo,Integer banco){
        Map<String,Object> d=new LinkedHashMap<String,Object>();
        d.put("referencia",referencia);d.put("versao",VERSAO);d.put("operacao",operacao);
        d.put("inicio",inicio);d.put("duracaoMs",decorrido());d.put("etapa",etapa);
        d.put("codigo",codigo);d.put("banco",banco);d.put("etapas",new ArrayList<Map<String,Object>>(etapas));
        d.put("bancoTipo",bancoTipo);
        String registro=new Gson().toJson(d);Logger logger=Logger.getLogger("br.com.zaya.belmicro.rateio");
        if("SUCESSO".equals(codigo))logger.info(registro);else logger.warning(registro);
        return d;
    }
    private long decorrido(){return (System.nanoTime()-nanos)/1000000;}
    static String detalheComposicao(String mensagem){
        java.util.regex.Matcher linha=java.util.regex.Pattern.compile("Percentual da linha ([1-9][0-9]{0,5}) invalido: (AUSENTE|FORMATO|PRECISAO|NAO_POSITIVO|ACIMA_100)\\.").matcher(mensagem==null?"":mensagem);
        if(linha.matches()){
            String motivo=linha.group(2),texto;
            if("AUSENTE".equals(motivo))texto="informe o percentual.";
            else if("PRECISAO".equals(motivo))texto="use até seis casas decimais.";
            else if("NAO_POSITIVO".equals(motivo))texto="o percentual deve ser maior que zero.";
            else if("ACIMA_100".equals(motivo))texto="o percentual não pode ultrapassar 100%.";
            else texto="use somente números e separador decimal, sem símbolo de porcentagem. No JSON, use ponto decimal.";
            return "Linha "+linha.group(1)+": "+texto;
        }
        // Exact application-owned messages only; never echo untrusted provider text.
        Map<String,String> m=new HashMap<String,String>();
        m.put("Informe RATEIO_JSON pelo dashboard.","A composição não foi recebida. Abra o rateio pelo botão do Financeiro.");
        m.put("Estrutura de entrada excessiva.","A estrutura da solicitação excede o limite permitido.");
        m.put("Entrada deve ser objeto JSON.","A solicitação deve conter um objeto de composição válido.");
        m.put("RATEIO_JSON invalido.","A composição recebida está em formato inválido.");
        m.put("Entrada contem campo nao permitido.","A solicitação contém campos não previstos para esta operação.");
        m.put("Informe a composicao ou criterio.","Informe os destinos do rateio ou selecione um critério.");
        m.put("Modalidade invalida.","Escolha rateio manual ou critério cadastrado.");
        m.put("Modo invalido para Simular.","O comando recebido não corresponde à simulação.");
        m.put("Composicao invalida.","Informe uma lista válida de destinos do rateio.");
        m.put("Destino invalido.","Há uma linha de destino em formato inválido.");
        m.put("Identificador deve ser inteiro positivo.","Empresa, centro de resultado ou identificação da simulação deve ser um código inteiro positivo.");
        m.put("Empresa ou CR fora do intervalo.","O código da empresa ou do centro de resultado está fora do intervalo permitido.");
        m.put("Percentual invalido.","Informe percentuais numéricos com até seis casas decimais, sem símbolo de porcentagem.");
        m.put("Percentual deve ser positivo, com ate seis casas.","Cada percentual deve ser maior que zero e ter até seis casas decimais.");
        m.put("Percentuais positivos, ate seis casas e soma 100 sao obrigatorios.","Os percentuais devem ser positivos, ter até seis casas decimais e somar exatamente 100%.");
        m.put("Composicao manual ausente ou incompleta.","A composição manual está ausente ou contém uma linha incompleta.");
        m.put("Entrada obrigatoria.","Informe a composição antes de simular.");
        m.put("Matriz com percentual invalido.","O critério cadastrado contém percentual inválido. Revise sua matriz.");
        m.put("Informe um motivo de ate 255 caracteres, sem dados pessoais.","Informe o motivo do recálculo com até 255 caracteres.");
        for(String campo:Arrays.asList("modalidade","solicitacaoId","motivo","criterioId","hashEsperado"))
            m.put("Parametro obrigatorio ou fora do limite: "+campo,"Confira o campo obrigatório: "+campo+".");
        String detalhe=m.get(mensagem);
        return detalhe==null?"Confira os destinos, os códigos e os percentuais da composição enviada.":detalhe;
    }
    static String jsonAscii(Object objeto){
        String json=new Gson().toJson(objeto);StringBuilder ascii=new StringBuilder();
        for(int i=0;i<json.length();i++){
            char c=json.charAt(i);
            if(c>127){ascii.append("\\u");String hex=Integer.toHexString(c);for(int z=hex.length();z<4;z++)ascii.append('0');ascii.append(hex);}
            else ascii.append(c);
        }
        return ascii.toString();
    }
}
