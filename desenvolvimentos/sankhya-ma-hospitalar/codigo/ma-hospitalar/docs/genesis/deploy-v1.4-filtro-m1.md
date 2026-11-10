# Deploy V1.4 - filtro de equipamentos M1

## Regra

Somente `TGFPRO.AD_TIPOPRODUTO='M1'` pode gerar item-material na Genesis.
Qualquer outro valor e bloqueado antes da primeira chamada HTTP.

## Deploy

1. Fazer backup da definicao vigente de `AD_VW_GENESIS_ESTOQUE`.
2. Executar `01_ad_vw_genesis_estoque.sql` como uma instrucao unica.
3. Implantar `ZAYA-MAH-GENESIS-V1.4.jar` no mesmo modulo da versao anterior.
4. Reiniciar ou recarregar o modulo conforme o processo Sankhya homologado.
5. Executar separadamente as consultas 6, 7 e 8 de
   `02_validar_ad_vw_genesis_estoque.sql`.
6. Confirmar que `TOTAL_TIPOS_INDEVIDOS=0` e `TOTAL_CODPROD_13331=0`.
7. Executar a agenda inicialmente com lote 1 e conferir o log detalhado.

## Rollback

1. Desativar temporariamente a agenda Genesis.
2. Reimplantar o JAR V1.3.
3. Executar `03_rollback_filtro_tipo_produto.sql` como uma instrucao unica.
4. Validar os totais anteriores da view antes de reativar a agenda.

O deploy nao altera registros de `TGFPRO`, `TGFCAB`, `TGFITE`, `TGFSER` ou das
tabelas de integracao. A mudanca da view afeta apenas a elegibilidade de leitura.
