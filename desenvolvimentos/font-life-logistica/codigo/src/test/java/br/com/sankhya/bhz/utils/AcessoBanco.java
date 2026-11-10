package br.com.sankhya.bhz.utils;
public class AcessoBanco {
    public static int closed;
    public void openSession() { }
    public void update(String sql, Object... params) { }
    public void closeSession() { closed++; }
}
