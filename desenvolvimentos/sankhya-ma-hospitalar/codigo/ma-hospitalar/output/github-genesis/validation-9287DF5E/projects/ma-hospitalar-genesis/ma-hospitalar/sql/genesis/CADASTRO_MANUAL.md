# Objetos existentes exigidos pela integração

Este módulo não instala nem altera objetos por SQL. Antes de publicar o JAR,
confirme manualmente no Sankhya:

- `AD_CENTPARAM`: registro `NUPAR=1`;
- `AD_CENTPARAM.TOKENGENESIS`: texto de 1000 caracteres, descrição
  `Token API Genesis - Homologação`, com acesso restrito;
- `AD_CENTPARAMTOP`: ao menos uma TOP ligada ao `NUPAR=1`;
- `TGFSER.AD_GARANTIA`: inteiro de 5 posições;
- `TGFSER.AD_STATUSEST`: texto de 1 posição (`O/A/I/V/D/R`);
- `AD_VW_GENESIS_ESTOQUE`: view validada pela equipe;
- `AD_GENMOVEST`: tabela de controle já cadastrada.

Campos esperados em `AD_GENMOVEST`: `IDINTGENESIS`, `CHAVE_MOVIMENTO`,
`TIPO_MOVIMENTO`, `STATUS_INTEGRACAO`, `TENTATIVAS`, `HTTP_STATUS`,
`MENSAGEM_ERRO`, `PAYLOAD_HASH`, `PROTOCOLO_GENESIS`, `DH_INCLUSAO`,
`DH_ENVIO`, `DH_ATUALIZACAO`, `USU_REPROCESSAMENTO`,
`DH_REPROCESSAMENTO` e `NUNOTA`.

Não criar `AD_MAH_GEN_CONFIG`, `AD_MAH_GEN_TOP`, `AD_MAH_GEN_MAP`,
`AD_MAH_GEN_BASE`, `AD_MAH_GEN_FILA`, `AD_MAH_GEN_FAB` ou `AD_MAH_GEN_PROD`.

Para a homologação atual, URL, ambiente, epoch, ativação e lote 1 estão definidos
no Java. O token não deve ser registrado em documentação, log ou dashboard.


---
Copia de consulta para entrega; fonte canonica local: `ma-hospitalar/sql/genesis/CADASTRO_MANUAL.md`. Sanitizacao de caminhos e enderecos aplicada; nao representa homologacao.
