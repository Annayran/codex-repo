package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

/** Never propagate raw JDBC messages, SQL bindings or connection details to the modal. */
public final class FalhaConsultaRateio extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String operacao;
    private final Integer codigoBanco;
    private final String bancoTipo;
    public FalhaConsultaRateio(String operacao) {
        this(operacao,null);
    }
    public FalhaConsultaRateio(String operacao,Throwable causa) {
        this(operacao,causa,null);
    }
    public FalhaConsultaRateio(String operacao,Throwable causa,String bancoTipo) {
        super("Falha tecnica na consulta de rateio. Consulte a correlacao no servidor.");
        this.operacao = operacao;
        this.bancoTipo="ORACLE".equals(bancoTipo)||"SQLSERVER".equals(bancoTipo)?bancoTipo:null;
        Integer codigo=null;
        java.util.Set<Throwable> vistos=java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<Throwable,Boolean>());
        java.util.Deque<Throwable> fila=new java.util.ArrayDeque<Throwable>();
        if(causa!=null)fila.add(causa);
        while(!fila.isEmpty()){
            Throwable t=fila.removeFirst();if(!vistos.add(t))continue;
            if(t instanceof java.sql.SQLException){
                java.sql.SQLException sql=(java.sql.SQLException)t;
                if(sql.getErrorCode()!=0){codigo=sql.getErrorCode();break;}
                if(sql.getNextException()!=null)fila.addLast(sql.getNextException());
            }
            if(t.getCause()!=null)fila.addLast(t.getCause());
        }
        this.codigoBanco=codigo;
    }
    public String getOperacao() { return operacao; }
    public Integer getCodigoBanco(){return codigoBanco;}
    public String getBancoTipo(){return bancoTipo;}
}
