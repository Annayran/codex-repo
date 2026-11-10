# Configuração - Genesis

## Objetos obrigatórios

- `AD_CENTPARAM`, registro `NUPAR=1`;
- `AD_CENTPARAM.TOKENGENESIS`, texto de 1000 caracteres e acesso restrito;
- `AD_CENTPARAMTOP`, TOPs ligadas ao `NUPAR=1`;
- `AD_VW_GENESIS_ESTOQUE`, já validada;
- `AD_GENMOVEST`, já cadastrada;
- `TGFSER.AD_GARANTIA` e `TGFSER.AD_STATUSEST`.
- `TGFPRO.AD_TIPOPRODUTO='M1'` para os equipamentos autorizados a integrar.

Não criar estruturas `AD_MAH_GEN_*` para esta integração.

## Configuração temporária de homologação

A URL de homologação, epoch `MS`, integração ativa, envio real e lote `1` estão
definidos no Java para reproduzir o fluxo de teste anterior. O token é lido do
campo restrito `TOKENGENESIS` da Central `NUPAR=1` e nunca deve aparecer em log,
dashboard ou documentação.

## Primeiro teste

1. Preencha `TOKENGENESIS` somente no registro `NUPAR=1`.
2. Execute a ação, que processará no máximo um movimento.
3. Confirme HTTP, protocolo e registros criados/atualizados no Genesis.
4. Corrija dados obrigatórios apontados no campo de erro antes de repetir.

Antes de liberar o envio, execute individualmente as consultas 6, 7 e 8 de
`sql/genesis/view/02_validar_ad_vw_genesis_estoque.sql`. As consultas 7 e 8
devem retornar zero.
