package br.com.sankhya.jape.wrapper;
import java.util.HashMap;
import java.util.Map;
/** Substituto exclusivo do processo de teste; nunca incluir no JAR. */
public class JapeFactory {
    public static final Map<String, JapeWrapper> DAOS = new HashMap<String, JapeWrapper>();
    public static JapeWrapper dao(String name) { return DAOS.get(name); }
}
