# Dashboard HTML5 / JSP / JavaScript para Integracao Genesis

## Conclusao

O HTML5/JSP/JavaScript deve ser usado como dashboard analitico da integracao MA Hospitalar x Genesis, conforme a ideia do documento funcional: consolidar informacoes da view de estoque por serie e cruzar com a tabela de controle da integracao.

A tela nao substitui a `ScheduledAction` Java. O envio para Genesis continua server-side, mas o dashboard permite analisar o que existe na view, o que representa entrada/saida/estoque e o que deu certo ou erro na integracao.

## Objetivo do dashboard

- Consolidar dados da `AD_VW_GENESIS_ESTOQUE`.
- Exibir leitura analitica de `ENTRADA`, `SAIDA` e estoque/base instalada por serie.
- Cruzar cada movimento com `AD_GENMOVEST` para mostrar status da integracao.
- Apoiar validacao do usuario/gestor: o que esta elegivel, o que foi enviado, o que falhou e quais series/produtos estao impactados.
- Facilitar reunioes e homologacao sem consultar SQL manualmente.

## Fontes de dados

### AD_VW_GENESIS_ESTOQUE

Fonte principal do dashboard. Campos esperados:

- Empresa: `CODEMP`, `NOMEFANTASIA`, `RAZAO_EMPRESA`, `CNPJ_EMPRESA`.
- Nota: `NUNOTA`, `NUMNOTA`, `DTNEG`, `DTINSTALACAO`, `CODTIPOPER`, `SEQUENCIA`.
- Parceiro: `CODPARC`, `NOMEPARC`, `RAZAO_PARCEIRO`, `CNPJ_CPF_PARCEIRO`, `CEP`, `ENDERECO`, `TELEFONE`, `PARC_STATUS`.
- Produto/serie: `CODPROD`, `DESCRPROD`, `PRODUTO`, `CODGRUPOPROD`, `DESCRGRUPOPROD`, `GRUPO_PRODUTO`, `VLRUNIT`, `CODLOCALORIG`, `DESCRLOCAL`, `MARCA`, `CODANVISA`, `SERIE`, `SERIE_FABRICANTE`, `AVARIADO`, `SER_ATUAL_ESTOQUE`, `PROD_STATUS`.
- Analiticos: `TIPO`, `PODER`, `GARANTIA_MESES`, `STATUS_ESTOQUE`, `STATUS_ESTOQUE_DESC`, `CHAVE_MOVIMENTO`.

### AD_GENMOVEST

Fonte do status da integracao:

- `CHAVE_MOVIMENTO`
- `TIPO_MOVIMENTO`
- `STATUS_INTEGRACAO`
- `TENTATIVAS`
- `HTTP_STATUS`
- `MENSAGEM_ERRO`
- `PROTOCOLO_GENESIS`
- `DH_INCLUSAO`
- `DH_ENVIO`
- `DH_ATUALIZACAO`
- `NUNOTA`

## Consultas sugeridas para backend interno

O JSP/JavaScript deve consumir endpoints internos do Sankhya. Esses endpoints devem consultar o banco e devolver JSON para a tela.

### GET /mge/ma-hospitalar/genesis/dashboard/resumo

Consulta sugerida:

```sql
SELECT
    SUM(CASE WHEN V.TIPO = 'ENTRADA' THEN 1 ELSE 0 END) AS ENTRADAS,
    SUM(CASE WHEN V.TIPO = 'SAIDA' THEN 1 ELSE 0 END) AS SAIDAS,
    COUNT(DISTINCT V.SERIE) AS SERIES,
    SUM(CASE WHEN NVL(M.STATUS_INTEGRACAO, 'PENDENTE') = 'ENVIADO' THEN 1 ELSE 0 END) AS ENVIADOS,
    SUM(CASE WHEN NVL(M.STATUS_INTEGRACAO, 'PENDENTE') IN ('PENDENTE', 'REPROCESSAR') THEN 1 ELSE 0 END) AS PENDENTES,
    SUM(CASE WHEN NVL(M.STATUS_INTEGRACAO, 'PENDENTE') = 'ERRO' THEN 1 ELSE 0 END) AS ERROS
FROM AD_VW_GENESIS_ESTOQUE V
LEFT JOIN AD_GENMOVEST M ON M.CHAVE_MOVIMENTO = V.CHAVE_MOVIMENTO
WHERE V.SERIE IS NOT NULL;
```

### GET /mge/ma-hospitalar/genesis/dashboard/movimentos

Consulta sugerida:

```sql
SELECT
    V.CODEMP,
    V.NOMEFANTASIA,
    V.NUNOTA,
    V.NUMNOTA,
    V.CODTIPOPER,
    V.CODPARC,
    V.NOMEPARC,
    V.CODPROD,
    V.DESCRPROD,
    V.GRUPO_PRODUTO,
    V.SERIE,
    V.TIPO,
    V.PODER,
    V.DESCRLOCAL,
    V.STATUS_ESTOQUE,
    V.STATUS_ESTOQUE_DESC,
    V.GARANTIA_MESES,
    V.VLRUNIT,
    V.CHAVE_MOVIMENTO,
    NVL(M.STATUS_INTEGRACAO, 'PENDENTE') AS STATUS_INTEGRACAO,
    M.TENTATIVAS,
    M.HTTP_STATUS,
    M.MENSAGEM_ERRO,
    M.PROTOCOLO_GENESIS,
    M.DH_ENVIO,
    M.DH_ATUALIZACAO
FROM AD_VW_GENESIS_ESTOQUE V
LEFT JOIN AD_GENMOVEST M ON M.CHAVE_MOVIMENTO = V.CHAVE_MOVIMENTO
WHERE V.SERIE IS NOT NULL
ORDER BY V.NUNOTA, V.SEQUENCIA;
```

Filtros recomendados:

- `TIPO`: entrada, saida ou a confirmar.
- `STATUS_INTEGRACAO`: pendente, enviado, erro, reprocessar.
- `STATUS_ESTOQUE`: operacional, alienado, inoperante, validacao, desativado, restricoes.
- `NUNOTA`.
- `SERIE`.
- `CODPROD`.

## O que a tela deve mostrar

- Indicadores principais: entradas, saidas, series/equipamentos, enviados, pendentes e erros.
- Distribuicao por tipo de movimento: entrada/saida/a confirmar.
- Distribuicao por status de estoque: operacional, alienado, inoperante, validacao, desativado e restricoes.
- Distribuicao por status de integracao: pendente, enviado, erro, reprocessar.
- Tabela analitica unindo dados da view com resultado da integracao.
- Detalhe do erro quando existir `MENSAGEM_ERRO`.

## Regra de seguranca

O token Genesis nao deve ir para HTML, JSP ou JavaScript. O dashboard consulta somente endpoints internos do Sankhya e dados ja gravados/consultaveis no banco. O POST continua no Java server-side.

## Arquivos prototipo

- `src/main/webapp/genesis/index.jsp`
- `src/main/webapp/genesis/assets/genesis-integracao.css`
- `src/main/webapp/genesis/assets/genesis-integracao.js`

O prototipo foi ajustado para o conceito analitico do dashboard. Ainda faltam os endpoints internos que retornarao os dados reais em JSON.