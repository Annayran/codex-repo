# Guia de Testes do JSP de Particionamento

**Documento:** ZY-SKALA-2026-PART-JSP-GUIA-TESTES-001  
**Projeto:** Skala - Particionamento de Pedidos  
**Versao:** v1.0  
**Data:** 19 de junho de 2026  
**Tecnologia:** Sankhya - JSP HTML5 Component / Action Java / Oracle SQL

## Objetivo

Orientar o teste funcional do modal JSP de particionamento de pedidos, cobrindo leitura dos itens da OE, calculo de paletes, composicao por mix de SKUs, prioridade, grupos, simulacao das OEPs, agendamento por OEP e envio para a rotina Java no Sankhya.

## Visual e legendas

| Elemento | Como interpretar |
|---|---|
| OE | Nota/pedido de origem selecionado no Sankhya. |
| Capacidade | Quantidade maxima de paletes por OEP. Para teste, usar 1 ajuda a forcar mais OEPs. |
| Mix | Composicao da OEP com varios SKUs do pedido origem. |
| Prioridade | SKU priorizado entra primeiro, mas ainda respeita a regra de mix. |
| Grupo | Classificacao visual/operacional dos SKUs. Nao significa consumir um grupo inteiro sozinho. |
| Calculo | Paletes = QTDNEG / (LASTRO * CAMADAS). |
| Mix OK | A OEP possui mais de um SKU. |
| Sobra final | OEP incompleta por falta de saldo para fechar outra OEP. |
| SKU unico | Ponto de atencao: OEP cheia com apenas um SKU quando havia mix disponivel. |

## Fluxo recomendado de teste

1. Abrir o modal a partir da OE/pedido selecionado.
2. Conferir a capacidade por OEP.
3. Abrir a OE na aba OEs e revisar SKUs, prioridade, grupo e ordem.
4. Conferir a aba Calculo de Palete.
5. Clicar em Simular.
6. Conferir OEPs previstas, ocupacao, SKUs e status.
7. Informar Agendamento da OEP em cada card.
8. Enviar para particionar.

## Como o mix funciona

A regra atual e por SKU selecionado. A rotina considera apenas itens marcados para uso. Ao montar uma OEP, ela distribui a capacidade entre os SKUs que ainda possuem saldo, evitando uma OEP completa com um unico SKU quando existem outros SKUs disponiveis.

- Se houver prioridade, os priorizados entram primeiro na ordem informada.
- Prioridade nao significa consumir o SKU sozinho.
- Se sobrar apenas um SKU no final, a ultima OEP pode ficar como sobra final.
- O calculo oficial e `QTDNEG / (LASTRO * CAMADAS)`.

## Agendamento por OEP

Cada OEP simulada deve receber data/hora no campo **Agendamento da OEP**. Esse valor e enviado no payload e gravado em `TGFCAB.AD_DHAGENDAMENTO` na OE criada. Se faltar agendamento, o JSP bloqueia o envio.

## Validacoes do backend

| Validacao | Resultado esperado |
|---|---|
| `TGFCAB.STATUSNOTA = 'L'` | Somente pedido liberado pode particionar. |
| TOP permite particionamento | Bloqueia quando `TGFTOP.AD_PERMITEPART` nao permite. |
| Pedido ja particionado | Bloqueia novo particionamento. |
| LASTRO e CAMADAS | Produto sem paletizacao valida bloqueia. |
| Agendamento por OEP | Toda OEP deve ter `AD_DHAGENDAMENTO`. |
| Rastreabilidade | Registra origem, OEs criadas e itens alocados. |

## Cenarios de teste

| Cenario | Entrada sugerida | Resultado esperado |
|---|---|---|
| Teste basico | OE liberada, TOP permitida, capacidade 28 | Gerar OEPs conforme total de paletes. |
| Mix forcado | Capacidade 1, varios SKUs selecionados | OEPs completas com Mix OK. |
| Prioridade | Marcar SKUs com ordem 1, 2, 3 | Priorizados entram primeiro sem quebrar o mix. |
| Sobra | Total de paletes nao multiplo da capacidade | Ultima OEP incompleta/sobra final. |
| Sem agendamento | Deixar uma OEP sem data/hora | JSP bloqueia envio. |
| Pedido nao liberado | `STATUSNOTA <> 'L'` | Action bloqueia. |
| Produto sem paletizacao | Sem LASTRO ou CAMADAS | Rotina bloqueia. |
| Desfazer | OEs abertas e rastreadas | Desfaz somente o particionamento daquela origem. |

## Checklist de aceite

- [ ] Modal abre com OE selecionada.
- [ ] Itens reais carregam da TGFITE/TGFPRO.
- [ ] Calculo usa QTDNEG / (LASTRO * CAMADAS).
- [ ] Simulacao mostra OEPs, ocupacao, SKUs, grupos e status.
- [ ] OEP completa com varios SKUs aparece como Mix OK.
- [ ] Toda OEP exige agendamento antes do envio.
- [ ] Pedido nao liberado nao particiona.
- [ ] Action grava `TGFCAB.AD_DHAGENDAMENTO` em cada OE criada.
- [ ] Rastreabilidade registra origem, OEs e itens.
- [ ] ZIP do HTML5 Component contem somente `modalParticionamentoPedidos.jsp` na raiz.
