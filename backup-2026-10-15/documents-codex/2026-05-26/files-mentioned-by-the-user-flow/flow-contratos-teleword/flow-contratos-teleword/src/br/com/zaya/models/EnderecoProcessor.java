package br.com.zaya.models;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EnderecoUtils;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.utils.SankhyaUtils;

import com.sankhya.util.StringUtils;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jboss.logging.Logger;

public class EnderecoProcessor {
  private Logger logger = SankhyaUtils.getLogger(EnderecoProcessor.class);
  
  public Endereco cadastrar(Endereco endereco) throws Exception {
    EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
    if (SankhyaUtils.isEmpty(endereco.cep).booleanValue())
      throw new Exception("CEP do endereninformado!"); 
    if (SankhyaUtils.isEmpty(endereco.nomecid).booleanValue())
      throw new Exception("Cidade do endereninformada!"); 
    if (SankhyaUtils.isEmpty(endereco.nomebai).booleanValue())
      throw new Exception("Bairro do endere~o ninformado!"); 
    if (SankhyaUtils.isEmpty(endereco.nomeend).booleanValue())
      throw new Exception("Rua/Endereninformado!"); 
    if (SankhyaUtils.isEmpty(endereco.numend).booleanValue())
      throw new Exception("Ndo endereninformado!"); 
    BigDecimal coduf = findEstadoByUF(dwfFacade, endereco.uf);
    if (coduf == null)
      throw new Exception("Estado " + endereco.uf + " nencontrado!"); 
    BigDecimal codcid = EnderecoUtils.findCodigoCidadeByDescriptionAndUF(endereco.nomecid, coduf, dwfFacade);
    BigDecimal codbai = EnderecoUtils.findCodigoBairroByDescription(endereco.nomebai, dwfFacade);
    BigDecimal codend = EnderecoUtils.findCodigoEnderecoByDescription(endereco.nomeend, dwfFacade);
    Map<String, Object> enderecoInfo = extrairTokensEndereco(endereco.nomeend);
    String nomeend = (String)enderecoInfo.get("NOMEEND");
    String logradouro = (String)enderecoInfo.get("LOGRADOURO");
    endereco.coduf = coduf;
    endereco.codcid = codcid;
    endereco.codbai = codbai;
    endereco.codend = codend;
    endereco.tipoend = logradouro;
    endereco.nomeend = nomeend;
    Boolean deveInserirCidade = Boolean.valueOf((endereco.codcid == null));
    Boolean deveInserirBairro = Boolean.valueOf((endereco.codbai == null));
    Boolean deveInserirEndereco = Boolean.valueOf((endereco.codend == null));
    if (deveInserirCidade.booleanValue()) {
      this.logger.info("Cadastrando cidade: " + endereco.nomecid);
      endereco.codcid = cadastrarCidade(dwfFacade, endereco.nomecid, endereco.coduf);
    } 
    if (deveInserirBairro.booleanValue()) {
      this.logger.info("Cadastrando bairro: " + endereco.nomebai);
      endereco.codbai = cadastrarBairro(dwfFacade, endereco.nomebai);
    } 
    if (deveInserirEndereco.booleanValue()) {
      this.logger.info("Cadastrando endere" + endereco.tipoend + " " + endereco.nomeend);
      endereco.codend = cadastrarEndereco(dwfFacade, endereco.nomeend, endereco.tipoend);
    } 
    Boolean cepExiste = verificarCepCadastrado(dwfFacade, endereco);
    if (!cepExiste.booleanValue())
      endereco.cep = cadastrarCEP(dwfFacade, endereco); 
    if (deveInserirBairro.booleanValue() || deveInserirCidade.booleanValue() || deveInserirEndereco.booleanValue()) {
      SankhyaUtils.debug("---------------");
      SankhyaUtils.debug("CEP: " + endereco.cep);
      SankhyaUtils.debug("CODUF: " + endereco.coduf);
      SankhyaUtils.debug("CODCID: " + endereco.codcid);
      SankhyaUtils.debug("CODBAI: " + endereco.codbai);
      SankhyaUtils.debug("CODEND: " + endereco.codend);
      SankhyaUtils.debug("TIPOEND: " + endereco.tipoend);
      SankhyaUtils.debug("ENDERECO: " + endereco.nomeend);
      SankhyaUtils.debug("---------------");
    } 
    return endereco;
  }
  
  public static Boolean verificarCepCadastrado(EntityFacade dwfFacade, Endereco endereco) throws Exception {
    String whereClause = "this.CODCID = ? AND this.CODBAI = ? AND this.CODEND = ? AND this.CEP = ?";
    Object[] parametros = { endereco.codcid, endereco.codbai, endereco.codend, endereco.cep };
    FinderWrapper finder = new FinderWrapper("CEP", whereClause, parametros);
    Collection<DynamicVO> result = dwfFacade.findByDynamicFinderAsVO(finder);
    return Boolean.valueOf(!result.isEmpty());
  }
  
  public static String cadastrarCEP(EntityFacade dwfFacade, Endereco endereco) throws Exception {
    DynamicVO cepVO = (DynamicVO)dwfFacade.getDefaultValueObjectInstance("CEP");
    cepVO.setProperty("CODCID", endereco.codcid);
    cepVO.setProperty("CODBAI", endereco.codbai);
    cepVO.setProperty("CODEND", endereco.codend);
    cepVO.setProperty("CEP", endereco.cep);
    PersistentLocalEntity cepEntity = dwfFacade.createEntity("CEP", (EntityVO)cepVO);
    return ((DynamicVO)cepEntity.getValueObject()).asString("CEP");
  }
  
  public static BigDecimal cadastrarCidade(EntityFacade dwfFacade, String nomecid, BigDecimal coduf) throws Exception {
    DynamicVO cidadeVO = (DynamicVO)dwfFacade.getDefaultValueObjectInstance("Cidade");
    cidadeVO.setProperty("DTALTER", new Timestamp(System.currentTimeMillis()));
    cidadeVO.setProperty("NOMECID", nomecid);
    cidadeVO.setProperty("UF", coduf);
    PersistentLocalEntity cidadeEntity = dwfFacade.createEntity("Cidade", (EntityVO)cidadeVO);
    return ((DynamicVO)cidadeEntity.getValueObject()).asBigDecimal("CODCID");
  }
  
  public static BigDecimal cadastrarBairro(EntityFacade dwfFacade, String nomebai) throws Exception {
    DynamicVO bairroVO = (DynamicVO)dwfFacade.getDefaultValueObjectInstance("Bairro");
    bairroVO.setProperty("DTALTER", new Timestamp(System.currentTimeMillis()));
    bairroVO.setProperty("NOMEBAI", nomebai);
    PersistentLocalEntity bairroEntity = dwfFacade.createEntity("Bairro", (EntityVO)bairroVO);
    return ((DynamicVO)bairroEntity.getValueObject()).asBigDecimal("CODBAI");
  }
  
  public static BigDecimal cadastrarEndereco(EntityFacade dwfFacade, String nomeend, String tipend) throws Exception {
    DynamicVO enderecoVO = (DynamicVO)dwfFacade.getDefaultValueObjectInstance("Endereco");
    enderecoVO.setProperty("DTALTER", new Timestamp(System.currentTimeMillis()));
    enderecoVO.setProperty("NOMEEND", nomeend);
    enderecoVO.setProperty("TIPO", tipend);
    PersistentLocalEntity enderecoEntity = dwfFacade.createEntity("Endereco", (EntityVO)enderecoVO);
    return ((DynamicVO)enderecoEntity.getValueObject()).asBigDecimal("CODEND");
  }
  
  public static BigDecimal findEstadoByUF(EntityFacade dwfFacade, String uf) throws Exception {
    if (uf == null)
      return null; 
    FinderWrapper finder = new FinderWrapper("UnidadeFederativa", "UPPER(this.UF) = UPPER(?)", new Object[] { uf.trim() });
    Collection<DynamicVO> result = dwfFacade.findByDynamicFinderAsVO(finder);
    if (!result.isEmpty()) {
      DynamicVO bairroVO = result.iterator().next();
      return bairroVO.asBigDecimal("CODUF");
    } 
    return null;
  }
  
  public static Map<String, String> getMapeamentoLogradouro() {
    Map<String, String> mapeamento = new LinkedHashMap<>();
    mapeamento.put("AEROPORTO", "AER");
    mapeamento.put("ALAMEDA", "AL");
    mapeamento.put("APARTAMENTO", "AP");
    mapeamento.put("AVENIDA", "AV");
    mapeamento.put("BECO", "BC");
    mapeamento.put("BLOCO", "BL");
    mapeamento.put("CAMINHO", "CAM");
    mapeamento.put("CHACARA", "CHA");
    mapeamento.put("ESCADINHA", "ESCD");
    mapeamento.put("ESTA??O", "EST");
    mapeamento.put("ESTACAO", "EST");
    mapeamento.put("ESTRADA", "ETR");
    mapeamento.put("FAZENDA", "FAZ");
    mapeamento.put("FORTALEZA", "FORT");
    mapeamento.put("GALERIA", "GL");
    mapeamento.put("LADEIRA", "LD");
    mapeamento.put("LARGO", "LGO");
    mapeamento.put("PRA?A", "PC");
    mapeamento.put("PRACA", "PC");
    mapeamento.put("PARQUE", "PRQ");
    mapeamento.put("PRAIA", "PR");
    mapeamento.put("QUADRA", "QD");
    mapeamento.put("QUIL?METRO", "KM");
    mapeamento.put("QUILOMETRO", "KM");
    mapeamento.put("QUINTA", "QTA");
    mapeamento.put("RODOVIA", "ROD");
    mapeamento.put("TRAVESSA", "TRV");
    mapeamento.put("VIADUTO", "VD");
    return mapeamento;
  }
  
  public static Map<String, Object> extrairTokensEndereco(String endereco) {
    Map<String, Object> result = new LinkedHashMap<>();
    List<String> enderecoTokens = Arrays.asList(endereco.split(" "));
    String logradouro = "";
    String primeiroToken = StringUtils.removePontuacao(((String)enderecoTokens.get(0)).toUpperCase());
    if (EnderecoUtils.isLogradouroToken(primeiroToken)) {
      StringBuilder enderecoBuf = new StringBuilder(endereco.length());
      for (int i = 1; i < enderecoTokens.size(); i++)
        enderecoBuf.append(enderecoTokens.get(i)).append(" "); 
      endereco = enderecoBuf.toString();
      logradouro = primeiroToken;
      Map<String, String> mapeamento = getMapeamentoLogradouro();
      if (mapeamento.containsKey(primeiroToken))
        logradouro = mapeamento.get(primeiroToken); 
    } 
    result.put("LOGRADOURO", logradouro);
    result.put("NOMEEND", endereco.trim());
    return result;
  }
}

