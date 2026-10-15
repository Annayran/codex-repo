# Checkpoint - Particionamento Skala / Sankhya

Atualizado em: 2026-06-25

## Estado atual

Projeto Java/Gradle para particionamento de pedidos Skala no Sankhya.

Componentes principais:

- Particionamento: `br.com.zaya.skala.ParticionarPedidosAction`.
- Desfazer particionamento: `br.com.zaya.skala.DesfazerParticionamentoAction`.
- Bloqueio de exclusao nativa: `br.com.zaya.skala.BloquearExclusaoOEParticionadaEvent`.
- Algoritmo de mix/prioridade/grupos: `ParticionamentoService`.
- Leitura do payload do JSP: `ParticionamentoRequestParser`.
- Criacao das OEPs: `OEGenerator`.
- Rastreabilidade: `AD_PARTCAB`, `AD_PARTOE`, `AD_PARTITEM`.
- Vinculo nativo: `TGFVAR`.

## Regras vigentes

- TOP de OEP em homologacao/testes: `3108`.
- Pedido origem precisa estar liberado: `TGFCAB.STATUSNOTA = 'L'`.
- O pedido origem e bloqueado para novo particionamento se `TGFCAB.AD_PARTICIONADO = 'S'`.
- As OEPs geradas tambem recebem `TGFCAB.AD_PARTICIONADO = 'S'`.
- Agendamento por OEP e obrigatorio no JSP e gravado em `TGFCAB.AD_DHAGENDAMENTO`.
- `ATUALESTOQUE` e `RESERVA` dos itens seguem a TOP vigente da OEP.
- Lote/controle nao sao revalidados na customizacao; a rotina copia `CONTROLE` e `STATUSLOTE` da origem e deixa as triggers nativas validarem.

## Calculo de paletes

Regra atual:

1. Usar o volume do item (`TGFITE.CODVOL`).
2. Buscar paletizacao em `TGFVOA` por `CODPROD + CODVOL`.
3. Converter `TGFITE.QTDNEG`, tratado como UN, conforme `TGFVOA.DIVIDEMULTIPLICA/QUANTIDADE`.
4. Calcular paletes por `quantidade convertida / (TGFVOA.LASTRO * TGFVOA.CAMADAS)`.

A rotina ajusta a alocacao para gerar `QTDNEG` inteiro na OEP.

## Criacao das OEPs

Nao usa modelo/clone nativo de nota.

A OEP e criada via Java/Jape como copia controlada:

- cria cabecalho com `cabecalhoNotaDao.create()`;
- copia campos principais da origem;
- aplica `CODTIPOPER = 3108`, `DHTIPOPER`, `TIPMOV`, observacao, agendamento e flag de particionamento;
- cria itens com `itemNotaDao.create()`;
- grava `TGFVAR` item a item;
- grava rastro em `AD_PARTCAB`, `AD_PARTOE`, `AD_PARTITEM`.

## TGFVAR

Cada item de OEP gera um vinculo:

- `NUNOTA`: OEP criada.
- `SEQUENCIA`: item da OEP.
- `NUNOTAORIG`: pedido origem.
- `SEQUENCIAORIG`: item original.
- `QTDATENDIDA`: `QTDNEG` gerado na OEP.
- `STATUSNOTA`: `'L'`.

Durante insert de `TGFVAR`, a rotina usa `VARIAVEIS_PKG.V_ADIARATUALIZACAOORIGEM = 'S'` para nao somar `QTDENTREGUE` na origem.

## Desfazer particionamento

Fluxo atual:

1. Localiza particionamento ativo pela nota origem ou por uma OEP.
2. Para cada OEP, na mesma sessao JDBC:
   - seta `VARIAVEIS_PKG.V_ADIARATUALIZACAOORIGEM = 'S'`;
   - exclui primeiro `TGFVAR`;
   - exclui `TGFITE`;
   - exclui `TGFCAB`;
   - reseta `VARIAVEIS_PKG.V_ADIARATUALIZACAOORIGEM = 'N'`.
3. Marca `AD_PARTCAB` e `AD_PARTOE` como `DESFEITO`.
4. Volta `TGFCAB.AD_PARTICIONADO` da origem para `N`.

Nao usar `STP_SET_ATUALIZANDO`; essa procedure nao existe na base validada.

## JSP / HTML5

Arquivo externo:

`C:\Users\annayran.ferreira\Documents\Codex\2026-06-05\files-mentioned-by-the-user-intpdd\work\modalParticionamentoPedidos.zip`

O ZIP deve conter apenas `modalParticionamentoPedidos.jsp` na raiz.

Estado atual do JSP:

- Nomenclatura amigavel para usuario final.
- Sem aba redundante `Itens das OEs`.
- Sem manipulacao de `window.parent` no JSP.
- Ajustes visuais do modal Sankhya ficam na chamada JS do botao/action, nao dentro do JSP.

## Artefatos

- JAR: `build/libs/PARTICIONAMENTOSKALA-1.0-SNAPSHOT.JAR`.
- Guia de teste: `output/pdf/ZY-SKALA-2026-PART-JSP-GUIA-TESTES.pdf`.
- Guia editavel: `output/pdf/ZY-SKALA-2026-PART-JSP-GUIA-TESTES.md`.

## Ultima validacao local

Em 2026-06-25, `.\gradlew.bat test runScenarios jar` passou apos remover `STP_SET_ATUALIZANDO` do desfazer e manter a exclusao explicita da `TGFVAR` antes de `TGFITE`.

## Proxima retomada

Subir o JAR e o ZIP no ambiente Sankhya/Skala e aguardar testes finais de Andre Siqueira.
