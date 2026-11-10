package br.com.zaya.engineering.nexobutton;
import java.util.*;
public final class StockCollectorChecks {
    private static Map<String,String> row(String... entries) {
        Map<String,String> result=new HashMap<String,String>();
        for (int i=0;i<entries.length;i+=2) result.put(entries[i],entries[i+1]);
        return result;
    }
    public static void main(String[] args) {
        Map<String,String> item=row("SEQUENCIA","1","CODPROD","19391","CODEMP","1","CODLOCALORIG","10",
                "CONTROLE",null,"CODVOL","UN","QTDNEG","10","ATIVO","N","STOCKUNIT","UN");
        Map<String,String> stock=row("CODPROD","19391","CODEMP","1","CODLOCAL","10","CONTROLE",null,
                "CODPARC","0","TIPO","P","ESTOQUE","3","RESERVADO","2");
        Map<String,String> unrelated=new HashMap<String,String>(stock);
        unrelated.put("CODEMP","2"); unrelated.put("ESTOQUE","999");
        String json=NoteStockCollector.assemble(123,"synthetic","test-version","synthetic signals",
                Arrays.asList(item),Arrays.asList(stock,unrelated));
        if (!json.contains("\"productActive\":false") || !json.contains("\"physical\":3") || !json.contains("\"reserved\":2")
                || !json.contains("\"availability\":null") || json.contains("999")) throw new AssertionError("Stock dimensions or product activity wrong");
        item.put("QTDNEG","-10");
        json=NoteStockCollector.assemble(123,"synthetic","test-version","signals",Arrays.asList(item),Collections.<Map<String,String>>emptyList());
        if (!json.contains("\"required\":null") || !json.contains("does not establish zero stock")) throw new AssertionError("Missing values must not be invented");
        List<Map<String,String>> many=new ArrayList<Map<String,String>>();
        for (int i=0;i<51;i++) many.add(item);
        json=NoteStockCollector.assemble(123,"synthetic","test-version","signals",many,Collections.<Map<String,String>>emptyList());
        if (!json.contains("truncated to 50")) throw new AssertionError("Limit must be reported");
        System.out.println("Product/stock collector: inactive product, physical/reserved separation, dimension filtering, missing rows and item bounds passed.");
    }
}
