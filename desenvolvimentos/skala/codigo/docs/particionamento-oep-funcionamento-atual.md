# Documentacao funcional - Particionamento de Pedidos/OEP

Atualizado em: 2026-07-17

## Objetivo

O particionamento divide um pedido de origem em OEPs, respeitando a capacidade de paletes informada no modal, a selecao de itens, prioridade, grupos e regras de paletizacao.

A rotina foi desenhada para facilitar a separacao fisica:

- priorizar paletes inteiros nas primeiras OEPs;
- evitar fracao nas primeiras OEPs;
- deixar quebras/fracao de palete para as ultimas OEPs;
- manter a quantidade da OEP em unidade inteira;
- criar rastreabilidade entre pedido origem e OEP gerada;
- usar o vinculo nativo `TGFVAR`, sem mexer manualmente em `TGFITE.QTDENTREGUE`.

## Componentes envolvidos

| Componente | Responsabilidade |
| --- | --- |
| `modalParticionamentoPedidos.jsp` | Tela HTML5 para selecionar itens, prioridade, grupos, capacidade e simular OEPs. |
| `ParticionarPedidosAction` | Action Java chamada pelo Sankhya para validar pedido, aplicar configuracao do modal, particionar e gerar OEPs. |
| `ParticionamentoService` | Algoritmo de distribuicao dos itens entre OEPs. |
| `PedidoRepository` | Leitura do pedido origem, itens e calculo de paletes com base em `TGFVOA`. |
| `OEGenerator` | Criacao de `TGFCAB`, `TGFITE` e `TGFVAR` das OEPs. |
| `RastreabilidadeRepository` | Gravacao de `AD_PARTCAB`, `AD_PARTOE` e `AD_PARTITEM`. |
| `DesfazerParticionamentoAction` | Desfaz o particionamento e reabre o pedido origem. |

## Pre-condicoes do pedido

Antes de particionar, o backend valida:

- o pedido origem precisa existir;
- `TGFCAB.STATUSNOTA = 'L'`;
- a TOP do pedido precisa permitir particionamento por `TGFTOP.AD_PERMITEPART`;
- o pedido origem nao pode estar marcado como ja particionado em `TGFCAB.AD_PARTICIONADO = 'S'`;
- a capacidade de paletes deve ser maior que zero;
- deve existir ao menos um item valido e selecionado.

Ao final do particionamento:

- o pedido origem recebe `TGFCAB.AD_PARTICIONADO = 'S'`;
- todos os itens do pedido origem recebem `TGFITE.PENDENTE = 'N'`;
- as OEPs geradas tambem recebem `TGFCAB.AD_PARTICIONADO = 'S'`.

## Origem dos dados do modal

O modal carrega os itens diretamente do Sankhya:

- `TGFCAB`;
- `TGFITE`;
- `TGFPRO`;
- `TGFVOA`;
- opcionalmente `TGFPEM` para informacao complementar do produto.

A consulta considera o volume do item no pedido:

```text
TGFITE.CODVOL
```

A paletizacao e buscada em:

```text
TGFVOA.CODPROD + TGFVOA.CODVOL
```

Nao existe fallback para campos antigos de paletizacao. Se `TGFVOA` nao possuir `LASTRO`, `CAMADAS`, `DIVIDEMULTIPLICA` e `QUANTIDADE`, a rotina bloqueia o calculo.

## Calculo de quantidade no volume do pedido

A base interna do pedido continua sendo `TGFITE.QTDNEG`.

Para exibicao e calculo de paletes, a rotina converte a quantidade para o mesmo volume do item (`TGFITE.CODVOL`).

Campos usados:

- `TGFITE.QTDNEG`;
- `TGFITE.CODVOL`;
- `TGFVOA.DIVIDEMULTIPLICA`;
- `TGFVOA.QUANTIDADE`;
- `TGFVOA.LASTRO`;
- `TGFVOA.CAMADAS`.

Regra de conversao para quantidade no volume:

```text
Se DIVIDEMULTIPLICA = 'M':
    qtdVolume = QTDNEG / QUANTIDADE

Se DIVIDEMULTIPLICA = 'D':
    qtdVolume = QTDNEG * QUANTIDADE

Caso contrario:
    qtdVolume = QTDNEG
```

## Calculo de caixas/embalagens por palete

A quantidade por palete exibida no modal considera o mesmo volume do pedido, por exemplo `CX`.

Formula:

```text
qtdPorPalete = LASTRO * CAMADAS
```

Exemplo:

```text
LASTRO = 120
CAMADAS = 6
qtdPorPalete = 720 CX
```

## Calculo de paletes

Formula principal:

```text
paletes = qtdVolume / qtdPorPalete
```

Exemplo:

```text
qtdVolume = 3600 CX
qtdPorPalete = 720 CX
paletes = 3600 / 720 = 5,0000
```

O modal exibe valores com virgula decimal nas colunas operacionais.

## Abas do modal

### Aba OEs

Mostra as OEs/pedidos selecionados no Sankhya e os itens carregados.

Permite:

- marcar se o item entra no mix;
- marcar prioridade;
- informar/alterar grupo;
- informar ordem de prioridade;
- visualizar quantidade no volume do pedido;
- visualizar quantidade por palete;
- visualizar paletes calculados.

### Aba Calculo de Palete

Mostra a validacao do calculo item a item.

Colunas principais:

- `Quantidade`: quantidade convertida para o volume do pedido;
- `Embalagem`: volume do item (`CODVOL`);
- `Qtd. por palete`: `LASTRO * CAMADAS`, no volume do pedido;
- `Paletes`: resultado de `quantidade / qtdPorPalete`;
- `Parte inteira`: parte inteira dos paletes;
- `Quebra`: fracao menor que 1 palete.

Resumo da aba:

- `Paletes inteiros`: soma das partes inteiras dos itens selecionados;
- `Quebras`: soma das fracoes;
- `Total calculado`: soma total de paletes;
- `OEP previstas`: `ceil(total calculado / capacidade)`.

### Aba Grupos

Agrupa os itens selecionados pelo campo de grupo informado no modal.

Para cada grupo:

```text
paletesGrupo = soma dos paletes dos itens selecionados no grupo
percentualGrupo = paletesGrupo / totalPaletesSelecionados * 100
```

A exibicao e ordenada do maior para o menor volume de paletes.

Importante: atualmente o grupo e usado como referencia de organizacao/ordenacao visual e de mix. A regra critica de particionamento nao depende exclusivamente do JavaScript; o backend recalcula a distribuicao.

### Aba Simulacao

Mostra como as OEPs serao montadas antes do envio.

A simulacao replica a regra do backend:

1. itens priorizados;
2. paletes inteiros;
3. mix dos demais SKUs;
4. quebras/fracao no final.

Tambem permite informar agendamento por OEP.

O agendamento e opcional:

- se informado, grava `TGFCAB.AD_DHAGENDAMENTO`;
- se vazio, a OEP e criada sem data de agendamento.

## Capacidade por OEP

A capacidade vem do campo do modal `Paletes por OE particionada`.

Padrao atual usado em teste:

```text
28 paletes
```

Essa capacidade limita cada OEP na simulacao e no backend.

## Regras de selecao

Somente itens selecionados entram no particionamento.

Quando o modal envia configuracao:

- item nao presente no payload e desconsiderado;
- item com `selecionado = false` nao entra;
- item selecionado pode ou nao ter prioridade;
- grupo e ordem de prioridade sao aplicados ao item correspondente.

## Regra de prioridade

Prioridade e definida pelo usuario no modal.

Ordenacao:

1. menor `ordemPrioridade` primeiro;
2. se houver empate, maior quantidade de paletes primeiro.

Regra atual:

- prioridade nao e percentual;
- prioridade nao e peso;
- prioridade significa que o SKU entra antes dos demais;
- paletes inteiros dos itens priorizados entram primeiro;
- fracoes dos itens priorizados nao entram nas primeiras OEPs;
- fracoes dos priorizados vao para a fase final de quebras.

Exemplo:

```text
SKU priorizado A = 5,40 paletes
SKU priorizado B = 4,30 paletes

Fase de prioridade:
    A entra com 5 paletes inteiros
    B entra com 4 paletes inteiros

Fase final:
    A entra com 0,40
    B entra com 0,30
```

## Regra de mix sem prioridade

Quando nao existem itens priorizados, os itens selecionados sao ordenados para mix por:

1. grupo com maior total de paletes;
2. nome do grupo;
3. SKU/item com maior quantidade de paletes.

Depois disso, a distribuicao e feita por fases.

## Fases do particionamento

### Fase 1 - Primeiro palete por SKU

Para cada SKU com pelo menos 1 palete:

```text
alocar 1,0000 palete
```

Objetivo:

- espalhar os SKUs no mix;
- facilitar a separacao;
- evitar que um SKU consuma tudo sozinho quando ha varios SKUs com saldo.

### Fase 2 - Saldos inteiros

Depois do primeiro palete por SKU, a rotina pega os saldos inteiros restantes.

Esses saldos sao quebrados em blocos de 1 palete:

```text
SKU com 5,70 paletes:
    1 palete vai para a fase 1
    4 paletes inteiros viram blocos de 1 palete
    0,70 fica para a fase final
```

Os blocos inteiros nao sao balanceados por cota fracionada.

Ou seja, nao deve acontecer nas primeiras OEPs:

```text
1,1113 paletes
1,6889 paletes
27,38 / 28,00 por causa de fracao
```

Nas primeiras OEPs, a regra busca manter a paletizacao fechada, com paletes inteiros.

### Fase 3 - Quebras/fracao

As quebras menores que 1 palete ficam para o final.

Antes de abrir uma nova OEP, a rotina usa o espaco livre da ultima OEP que recebeu paletes inteiros. Uma nova OEP e criada somente quando as quebras restantes nao couberem nessa capacidade.

Objetivo:

- concentrar as fracoes na ultima OEP disponivel;
- evitar a criacao de uma OEP adicional quando ainda existe capacidade;
- facilitar a conferencia e a montagem fisica.

Exemplo:

```text
OEP 1: 28,00 / 28,00
OEP 2: 28,00 / 28,00
OEP 3: 28,00 / 28,00
OEP final: quebras/fracao
```

## Proporcionalidade e grupos

O percentual dos grupos e calculado para leitura e organizacao do mix:

```text
percentualGrupo = paletesGrupo / totalPaletesSelecionados * 100
```

A distribuicao atual nao tenta forcar exatamente o mesmo percentual em cada OEP, porque a regra prioritaria passou a ser operacional:

1. respeitar prioridade;
2. manter paletes inteiros nas primeiras OEPs;
3. distribuir por SKU em blocos de 1 palete;
4. deixar quebras no final.

Assim, os grupos ajudam a ordenar e dar proporcionalidade geral, mas sem quebrar a regra de palete fechado.

## Ajuste para quantidade inteira na OEP

Mesmo quando a divisao trabalha em paletes, a OEP precisa gravar quantidade em unidade inteira.

Para isso, antes de alocar um item, a rotina ajusta a quantidade de paletes pelo fator:

```text
unidadesPorPalete = UND_PALETE_CAD
```

O backend calcula:

```text
qtdUnidade = paletesAlocados * unidadesPorPalete
qtdCaixa = paletesAlocados * caixasPorPalete
```

Antes de gravar `TGFITE.QTDNEG`, o `OEGenerator` valida se a quantidade em unidade ficou inteira.

Se houver unidade fracionada acima da tolerancia, a rotina bloqueia a criacao da OEP com erro.

## Criacao das OEPs

A rotina nao usa clone/modelo nativo de nota.

Ela cria uma OEP por copia controlada:

### Cabecalho `TGFCAB`

Campos principais copiados/aplicados:

- empresa;
- parceiro;
- natureza;
- centro de custo;
- vendedor;
- tipo de venda;
- datas base;
- observacao;
- `CODTIPOPER = 3108`;
- versao vigente da TOP (`DHTIPOPER`);
- `TIPMOV` da TOP;
- `AD_PARTICIONADO = 'S'`;
- `AD_DHAGENDAMENTO`, quando informado.

### Itens `TGFITE`

Para cada item alocado na OEP:

- `CODPROD`;
- `QTDNEG`;
- `CODLOCALORIG`;
- `CONTROLE`;
- `STATUSLOTE`;
- `RESERVA`;
- `ATUALESTOQUE`;
- `CODVOL`;
- `USOPROD`;
- `VLRUNIT`;
- `VLRTOT`;
- campos customizados de palete/caixa da OEP.

`RESERVA` e `ATUALESTOQUE` seguem a configuracao da TOP 3108 vigente.

## TGFVAR

Para cada item criado na OEP, a rotina cria o vinculo nativo em `TGFVAR` via Jape DAO:

```text
Entidade: CompraVendavariosPedido
```

Campos usados:

```text
NUNOTA       = OEP criada
NUNOTAORIG   = pedido origem
SEQUENCIA    = sequencia do item na OEP
SEQUENCIAORIG= sequencia do item no pedido origem
QTDATENDIDA  = QTDNEG criada na OEP
```

Ponto importante:

- a customizacao nao atualiza `TGFITE.QTDENTREGUE`;
- `QTDENTREGUE` fica sob controle nativo do Sankhya a partir da `TGFVAR`.

## Rastreabilidade customizada

Alem da `TGFVAR`, a rotina grava rastreabilidade nas tabelas customizadas:

### `AD_PARTCAB`

Guarda o cabecalho do particionamento:

- `IDPART`;
- pedido origem;
- parceiro;
- TOP origem;
- capacidade;
- quantidade de OEPs geradas;
- status;
- usuario/data de criacao.

### `AD_PARTOE`

Guarda cada OEP gerada:

- `IDPART`;
- pedido origem;
- numero da OEP;
- TOP da OEP;
- total de paletes;
- total de caixas;
- status.

### `AD_PARTITEM`

Guarda os itens alocados:

- pedido origem;
- OEP;
- produto;
- quantidade origem;
- quantidade alocada;
- paletes;
- caixas.

## Marcacao de pendencia

Ao particionar:

```text
TGFITE.PENDENTE = 'N'
```

para todos os itens do pedido origem.

Ao desfazer:

```text
TGFITE.PENDENTE = 'S'
```

para todos os itens do pedido origem.

## Desfazer particionamento

O desfazer pode ser acionado pelo pedido origem ou por uma OEP.

Fluxo:

1. localizar particionamento ativo em `AD_PARTCAB` ou `AD_PARTOE`;
2. bloquear o particionamento com `FOR UPDATE`;
3. ativar `VARIAVEIS_PKG.V_ADIARATUALIZACAOORIGEM = 'S'`;
4. excluir `TGFVAR` da OEP;
5. excluir `TGFITE` da OEP;
6. excluir `TGFCAB` da OEP;
7. resetar `VARIAVEIS_PKG.V_ADIARATUALIZACAOORIGEM = 'N'`;
8. marcar rastros como `DESFEITO`;
9. voltar `TGFCAB.AD_PARTICIONADO = 'N'` no pedido origem;
10. voltar `TGFITE.PENDENTE = 'S'` nos itens da origem.

Nao e usada a procedure `STP_SET_ATUALIZANDO`, pois ela nao existe na base validada.

## Regras de exibicao

O modal exibe:

- quantidade no mesmo volume do pedido;
- quantidade por palete no mesmo volume do pedido;
- paletes e quebras com virgula decimal;
- grupos ordenados do maior para o menor volume, com codigo e descricao;
- percentual do grupo com virgula decimal;
- simulacao com quantidade, volume, grupo, paletes e prioridade, ordenada pela sequencia original do item dentro de cada OEP.

## Limites e fora de escopo atual

Nao fazem parte da regra atual:

- paletizacao especial por cliente;
- fallback para `AD_UNDPALETE` ou campos antigos;
- regra de "aceita saldo";
- manipulacao manual de `QTDENTREGUE`;
- revalidacao customizada de lote/controle antes de gravar, pois as triggers nativas continuam responsaveis.

## Resumo operacional da regra atual

1. Usuario abre o modal no pedido.
2. Modal carrega itens e calcula paletes por `TGFITE.CODVOL` + `TGFVOA`.
3. Usuario seleciona itens, grupos, prioridades e capacidade.
4. Simulacao monta OEPs usando a mesma regra do backend.
5. Backend valida pedido e configuracao.
6. Itens priorizados entram primeiro, mas somente seus paletes inteiros.
7. Demais SKUs entram em blocos de 1 palete por SKU.
8. Saldos inteiros continuam em blocos de 1 palete.
9. Quebras/fracao ficam para as ultimas OEPs.
10. OEPs sao criadas via Jape.
11. `TGFVAR` e criada por item.
12. Rastreabilidade e gravada em `AD_PARTCAB`, `AD_PARTOE` e `AD_PARTITEM`.
13. Pedido origem e marcado como particionado e seus itens ficam `PENDENTE = 'N'`.
14. Se necessario, o desfazer remove OEPs, apaga `TGFVAR`, marca rastros como desfeitos e reabre a origem.
