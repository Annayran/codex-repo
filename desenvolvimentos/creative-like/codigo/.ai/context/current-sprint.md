# Sprint / Ciclo Atual

## Objetivo

Estabilizar em homologacao o fluxo real de cancelamento da negociacao pelo modal `JSP/HTML5` integrado ao Java.

## Escopo Em Homologacao

- Abrir modal pela negociacao selecionada.
- Consultar Pedido(s) de Venda por `AD_VGLDOC` + `TGFCAB.TIPMOV = 'P'`.
- Consultar Nota Mov. Interna por `TGFCAB.AD_NUNEGOCIACAO`.
- Consultar itens por `TGFITE.NUNOTA`.
- Selecionar documento ativo e operar sobre seus itens.
- Exibir itens apenas depois do clique no documento relacionado.
- Exibir acompanhamentos somente quando houver `TGFACT` real/simulado para o documento selecionado.
- Cancelar item individual ou todos os itens aptos do documento.
- Cancelar exclusivamente producao por botao dedicado.
- Cancelar tudo pela mesma Action operacional, cobrindo Faturamento, Suprimento e Producao.
- Manter `TGFITE.PENDENTE = 'S'`.
- Gravar acompanhamento em `TGFACT` com `AD_ITEMCANCELADO = 'S'`.
- Bloquear recancelamento por `TGFACT.AD_ITEMCANCELADO = 'S'`.
- Permitir cancelamento de item com saldo mesmo quando o valor atual de `TGFITE.PENDENTE` estiver `N`; a rotina real marca `PENDENTE = 'S'` ao cancelar.
- Permitir processamento direto de Faturamento, Suprimento e Producao conforme regra prioritaria atual.
- Suspender producao em `TPRIPROC.STATUSPROC = 'S'` por botao dedicado ou cancelamento completo.
- Exibir logs claros no JSP para sucesso, bloqueio e erro.
- Em modo operacional, nao aplicar cancelamento simulado apos retorno da Action; recarregar/revalidar `TGFITE`/`TGFACT` reais.
- Em modo operacional, nao exibir documentos mockados quando nao houver origem real ou parametro recebido.
- Desabilitar utilitarios sem Action configurada em modo operacional.

## Proximas Acoes

- Subir JAR atualizado no modulo Sankhya.
- Reimportar ZIP HTML5 atualizado.
- Confirmar ID da Action de cancelamento no modal, atualmente `304`.
- Configurar, se existirem, Actions reais para `Notificar areas`, `Registrar pendencia` e `Solicitar aprovacao`; sem ID, ficam indisponiveis no operacional.
- Testar:
  - Pedido de Venda com item apto;
  - Nota Mov. Interna com item apto;
  - item totalmente entregue;
  - item ja cancelado;
  - cancelamento em lote;
  - tentativa repetida de cancelamento;
  - cancelamento exclusivo de producao;
  - cancelamento completo executando documentos e producao;
  - tentativa repetida de producao ja suspensa;
  - ambiente onde `TPRIPROC` nao exista/nao esteja acessivel, validando retorno controlado;
  - fluxo progressivo: negociacao -> documento -> itens -> TGFACT.
- Conferir no banco:
  - `TGFITE.PENDENTE = 'S'`;
  - `TGFACT.AD_ITEMCANCELADO = 'S'`;
  - `TGFACT.OCORRENCIAS`;
  - `TPRIPROC.STATUSPROC = 'S'`, quando aplicavel.

## A Confirmar

- Se a mensagem de `TGFACT.OCORRENCIAS` deve variar por tipo de documento ou continuar padrao.
- Se os textos de status pendente no acompanhamento sao exatamente `PEND`, `AGUARD`, `ANDAMENTO` e `PARCIAL`.
- Se `Notificar areas`, `Registrar pendencia` e `Solicitar aprovacao` terao Actions Java reais ou permanecerao utilitarios/log.
- Confirmar em homologacao se `TPRIPROC.AD_NUNEGOCIACAO` e `TPRIPROC.STATUSPROC` estao disponiveis para consulta/update pelo usuario do modulo.

## Atencoes

- Nao testar em producao antes da homologacao completa.
- Evitar upload de JAR/ZIP antigo.
- Nao usar `contexto.confirmarSimNao(...)` em Action chamada pelo JSP.
- Nao abrir `JapeSession.open()`/`execWithTX(...)` dentro da Action chamada pelo `ActionButtonsSP.executeJava`.
- A tela pode ficar defasada se outra sessao alterar os mesmos itens; o Java e a fonte final da validacao.
