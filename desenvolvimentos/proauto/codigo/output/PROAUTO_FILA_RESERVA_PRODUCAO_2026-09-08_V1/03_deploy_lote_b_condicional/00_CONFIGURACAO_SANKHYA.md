# Lote B — configuração condicional

Este lote não deve ser executado/ativado até a aprovação de `DTNOTINT`, `GRPCOMINT`, dos destinatários e do uso de `TSIAVI/TGFNUM`.

Depois do deploy e da homologação da procedure:

1. cadastrar uma Ação Agendada no Sankhya para `PRA_ACTION_NOTIF_COMPRA_INT`;
2. definir frequência e horário aprovados pelo cliente;
3. deixar **Controle de transação manual desmarcado**;
4. executar primeiro em homologação com grupos de teste;
5. validar que a segunda execução para o mesmo pedido/grupo/data não gera aviso duplicado;
6. só então ativar em produção.

Não há `COMMIT` dentro da procedure. Com o controle manual desmarcado, o próprio Sankhya controla commit e rollback da execução.

