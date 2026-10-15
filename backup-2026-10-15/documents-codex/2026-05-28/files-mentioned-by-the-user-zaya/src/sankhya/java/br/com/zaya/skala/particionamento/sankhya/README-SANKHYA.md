# Camada Sankhya

Classe de entrada:

```text
br.com.zaya.skala.ParticionarPedidosAction
```

Classe para desfazer particionamento:

```text
br.com.zaya.skala.DesfazerParticionamentoAction
```

Evento para bloquear exclusao nativa de OE particionada:

```text
br.com.zaya.skala.BloquearExclusaoOEParticionadaEvent
```

## Parametro da acao

Configure um parametro numerico na acao customizada:

```text
QTDE_PALETES
```

Se o parametro vier vazio ou invalido, a rotina usa `28`.

## Campos usados

- `TGFTOP.AD_PERMITEPART`
- `TGFCAB.AD_PARTICIONADO`
- `TGFVOA.LASTRO`
- `TGFVOA.CAMADAS`
- `TGFVOA.DIVIDEMULTIPLICA`
- `TGFVOA.QUANTIDADE`
- `TGFITE.AD_QTDPALETES`
- `TGFITE.AD_QTDCAIXA`

O calculo oficial de paletes considera o volume do pedido (`TGFITE.CODVOL`):

```text
1. Buscar TGFVOA por TGFITE.CODPROD + TGFITE.CODVOL.
2. Converter TGFITE.QTDNEG, tratado como UN, para o volume do pedido usando TGFVOA.DIVIDEMULTIPLICA e TGFVOA.QUANTIDADE.
3. Paletes = quantidade convertida no volume / (TGFVOA.LASTRO * TGFVOA.CAMADAS).
```

Nao ha fallback para campos antigos de paletizacao.

Na geracao da OE, o `QTDNEG` calculado e tratado como UN e precisa ser inteiro. A alocacao em paletes e ajustada antes de criar o item para evitar quantidade fracionada em `ItemNota.QTDNEG`.

## TOP de OE

No arquivo `OEGenerator.java`, a TOP de OE esta configurada como:

```java
private static final int CODTIPOPER_OE = 3108;
```

Confira os campos obrigatorios do `CabecalhoNota` e `ItemNota` no ambiente Skala. O codigo ja copia os campos principais do pedido origem, mas cada ambiente Sankhya pode ter regras adicionais por TOP, empresa, negociacao e financeiro.

## Criacao das OEs

A implementacao atual cria uma nova OE por carreta fazendo uma copia controlada dos principais campos do pedido selecionado:

- empresa;
- parceiro;
- natureza;
- centro de resultado;
- vendedor;
- tipo de negociacao;
- datas principais;
- observacao;
- dados operacionais dos itens, como produto, local, controle, volume, uso do produto e valor unitario.

Depois altera apenas o necessario para a OE:

- `CODTIPOPER = 3108`;
- observacao com a carreta e pedido de origem;
- quantidade do item conforme particionamento;
- campos adicionais de palete e caixa.
- vinculo de variacao na `TGFVAR`, ligando cada item da OEP ao item original.

Isso nao e um clone completo automatico da nota. Se for necessario usar a rotina nativa de duplicacao/clone do Sankhya, substituir a criacao em `OEGenerator` pelo exemplo oficial/usado no projeto da Skala.

## TGFVAR

A rotina grava a integridade entre a OEP criada e o pedido origem na `TGFVAR`, seguindo o padrao validado por query do ambiente:

```text
NUNOTA        = NUNOTA da OEP criada
SEQUENCIA     = sequencia do item na OEP
NUNOTAORIG    = NUNOTA do pedido origem
SEQUENCIAORIG = sequencia do item original
QTDATENDIDA   = QTDNEG gerado para o item da OEP
STATUSNOTA    = 'L'
```

Os campos opcionais observados na query, como `CUSATEND`, `ORDEMPROD`, `FIXACAO`, `NROMEMORANDO`, `NROREGEXPORT` e `NROATOCONCDRAW`, permanecem nulos.

Antes de inserir, a rotina remove eventuais registros existentes da propria OEP em `TGFVAR`. Como a OEP acabou de ser criada, isso funciona como protecao contra reprocessamento interno e evita duplicidade se algum comportamento do ambiente reaproveitar numero durante rollback/teste.

## Rollback

A rotina executa a criacao das OEs e a marcacao de `TGFCAB.AD_PARTICIONADO` dentro de `JapeSession.execEnsuringTX`.

Durante a transacao, o pedido de origem e bloqueado com `SELECT ... FOR UPDATE`. Isso evita duas execucoes simultaneas no mesmo `NUNOTA`.

Se qualquer erro acontecer durante a criacao de cabecalho, itens, `TGFVAR`, rastreabilidade ou marcacao do pedido:

- nenhuma OE deve permanecer gravada pela transacao;
- o pedido original nao deve ser marcado como particionado;
- a mensagem de erro volta para o usuario da acao.

## Versao da TOP

As consultas de TOP consideram a versao mais recente por `MAX(DHALTER)`.

Isso vale para:

- validacao da TOP do pedido selecionado;
- criacao da OE com `CODTIPOPER = 3108`;
- preenchimento de `DHTIPOPER` e `TIPMOV` da OE.

## Rastreabilidade

A rotina grava o rastro do particionamento nas tabelas adicionais:

- `AD_PARTCAB`: cabecalho/lote da execucao.
- `AD_PARTOE`: OEs criadas por carreta.
- `AD_PARTITEM`: itens alocados em cada OE.

Tudo e gravado dentro da mesma transacao da geracao das OEs. Se a gravacao do rastro falhar, as OEs tambem sao desfeitas pelo rollback.

Durante a gravacao do rastro, as tabelas `AD_PARTCAB`, `AD_PARTOE` e `AD_PARTITEM` sao bloqueadas em modo exclusivo para evitar colisao na geracao dos identificadores por `MAX(ID) + 1`.

Consulta rapida para validar um particionamento:

```sql
SELECT CAB.IDPART,
       CAB.NUNOTA_ORIGEM,
       CAB.QTDE_PALETES,
       CAB.QTD_OES_GERADAS,
       CAB.STATUS,
       OE.NUNOTA_OE,
       OE.TOTAL_PALETES,
       OE.TOTAL_CAIXAS
  FROM AD_PARTCAB CAB
  JOIN AD_PARTOE OE ON OE.IDPART = CAB.IDPART
 WHERE CAB.NUNOTA_ORIGEM = :NUNOTA_ORIGEM
 ORDER BY OE.NUNOTA_OE;
```

Consulta dos itens rastreados:

```sql
SELECT ITEM.IDPART,
       ITEM.NUNOTA_ORIGEM,
       ITEM.NUNOTA_OE,
       ITEM.CODPROD,
       ITEM.QTD_ORIGEM,
       ITEM.QTD_ALOCADA,
       ITEM.QTD_PALETES,
       ITEM.QTD_CAIXAS
  FROM AD_PARTITEM ITEM
 WHERE ITEM.NUNOTA_ORIGEM = :NUNOTA_ORIGEM
 ORDER BY ITEM.NUNOTA_OE, ITEM.IDPARTITEM;
```

## Desfazer Particionamento

Criar um botao de acao com a classe:

```text
br.com.zaya.skala.DesfazerParticionamentoAction
```

O botao pode ser executado na nota origem ou em uma OE gerada pelo particionamento.

Comportamento:

- localiza o particionamento ativo em `AD_PARTCAB`/`AD_PARTOE`;
- valida se as OEs ainda estao abertas;
- remove os itens e cabecalhos das OEs geradas;
- marca `AD_PARTCAB` e `AD_PARTOE` como `DESFEITO`;
- volta `TGFCAB.AD_PARTICIONADO` da nota origem para `N`;
- executa tudo dentro de transacao.

Se houver erro durante o desfazimento, a transacao faz rollback e nada deve ficar parcialmente desfeito.

## Bloqueio de Exclusao Nativa

Registrar o evento abaixo na entidade `CabecalhoNota`:

```text
br.com.zaya.skala.BloquearExclusaoOEParticionadaEvent
```

O evento bloqueia exclusao manual de qualquer nota encontrada em `AD_PARTOE` com status ativo.

O bloqueio tambem verifica `TGFCAB.AD_PARTICIONADO = 'S'` na propria nota. As OEs geradas pelo particionamento sao criadas com esse campo marcado como `S`, para impedir exclusao pelo botao nativo.

Mensagem retornada ao usuario:

```text
Esta OE foi gerada por particionamento. Utilize a acao "Desfazer Particionamento" para desfazer o processo completo.
```

O proprio botao `Desfazer Particionamento` possui uma chave interna de bypass para conseguir remover as OEs do lote completo de forma controlada.
