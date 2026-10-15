package br.zaya.telecontrol.models;

public class LinhaGet {
    public String linha;
    public String nome;
    public String codigo_linha;
    public boolean ativo;
    public Fabrica fabrica;

    public static class Fabrica {
        public String fabrica;
    }
}
