# Checklist GO/NO-GO — Fila de Reserva Fase 2

Data prevista da apresentação: 21/08/2026.

O status somente pode ser `GO` se todos os itens críticos estiverem aprovados e
as evidências estiverem anexadas à OS/homologação.

## Plataforma e objetos

- [ ] Build Java 8 publicado e carregado pelo Sankhya 4.36b124.
- [ ] Todos os objetos Oracle estão `VALID`.
- [ ] `USER_ERRORS` não retorna erros.
- [ ] JAR anterior e DDLs anteriores estão disponíveis.
- [ ] Rollback dos dois níveis foi revisado por quem executará a publicação.

## Automação e processo manual

- [ ] Pedido dentro da janela recebe `PROVISAO = N`.
- [ ] Pedido além de 120 dias úteis recebe `PROVISAO = S`.
- [ ] Calendário considera os feriados aplicáveis da empresa.
- [ ] Botão 195 executa a mesma regra do scheduler.
- [ ] Scheduler executa sem duplicidade.
- [ ] Ação 161 mantém a prioridade manual após nova execução automática.
- [ ] Fragmentos manuais preservam quantidade total e provisão individual.

## Estoque futuro, faturamento e OE

- [ ] Compra `TIPMOV = C`, confirmada e pendente, entra no estoque futuro.
- [ ] Estoque futuro aparece em `AD_VGFRESEST` e `AD_VGFPROVEST`.
- [ ] Faturamento parcial consome somente a quantidade liberada.
- [ ] Tentativa acima do saldo livre é bloqueada.
- [ ] Estorno devolve somente o delta anteriormente consumido.
- [ ] Exceção da TOP 906 permanece funcionando.
- [ ] Fluxo real de OE atualiza/valida o pedido de origem.
- [ ] Matriz das 72 TOPs foi executada e evidenciada.

## Dados e regressão

- [ ] Posições repetidas conhecidas não aumentaram.
- [ ] Os 15 itens fragmentados/mistos foram revisados.
- [ ] Divergência de itens sem `DTPREV` foi registrada e aceita pelo responsável.
- [ ] Totais antes/depois das filas foram comparados.
- [ ] Tempo de execução das views e do scheduler foi considerado aceitável.

## Decisão

- [ ] GO — todos os itens críticos aprovados.
- [ ] NO-GO — existe falha funcional, objeto inválido, erro de transação,
      divergência de saldo, OE não comprovada ou rollback indisponível.

Responsável técnico: _______________________________________________

Responsável funcional/cliente: ____________________________________

Data/hora da decisão: _____________________________________________

