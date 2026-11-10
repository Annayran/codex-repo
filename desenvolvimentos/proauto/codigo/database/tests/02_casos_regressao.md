# Casos de regressão — Fila de Reserva

## Massa real identificada

| Caso | Produto | Objetivo |
|---|---:|---|
| CT-01 | 80 | A entrega de 27/03/2026 deve preceder 04/05/2026 e consumir saldo primeiro |
| CT-02 | 22360 | Manter 23/03/2026 antes de 06/05/2026 |
| CT-03 | 22595 | Quatro itens com mesmo `SEQENT`; três com mesma previsão devem ter desempate estável |
| CT-04 | 37717 | Mesma previsão e `SEQENT` devem resultar em posição determinística |
| CT-05 | - | Os 135 itens sem previsão devem seguir a RN-13 aprovada |

## Fluxos obrigatórios

1. Inserção de pedido elegível.
2. Alteração de previsão para data anterior e posterior.
3. Alteração de quantidade sem aumento permitido.
4. Faturamento total.
5. Faturamento parcial.
6. Estorno/redução de `QTDENTREGUE`.
7. Exclusão de item.
8. Cancelamento de pedido.
9. Entrada e saída de provisão no limite exato.
10. Troca manual total.
11. Troca manual parcial.
12. Duas sessões processando o mesmo produto.
13. Exceção após gravação da fila para comprovar rollback.
14. Reprocessamento repetido sem duplicar posição, quantidade ou alerta.

## Evidência esperada

Para cada caso registrar estado anterior, operação, mensagem, estado posterior, status dos objetos e resultado do rollback. Não executar em produção.

