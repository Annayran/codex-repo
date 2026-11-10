# Matriz de decisões — Fila de Reserva Fase 2

Nenhuma decisão pendente deve ser substituída por hardcode.

| ID | Decisão | Status | Responsável | Impacto |
|---|---|---|---|---|
| RN-01 | `DTPREVENT` é o referencial principal da prioridade automática | Confirmada no PDD | Negócio | View, saldo e testes |
| RN-02 | Pedido fora da janela permanece como provisão | Confirmada no PDD | Negócio | Gravação e ação agendada |
| RN-03 | Compra confirmada e pendente participa da cobertura planejada | Confirmada no PDD | Compras | Sprint 2 |
| RN-04 | Provisão não libera OE | Confirmada no PDD | Logística | Sprint 3 |
| RN-05 | Alerta não altera data negociada | Confirmada no PDD | Negócio | Notificação |
| RN-06 | Janela nacional: 120 dias | Confirmada na resposta do cliente em 18/08/2026 | Proauto | Provisão e parametrização |
| RN-07 | Janela importada: 120 dias úteis, igual à nacional | Confirmada na resposta do cliente em 18/08/2026 | Proauto | Provisão e ação agendada |
| RN-08 | Compra futura elegível: pedido de compra confirmado e pendente (`TIPMOV = 'C'`) | Confirmada na resposta do cliente em 18/08/2026 | Compras/Proauto | Estoque futuro nas views |
| RN-09 | Manter o escopo atual por produto, sem nova separação por empresa, local, lote, série ou controle | Confirmada na resposta do cliente em 18/08/2026 | Compras/Estoque | Preservar comportamento consolidado |
| RN-10 | A prioridade manual prevalece por decisão do usuário e continua persistida em `AD_TGFSEQENT.SEQENT` | Confirmada na resposta do cliente em 18/08/2026 | Comercial/Proauto | Preservar ação manual existente |
| RN-11 | Impedir OE no faturamento/liberação física por `STP_VALIDAFAT_FILARES_PRA`, integrada ao saldo das views | Confirmada na resposta do cliente em 18/08/2026 | Logística/Proauto | Sprint 3 |
| RN-12 | Destinatários, canal, frequência e reenvio | Pendente | Gestão/Proauto | Alertas |
| RN-13 | Atualizar ativos e pendentes sem previsão em `AD_TGFSEQENT`; saneamento atribuído à Fase 1 | Divergência de evidência: exportação atual atingiu 5.000 registros sem `DTPREV`, com 135 fontes candidatas | Comercial/Proauto | Não executar novo saneamento; obter contagem integral e validar com o cliente |
| RN-14 | Campo de origem nacional/importado | Não necessário para a janela; mesma regra confirmada para ambos | Cadastro/Proauto | Sem diferenciação de prazo |
| RN-15 | Ações 161/195 usam transação controlada pelo Sankhya; atualização automática deve chamar `STP_ATUALIZA_PROV_FILA_PRA` | Mecanismo confirmado; frequência de cadastro é configuração técnica | TI/Proauto | Commit/rollback e agendamento |
| RN-16 | Criam reserva os pedidos de venda participantes do processo, com `AD_FILARESERVA = 'S'`; operações fora desse critério não criam posição | Confirmada na resposta do cliente em 18/08/2026 | Estoque/Proauto | Trigger e testes das TOPs |
| RN-17 | Não ampliar o bloqueio por empresa, local, lote/controle ou alçada; manter corte manual e comportamento atual | Confirmada na resposta do cliente em 18/08/2026 | Estoque/Proauto | Preservar regra consolidada |

## Gate da Sprint 1

As decisões funcionais de provisão, prioridade manual, compras futuras, proteção de estoque e OE foram fechadas pelo documento `ZY-PROA-2026-VALFIL-001`. Os gates restantes são técnicos: validar o calendário empresarial da versão 4.36, verificar o saneamento dos 135 registros, testar as TOPs afetadas, corrigir concorrência/transações e homologar rollback.

Não implementar ampliação da regra de inventário por empresa, local, lote, controle ou alçada, pois a resposta do cliente determina a manutenção do comportamento atual.
