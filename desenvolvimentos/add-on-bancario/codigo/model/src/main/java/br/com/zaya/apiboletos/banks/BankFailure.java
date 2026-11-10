package br.com.zaya.apiboletos.banks;

/** Não carrega payloads bancários, pagadores ou tokens na mensagem. */
public final class BankFailure extends Exception {
  public final int status;

  public BankFailure(int status) {
    super("BANCO_HTTP_" + status);
    this.status = status;
  }
}
