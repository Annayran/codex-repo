package br.com.zaya.apiboletos.security;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.nfe.AssinadorNFe;
import java.security.cert.*;

/** Mesmo ponto de acesso utilizado pelo Sicoob, sem dependência do ZayaProd. */
public final class SankhyaCertificateProvider implements TlsMaterial.NativeProvider {
  public TlsMaterial.Material load(long company) throws Exception {
    DynamicVO empresa = JapeFactory.dao("Empresa").findByPK(java.math.BigDecimal.valueOf(company));
    if (empresa == null) throw new IllegalStateException("EMPRESA_CERTIFICADO_AUSENTE");
    String cnpj = empresa.asString("CGC");
    cnpj = cnpj == null ? "" : cnpj.replaceAll("\\D", "");
    if (cnpj.length() != 14) throw new IllegalStateException("CNPJ_EMPRESA_INVALIDO");
    try {
      AssinadorNFe signer = AssinadorNFe.getInstance(cnpj);
      Certificate[] certificates = signer.getCertificateChain();
      if (certificates == null || certificates.length == 0)
        throw new IllegalStateException("CADEIA_CERTIFICADO_AUSENTE");
      X509Certificate[] chain = new X509Certificate[certificates.length];
      for (int i = 0; i < chain.length; i++) chain[i] = (X509Certificate) certificates[i];
      X509Certificate leaf = signer.getX509Certificate();
      if (!leaf.equals(chain[0])) throw new IllegalStateException("CADEIA_CERTIFICADO_INVALIDA");
      TlsMaterial.validateCompany(leaf, cnpj);
      return new TlsMaterial.Material(signer.getPrivateKey(), chain);
    } catch (Exception e) {
      // Não propaga detalhes internos do ERP, senha, subject ou chave.
      throw new IllegalStateException("CERTIFICADO_SANKHYA_INDISPONIVEL_OU_INAPTO");
    }
  }
}
