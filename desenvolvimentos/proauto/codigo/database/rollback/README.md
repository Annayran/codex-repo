# Rollback — Fila de Reserva Fase 2

Os fontes atuais estão preservados em `database/baseline/2026-08-13/`.

O rollback executável será criado junto com cada script de deploy, porque a ordem de restauração depende dos objetos efetivamente alterados. Não utilizar o baseline como rollback automático sem:

1. Backup restaurável do ambiente;
2. Registro do status e `LAST_DDL_TIME` anterior;
3. Validação das dependências;
4. Janela de homologação;
5. Conferência em `ALL_ERRORS` após restauração;
6. Smoke test de pedido, fila, faturamento e troca manual.

A restauração deverá respeitar dependências: funções usadas pela view, view, procedures consumidoras e triggers por último.

