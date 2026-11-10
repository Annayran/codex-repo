package br.com.zaya.proauto.compensacao;

/** Transicao de confirmacao; alteracoes comuns de nota liberada nao disparam. */
final class ConfirmacaoNota {
    static boolean deveExecutar(String movimento, String anterior, String atual) {
        return "V".equals(movimento) && anterior != null
                && !"L".equals(anterior) && "L".equals(atual);
    }
}
