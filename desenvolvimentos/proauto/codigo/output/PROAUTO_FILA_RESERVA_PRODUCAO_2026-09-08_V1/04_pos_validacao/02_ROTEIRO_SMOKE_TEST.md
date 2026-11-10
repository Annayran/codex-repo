# Smoke test do Lote A

Executar em janela controlada e com acompanhamento funcional. Não usar pedidos produtivos sem autorização.

1. Confirmar que a trigger está `VALID` e sem linhas em `ALL_ERRORS`.
2. Preparar ou identificar um item nacional de teste com TOP marcada para fila.
3. Validar uma data dentro e outra fora do limite `DTNACINT`.
4. Preparar ou identificar um item internacional (`ORIGPROD` 1, 2, 6 ou 7).
5. Validar uma data dentro e outra fora do limite `DTINTERINT`.
6. Confirmar na `AD_TGFSEQENT` a provisão calculada para os quatro cenários.
7. Validar aumento de `QTDENTREGUE`: a rotina de faturamento deve ser chamada uma única vez.
8. Validar redução/cancelamento de `QTDENTREGUE`: a rotina deve ser chamada uma única vez com ação `DELETE`.
9. Confirmar que uma alteração sem mudança em `QTDENTREGUE` não movimenta quantidade entregue indevidamente.
10. Conferir a tela `AD_VGFRESEST` e o fluxo já existente de gravação pela `STP_GRAVAFILA_PRA`.

Registrar NUNOTA, sequência, cenário, resultado esperado, resultado obtido e responsável pela validação. Em falha, interromper e aplicar o DDL de rollback capturado antes do deploy.

