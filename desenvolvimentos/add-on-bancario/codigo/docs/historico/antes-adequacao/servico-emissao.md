# Serviço de emissão nativa

Implementação: `model/src/main/java/br/com/zaya/bancario/sankhya/services/ZayaBoletoSPBean.java`.

Serviço lógico: `ZayaBoletoSP.gerarPdf`, publicado no broker `service.sbr` do contexto do add-on após instalação. O contexto final é definido pelo empacotamento oficial; o WAR intermediário ainda contém placeholders do plugin. Não implantar o WAR isoladamente como pacote final.

## Requisição

Exemplo sintético; não foi executado no ERP:

```json
{
  "serviceName": "ZayaBoletoSP.gerarPdf",
  "requestBody": {
    "nuFin": "1",
    "gerarNumeroBoleto": true,
    "usarBoletoRapido": false
  }
}
```

NUFIN deve ser inteiro positivo. As duas opções devem ser booleanos JSON explícitos; não são aceitas strings como `"false"`. Gerar numeração pode alterar o título. A flag Boleto Rápido é repassada ao helper; seus efeitos e a combinação com integrações configuradas na conta precisam de homologação. O documento PDF não confirma registro bancário ou pagamento.

## Autenticação e contexto

O plugin gera `service-providers.xml` com autenticação habilitada e JNDI correspondente às interfaces geradas por XDoclet. O serviço exige `AuthenticationInfo` válido e usuário incluído em `BANCARIO_EMISSAO_USUARIOS`, uma lista de códigos de usuários separada por vírgulas. Sem configuração, o serviço nega a emissão. Não há curinga nem exceção automática para SUP.

A inclusão na lista concede a permissão de emissão deste add-on para títulos informados pelo operador. Não foi criado um filtro adicional por empresa/título; as restrições do ERP e do helper devem ser verificadas em homologação antes de liberar operadores com escopo limitado.

O EJB usa transação `Required` do contêiner. A sessão Jape é aberta e fechada no serviço; falhas passam por `throwExceptionRollingBack`, e repetição automática é desativada. Isso não torna efeitos bancários externos reversíveis por rollback.

## Resultado

O corpo de resposta contém `contentType=application/pdf`, `fileName=boleto.pdf` e `pdfBase64`, codificado com Base64 do Java 8. Não retorna credenciais nem grava o PDF em arquivo público. Nenhum e-mail é enviado pelo adaptador.

## Build e pacote

Executar na raiz:

```powershell
.\scripts\Build-Addon.ps1 -JavaHome '<caminho-do-JDK8>'
```

`-Offline` reutiliza o cache resolvido. `-SemProxy` remove os proxies HTTP/HTTPS/ALL apenas no processo de build, restaurando-os ao final; usar somente quando essa for a configuração correta da rede. Não muda o Windows globalmente.

Após configurar `ADDON_APP_KEY` externamente, a opção `-GerarPacote` executa `gerarAddon`. Não executa deploy ou publicação. `WILDFLY_HOME` é necessário para as etapas de ambiente/desenvolvimento que dependam de servidor.

## Evidência de validação

- JDK real: Amazon Corretto 8u492; Wrapper original Gradle 8.2.
- A faixa `1+` do template resolveu o plugin oficial 1.14.5. Não houve migração para 2.x.
- `:model:build :vc:build` passou, incluindo seis grupos de verificações: cobrança, consulta, endpoints, geração, permissão e contrato JSON do SP.
- XDoclet gerou interface remota, home, session e descritores com `Required`; o provider gerado usa `authentication=true`.
- JAR de model e WAR intermediário gerados pelo plugin. Não são apresentados como pacote instalável independente.
- `gerarAddon` foi tentado e parou em `createExtensionFile` por ausência da chave do componente. Não foi criada chave fictícia.
- Nenhum deploy ou geração real de boleto foi executado. Instalação, geração PDF, rollback integrado e efeitos bancários permanecem pendentes de homologação.

As bibliotecas de referência local 4.35b99 continuam disponíveis para o teste isolado do adaptador. O build oficial usa o SDK resolvido pelo próprio plugin; nenhum desses fatos confirma a versão do ambiente do solicitante ou a compatibilidade de todas as versões a partir de 4.28.
