# Decisions - Particionamento Skala

Atualizado em: 2026-06-25

## Decisoes vigentes

- O calculo de paletes usa o volume do item: `TGFITE.CODVOL` + `TGFVOA` por `CODPROD + CODVOL`.
- `TGFITE.QTDNEG` e tratado como UN; a rotina converte para o volume do pedido usando `TGFVOA.DIVIDEMULTIPLICA/QUANTIDADE`.
- Nao ha fallback para `AD_UNDPALETE` ou campos antigos.
- Paletizacao especial por cliente e regra de "aceita saldo" estao fora do escopo atual.
- A rotina nao pode gerar `QTDNEG` fracionado na OEP.
- TOP de OEP permanece `3108` para testes atuais.
- O particionamento nao usa modelo/clone nativo de nota; cria a OEP por copia controlada via Java/Jape.
- Pedido precisa estar liberado (`TGFCAB.STATUSNOTA = 'L'`).
- Pedido/OEP particionados usam `TGFCAB.AD_PARTICIONADO = 'S'`.
- O backend bloqueia novo particionamento se a origem ja estiver particionada.
- A exclusao nativa de OEP particionada deve ser bloqueada; desfazer ocorre pelo botao customizado.
- Cada OEP exige agendamento e grava `TGFCAB.AD_DHAGENDAMENTO`.
- `ATUALESTOQUE` e `RESERVA` dos itens da OEP seguem a TOP vigente da OEP.
- Validacoes de lote/controle ficam nas triggers nativas; a customizacao apenas copia `CONTROLE` e `STATUSLOTE`.
- `TGFVAR` deve ser gravada item a item para manter integridade nativa entre OEP e origem.
- Insert de `TGFVAR` deve usar `VARIAVEIS_PKG.V_ADIARATUALIZACAOORIGEM = 'S'` para nao somar `QTDENTREGUE`.
- No desfazer, excluir `TGFVAR` antes de `TGFITE`, mantendo `VARIAVEIS_PKG.V_ADIARATUALIZACAOORIGEM = 'S'` durante esse trecho.
- Nao usar `STP_SET_ATUALIZANDO`; a procedure nao existe na base validada.
- JSP nao deve manipular `window.parent` nem alterar o modal pai do Sankhya. Ajustes de tamanho/titulo/X ficam na chamada JS do botao/action.
- Textos do JSP devem ser amigaveis para usuario final; nomes tecnicos ficam apenas em consulta/payload interno.
