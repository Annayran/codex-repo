# Prototipo - Modal Momento da Negociacao

Este prototipo abre um componente HTML5 dentro do popup do botao de acao do Sankhya.

Objetivo da fase atual:

- Nao alterar dados.
- Receber `NUNEGOCIACAO` da linha selecionada.
- Mostrar o momento da negociacao em uma tela maior e mais legivel.
- Preparar a estrutura para, no futuro, chamar acoes de validacao/cancelamento por etapa.

## Arquivos

- `botao-acao/momentoNegociacaoModalButton.js`
  - Script para cadastrar em uma Acao do tipo JavaScript.
  - Abre o modal com iframe.
  - Passa `nunegociacao` e `modo=SIMULACAO`.

- `html5-component/modalMomentoNegociacao.jsp`
  - Pagina inicial do componente HTML5.
  - Hoje esta mockada para teste visual.
  - Pode ser evoluida para consultar backend/servico por `nunegociacao`.

## Parametros esperados

O iframe recebe:

- `entryPoint=modalMomentoNegociacao.jsp`
- `nuGdg=<ID do componente BI/HTML5>`
- `nunegociacao=<NUNEGOCIACAO selecionado>`
- `modo=SIMULACAO`

## Acao Java de referencia

- Codigo: `299`
- Nome: `[ZAYA.IT] - Momento Negociação - BETA V1.0`
- Ambiente/identificador informado: `RJ`

Observacao: o codigo `299` identifica a acao Java cadastrada no Sankhya. Ele nao substitui o `nuGdg`, que e o ID do componente HTML5 usado no iframe.

## Fluxo seguro sugerido

1. Cadastrar primeiro em ambiente de homologacao.
2. Criar um componente HTML5 novo, sem reaproveitar componente produtivo.
3. Publicar `modalMomentoNegociacao.jsp` como pagina inicial.
4. Ajustar `nuGdg` no script do botao.
5. Cadastrar o botao em `AD_TGLCAB` ou na tela onde exista `NUNEGOCIACAO`.
6. Testar com propostas conhecidas.

## Evolucao planejada

Fase 1:

- Modal informativo.
- Dados mockados ou somente leitura.

Fase 2:

- Modal lendo dados reais por `NUNEGOCIACAO`.
- Cards por area: Suprimento, Layout, Producao, Faturamento, Entrega.
- Checklist de impedimentos.

Fase 3:

- Botoes de acao por area em modo `DRY_RUN`.
- Cada botao mostra o que faria, mas nao grava.

Fase 4:

- Liberar acoes reais uma por vez, com log/auditoria.
