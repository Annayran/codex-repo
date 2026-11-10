# Regra de tipo e poder do estoque

## Objetivo

Separar três conceitos na `AD_VW_GENESIS_ESTOQUE`:

- `TIPO`: movimento operacional já existente, como entrada ou saída;
- `TIPO_ESTOQUE`: classificação por CNPJ, `PROPRIO` ou `TERCEIRO`;
- `PODER`: classificação original por `TGFEST.CODPARC`.

## Regra confirmada

- `TIPO` permanece derivado dos valores da `TGFESE`;
- `PODER` permanece `PROPRIO` quando `TGFEST.CODPARC = 0` e `TERCEIRO` nos
  demais casos;
- somente `TIPO_ESTOQUE` compara o documento do parceiro com os CNPJs da
  `TSIEMP`;
- CNPJ correspondente a uma empresa gera `PROPRIO`; CNPJ externo gera
  `TERCEIRO`; documento ausente gera `NAO_CLASSIFICADO`.

## Alteração da view

Antes de substituir a view, exportar o DDL atual com `DBMS_METADATA.GET_DDL`.
O deploy completo esta em `sql/genesis/view/01_ad_vw_genesis_estoque.sql`. A
comparação remove máscara e pontuação dos documentos e não modifica `PODER`.

```sql
CASE
    WHEN PAR.CGC_CPF IS NULL THEN 'NAO_CLASSIFICADO'
    WHEN EXISTS (
        SELECT 1
          FROM TSIEMP EMPDOC
         WHERE LPAD(REGEXP_REPLACE(TO_CHAR(EMPDOC.CGC), '[^0-9]', ''), 14, '0') =
               LPAD(REGEXP_REPLACE(TO_CHAR(PAR.CGC_CPF), '[^0-9]', ''), 14, '0')
    ) THEN 'PROPRIO'
    ELSE 'TERCEIRO'
END AS TIPO_ESTOQUE
```

## Validação

```sql
SELECT TIPO, TIPO_ESTOQUE, PODER, COUNT(*) TOTAL
  FROM AD_VW_GENESIS_ESTOQUE
 GROUP BY TIPO, TIPO_ESTOQUE, PODER
 ORDER BY TIPO, TIPO_ESTOQUE, PODER
```

Validar separadamente parceiros sem documento e documentos sem correspondência:

```sql
SELECT NUNOTA, CODPARC, CNPJ_CPF_PARCEIRO, TIPO_ESTOQUE, PODER
  FROM AD_VW_GENESIS_ESTOQUE
 WHERE TIPO_ESTOQUE IN ('TERCEIRO', 'NAO_CLASSIFICADO')
 ORDER BY NUNOTA DESC
```

## Rollback

Restaurar o DDL exportado da view anterior. O dashboard que contém a coluna
`Tipo` deve ser publicado somente depois da view atualizada.
