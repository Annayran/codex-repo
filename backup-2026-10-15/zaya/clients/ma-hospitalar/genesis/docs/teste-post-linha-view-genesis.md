# Teste de POST com uma linha da AD_VW_GENESIS_ESTOQUE

Objetivo: validar o envio real de apenas uma linha da view `AD_VW_GENESIS_ESTOQUE` para o endpoint `POST /api/v1/estoques/` da Genesis.

Este teste altera estado na API Genesis. Execute somente em homologacao Genesis e com uma linha real conferida.

## 1. Buscar uma linha da view

Use uma consulta restrita para escolher conscientemente uma linha elegivel:

```sql
SELECT *
FROM AD_VW_GENESIS_ESTOQUE
WHERE ROWNUM = 1;
```

Preferencialmente filtre por uma nota/serie conhecida para evitar envio acidental de item incorreto.

## 2. Preencher variaveis de ambiente

Exemplo PowerShell sem expor token no codigo:

```powershell
$env:GENESIS_SMOKE_ENABLED='true'
$env:GENESIS_VIEW_ROW_POST_ENABLED='true'
$env:GENESIS_BASE_URL='https://sistema.genesis-s.com:7818/G4VAPI'
$env:GENESIS_TOKEN='<token-da-api>'

$env:GENESIS_VIEW_CODEMP='<CODEMP>'
$env:GENESIS_VIEW_NUNOTA='<NUNOTA>'
$env:GENESIS_VIEW_NUMNOTA='<NUMNOTA>'
$env:GENESIS_VIEW_CODTIPOPER='<CODTIPOPER>'
$env:GENESIS_VIEW_CODPARC='<CODPARC>'
$env:GENESIS_VIEW_NOMEPARC='<NOMEPARC>'
$env:GENESIS_VIEW_CNPJ_CPF_PARCEIRO='<CNPJ_CPF_PARCEIRO>'
$env:GENESIS_VIEW_CODPROD='<CODPROD>'
$env:GENESIS_VIEW_PRODUTO='<PRODUTO>'
$env:GENESIS_VIEW_GRUPO_PRODUTO='<GRUPO_PRODUTO>'
$env:GENESIS_VIEW_MARCA='<MARCA>'
$env:GENESIS_VIEW_CODANVISA='<CODANVISA>'
$env:GENESIS_VIEW_SERIE='<SERIE>'
$env:GENESIS_VIEW_PODER='<PODER>'
$env:GENESIS_VIEW_GARANTIA_MESES='<GARANTIA_MESES>'
$env:GENESIS_VIEW_STATUS_ESTOQUE='<STATUS_ESTOQUE>'
$env:GENESIS_VIEW_QTD='1'
$env:GENESIS_VIEW_VLRUNIT='<VLRUNIT>'
$env:GENESIS_VIEW_DESCRLOCAL='<DESCRLOCAL>'
$env:GENESIS_VIEW_CHAVE_MOVIMENTO='<CHAVE_MOVIMENTO>'
```

Campos opcionais caso a Genesis exija IDs internos:

```powershell
$env:GENESIS_VIEW_ID_CATEGORIA='<idCategoria>'
$env:GENESIS_VIEW_ID_FABRICANTE='<idFabricante>'
$env:GENESIS_VIEW_ID_FORNECEDOR='<idFornecedor>'
$env:GENESIS_VIEW_ID_SUBCATEGORIA='<idSubCategoria>'
```

Se `GENESIS_VIEW_DTINSTALACAO_EPOCH` nao for informado, o teste usa a data/hora atual como `dtEntrada`.

## 3. Executar o teste

```powershell
.\gradlew.bat smokeTest --tests '*GenesisApiViewRowPostSmokeTest' --stacktrace
```

Resultado esperado: HTTP 201.

## 4. Controle esperado na AD_GENMOVEST

O fluxo final deve registrar a fila antes do POST e atualizar o status depois da resposta.

Entrada pendente:

```sql
INSERT INTO AD_GENMOVEST (
    IDINTGENESIS,
    CHAVE_MOVIMENTO,
    TIPO_MOVIMENTO,
    STATUS_INTEGRACAO,
    TENTATIVAS,
    DH_INCLUSAO
) VALUES (
    SEQ_AD_GENMOVEST.NEXTVAL,
    :CHAVE_MOVIMENTO,
    :TIPO,
    'PENDENTE',
    0,
    SYSDATE
);
```

Sucesso Genesis:

```sql
UPDATE AD_GENMOVEST
   SET STATUS_INTEGRACAO = 'ENVIADO',
       HTTP_STATUS = 201,
       PROTOCOLO_GENESIS = :PROTOCOLO_GENESIS,
       DH_ENVIO = SYSDATE,
       DH_ATUALIZACAO = SYSDATE,
       MENSAGEM_ERRO = NULL
 WHERE CHAVE_MOVIMENTO = :CHAVE_MOVIMENTO;
```

Erro Genesis:

```sql
UPDATE AD_GENMOVEST
   SET STATUS_INTEGRACAO = 'ERRO',
       HTTP_STATUS = :HTTP_STATUS,
       MENSAGEM_ERRO = SUBSTR(:MENSAGEM_ERRO, 1, 4000),
       TENTATIVAS = TENTATIVAS + 1,
       DH_ATUALIZACAO = SYSDATE
 WHERE CHAVE_MOVIMENTO = :CHAVE_MOVIMENTO;
```

Se a sequence ainda nao existir:

```sql
CREATE SEQUENCE SEQ_AD_GENMOVEST START WITH 1 INCREMENT BY 1 NOCACHE;
```

## Observacoes

- `codigo` vem de `CODPROD`.
- `codigoExterno` vem de `CHAVE_MOVIMENTO`; se ela estiver vazia, o Java monta com empresa, nota, produto e serie.
- `nserie` vem de `SERIE`.
- `status` vem de `STATUS_ESTOQUE`.
- `periodoGarantia` vem de `GARANTIA_MESES`.
- `qtd` fica `1` para teste por serie.
- `vlrCusto`, `vlrVenda` e totais usam `VLRUNIT` inicialmente.
- `partNumber` e `tag` usam `MARCA` enquanto nao houver regra mais especifica.