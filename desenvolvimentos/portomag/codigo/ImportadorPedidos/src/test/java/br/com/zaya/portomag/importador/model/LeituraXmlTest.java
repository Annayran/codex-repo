package br.com.zaya.portomag.importador.model;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
public final class LeituraXmlTest {
    private static int count;
    private static void reject(String xml) throws Exception {
        try{new LeituraXml().ler(xml);}catch(Exception expected){count++;return;}
        throw new AssertionError("Entrada invalida foi aceita.");
    }
    public static void main(String[] args) throws Exception {
        String xml=new String(Files.readAllBytes(Paths.get(args[0])),StandardCharsets.UTF_8);
        PedidoRecebido p=new LeituraXml().ler(xml);
        if(p.itens.size()!=2||p.alertas.size()!=1)throw new AssertionError("Itens/alertas incorretos.");count++;
        reject(xml.replace("<pedido versao=\"1\">","<!DOCTYPE pedido [<!ENTITY e SYSTEM 'file:///nao-ler'>]><pedido versao=\"1\">"));
        reject(xml.replace("nome=\"NUMPED\"","nome=\"SQL\""));
        reject(xml.replace(">0002</campo>",">0001</campo>"));
        reject(xml.replace(">2026-09-03</campo>",">2026-02-30</campo>"));
        reject(xml.replace(">21616.09</campo>",">99999.99</campo>"));
        reject(xml.replace("<campo nome=\"QTDE\">30</campo>","<campo nome=\"QTDE\">-1</campo>"));
        reject(xml.replace("<campo nome=\"ALIQICMS\">7.00</campo>","<campo nome=\"ALIQICMS\">101.00</campo>"));
        reject(xml.replace("<campo nome=\"PAGANEXO\">12</campo>","<campo nome=\"PAGANEXO\">99</campo>"));
        reject(xml.replace("<cabecalho>","<cabecalho><campo nome=\"NUMPED\">123456</campo>"));
        reject(xml.replace("CLARO-PC-1.0","DESCONHECIDO"));
        reject("");
        System.out.println(count+" testes de modelo passaram. Nenhuma conexao com banco.");
    }
}
