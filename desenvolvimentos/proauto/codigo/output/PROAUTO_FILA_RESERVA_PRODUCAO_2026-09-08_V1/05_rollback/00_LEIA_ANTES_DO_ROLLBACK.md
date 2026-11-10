# Rollback

## Trigger

O rollback oficial deve ser o DDL exportado do ambiente de produção imediatamente antes da publicação. O arquivo `BASE_RECEBIDA_21-08_RESTORE_TRG_IUD_TGFITE_FILRES_PRA.sql` é somente a baseline recebida em 21/08/2026 e pode estar desatualizado.

Não execute a baseline sem conferir que ela corresponde à fonte substituída na janela.

## Notificação

`02_DROP_PRA_ACTION_NOTIF_COMPRA_INT.sql` remove a procedure. Antes disso, desative a ação agendada no Sankhya. O rollback não apaga avisos já gravados em `TSIAVI`.

