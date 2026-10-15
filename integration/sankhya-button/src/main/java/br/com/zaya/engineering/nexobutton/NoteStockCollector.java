package br.com.zaya.engineering.nexobutton;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import java.sql.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

/** Fixed, bound, read-only queries. No availability formula or SQL from the caller. */
public final class NoteStockCollector {
    static final String ITEMS_SQL = "SELECT I.SEQUENCIA,I.CODPROD,I.CODEMP,I.CODLOCALORIG,I.CONTROLE,I.CODVOL,I.QTDNEG,"
            + "P.ATIVO,P.CODVOL AS STOCKUNIT FROM TGFITE I LEFT JOIN TGFPRO P ON P.CODPROD=I.CODPROD "
            + "WHERE I.NUNOTA=? ORDER BY I.SEQUENCIA";
    static final String STOCK_SQL = "SELECT E.CODPROD,E.CODEMP,E.CODLOCAL,E.CONTROLE,E.CODPARC,E.TIPO,E.ESTOQUE,E.RESERVADO "
            + "FROM TGFEST E WHERE EXISTS (SELECT 1 FROM TGFITE I WHERE I.NUNOTA=? AND I.CODPROD=E.CODPROD "
            + "AND I.CODEMP=E.CODEMP AND I.CODLOCALORIG=E.CODLOCAL "
            + "AND (I.CONTROLE=E.CONTROLE OR (I.CONTROLE IS NULL AND E.CONTROLE IS NULL))) "
            + "ORDER BY E.CODPROD,E.CODEMP,E.CODLOCAL,E.CONTROLE,E.CODPARC,E.TIPO";
    private NoteStockCollector() {}
    public static String collect(long note, String error, String version, String signals) throws Exception {
        return collect(note,error,version,signals,null);
    }
    public static String collect(long note, String error, String version, String signals,String logExcerpt) throws Exception {
        MinimalPackage.collect(note,error,version,signals);
        JdbcWrapper jdbc = null;
        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();
            try (PreparedStatement items=jdbc.getPreparedStatement(ITEMS_SQL);
                 PreparedStatement stock=jdbc.getPreparedStatement(STOCK_SQL)) {
                items.setLong(1,note); items.setMaxRows(51); items.setQueryTimeout(3);
                stock.setLong(1,note); stock.setMaxRows(501); stock.setQueryTimeout(3);
                List<Map<String,String>> itemRows;
                List<Map<String,String>> stockRows;
                try (ResultSet rows=items.executeQuery()) { itemRows=read(rows,51); }
                try (ResultSet rows=stock.executeQuery()) { stockRows=read(rows,501); }
                String packet=assemble(note,error,version,signals,itemRows,stockRows);
                return logExcerpt==null?packet:packet.substring(0,packet.length()-1)+",\"logExcerpt\":"+logExcerpt+"}";
            }
        } catch (Exception failure) {
            // Never expose SQL text, schema paths, credentials or raw database messages.
            throw new IllegalStateException("Não foi possível coletar produto/estoque. Verifique permissão SELECT e compatibilidade dos campos; nenhum pacote completo foi enviado.");
        } finally { JdbcWrapper.closeSession(jdbc); }
    }
    private static List<Map<String,String>> read(ResultSet rows,int limit) throws SQLException {
        List<Map<String,String>> result=new ArrayList<Map<String,String>>();
        while (result.size()<limit && rows.next()) {
            Map<String,String> row=new HashMap<String,String>();
            for (int column=1;column<=rows.getMetaData().getColumnCount();column++)
                row.put(rows.getMetaData().getColumnLabel(column).toUpperCase(Locale.ROOT),rows.getString(column));
            result.add(row);
        }
        return result;
    }
    static String assemble(long note,String error,String version,String signals,
                           List<Map<String,String>> rows,List<Map<String,String>> stocks) {
        // Reuse validation/escaping of the minimal collector for user-provided text.
        MinimalPackage.collect(note,error,version,signals);
        List<String> items=new ArrayList<String>();
        List<String> evidence=new ArrayList<String>();
        List<String> missing=new ArrayList<String>();
        missing.add("Physical/reserved stock is not availability; approved policy, ownership semantics and conversions missing");
        missing.add("Historical TOP, events and customizations not collected; current snapshot may differ from failed transaction");
        if (rows.isEmpty()) missing.add("No persisted items found for selected note; failed insert may have rolled back");
        if (rows.size()>50) missing.add("Item collection truncated to 50 rows");
        if (stocks.size()>500) missing.add("Stock collection truncated to 500 rows");
        evidence.add(evidence("selected-note","collection://sankhya/action-button/selected-note","Selected note NUNOTA="+note));
        evidence.add(evidence("server-log-signals","collection://sankhya/server-log/aggregate",signals));
        int index=0;
        for (Map<String,String> row : rows.subList(0,Math.min(50,rows.size()))) {
            index++;
            String reference="item-"+index;
            List<String> matching=new ArrayList<String>();
            int matches=0;
            for (Map<String,String> stock : stocks.subList(0,Math.min(500,stocks.size()))) {
                if (same(row,stock,"CODPROD","CODPROD") && same(row,stock,"CODEMP","CODEMP")
                        && same(row,stock,"CODLOCALORIG","CODLOCAL") && same(row,stock,"CONTROLE","CONTROLE")) {
                    matches++;
                    if (matching.size()<8) matching.add("{\"company\":"+text(stock.get("CODEMP"))+",\"location\":"+text(stock.get("CODLOCAL"))
                            +",\"control\":"+text(stock.get("CONTROLE"))+",\"partner\":"+text(stock.get("CODPARC"))
                            +",\"type\":"+text(stock.get("TIPO"))+",\"physical\":"+number(stock.get("ESTOQUE"),false)
                            +",\"reserved\":"+number(stock.get("RESERVADO"),false)+"}");
                }
            }
            if (matches>8) missing.add("Stock rows truncated to 8 for item "+index);
            if (matches==0) missing.add("No stock row collected for item "+index+"; this does not establish zero stock");
            String active="S".equals(row.get("ATIVO")) ? "true" : "N".equals(row.get("ATIVO")) ? "false" : "null";
            String required=number(row.get("QTDNEG"),true);
            if ("null".equals(required)) missing.add("Positive required quantity unavailable for item "+index);
            evidence.add(evidence(reference,"collection://sankhya/TGFITE-TGFPRO/"+note+"/"+index,
                    "sequence="+row.get("SEQUENCIA")+"; product="+row.get("CODPROD")+"; ATIVO="+row.get("ATIVO")
                    +"; QTDNEG="+row.get("QTDNEG")+"; stockRowsCollected="+matching.size()+"; no availability computed"));
            items.add("{\"item\":{\"product\":"+text(row.get("CODPROD"))+",\"company\":"+text(row.get("CODEMP"))
                    +",\"location\":"+text(row.get("CODLOCALORIG"))+",\"expectedLocation\":null,\"unit\":"+text(row.get("CODVOL"))
                    +",\"stockUnit\":"+text(row.get("STOCKUNIT"))+",\"control\":"+text(row.get("CONTROLE"))
                    +",\"stockControl\":null,\"required\":"+required+",\"availability\":null,\"ruleValidated\":false,\"ruleReference\":null"
                    +",\"productActive\":"+active+",\"stockRows\":["+String.join(",",matching)+"]},\"evidenceIds\":["+text(reference)+"]}");
        }
        // Total missing list remains within intake limits even for a large/partial note.
        if (missing.size()>45) {
            missing=new ArrayList<String>(missing.subList(0,44));
            missing.add("Additional collection gaps omitted to respect package limits");
        }
        List<String> encodedMissing=new ArrayList<String>();
        for (String entry:missing) encodedMissing.add(text(entry));
        return "{\"schemaVersion\":1,\"nunota\":"+note+",\"action\":\"CONFIRMAR_NOTA\",\"error\":"+text(error)
                +",\"collectedAt\":"+text(Instant.now().toString())+",\"erpVersion\":"+text(version)
                +",\"collectorVersion\":\"note-product-stock-v1\",\"simulated\":false,\"items\":["+String.join(",",items)
                +"],\"evidence\":["+String.join(",",evidence)+"],\"missing\":["+String.join(",",encodedMissing)+"]}";
    }
    private static boolean same(Map<String,String> left,Map<String,String> right,String a,String b) {
        return Objects.equals(left.get(a),right.get(b));
    }
    private static String text(String value) { return value==null ? "null" : MinimalPackage.quote(value); }
    private static String number(String value,boolean positive) {
        if (value==null) return "null";
        BigDecimal decimal=new BigDecimal(value);
        if (decimal.precision()>28 || Math.abs((long)decimal.scale())>8 || positive && decimal.signum()<=0) return "null";
        return decimal.toPlainString();
    }
    private static String evidence(String id,String source,String observation) {
        return "{\"id\":"+text(id)+",\"source\":"+text(source)+",\"version\":\"1\",\"observation\":"+text(observation)+"}";
    }
}
