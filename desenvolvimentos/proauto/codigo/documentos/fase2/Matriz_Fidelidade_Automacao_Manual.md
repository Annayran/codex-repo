# Matriz de fidelidade — automação e operação manual da fila

Fonte funcional: `PROAUTO_Reserva_Fase_Resposta.pdf`, respostas Q01 a Q17.

Status desta matriz: **implementação candidata para homologação**. O desenho
automático/manual está fechado no código, mas a publicação não deve ser
classificada como 100% homologada enquanto os gates G1 e G2 não forem
comprovados no ambiente TESTE.

## Um único núcleo de regra

Não existem duas regras de ativação. A ação agendada e o botão manual chamam a
mesma procedure com contador:

`STP_ATUALIZA_PROV_FILA_PRA` → `PRA_PKG_FILARES.ATUALIZAR_PROVISOES`.

O Java apenas agenda, abre a transação e registra o resultado. Critério de 120
dias úteis, calendário, reentrada, ordenação e concorrência permanecem no banco.

## Funcionamento de ponta a ponta

1. Um item é inserido ou alterado em `TGFITE`.
2. `TRG_IUD_TGFITE_FILRES_PRA` aceita para criação da fila somente nota
   liberada, TOP com `AD_FILARESERVA = 'S'` e movimento atualmente preservado
   pelo legado (`P` ou `J`).
3. A trigger calcula a provisão pelo calendário `TSIFER` da empresa:
   - dentro de 120 dias úteis: `PROVISAO = 'N'`;
   - depois da janela: `PROVISAO = 'S'`.
4. `STP_GRAVAFILA_PRA` delega ao pacote e persiste em `AD_TGFSEQENT`.
5. A entrada/reentrada automática usa `DTPREV`; a ordem operacional final fica
   em `SEQENT`.
6. Quando a data entra na janela, há dois meios equivalentes de ativação:
   - automático: `AtualizaProvisaoScheduledAction`;
   - manual de contingência: botão 195, `STP_ATUALIZA_PROV_FILA_BTN`.
7. Ambos bloqueiam o produto, relêem a elegibilidade e mudam somente registros
   ainda provisionados de `S` para `N`. Execuções concorrentes não processam a
   mesma linha duas vezes.
8. A prioridade manual continua na ação 161,
   `STP_SETSEQPREVEST_PRA`. Ela altera `SEQENT`, pode fracionar quantidade e
   também usa o mesmo bloqueio por produto.
9. O processo automático não reordena registros já ativos (`PROVISAO = 'N'`).
   Portanto, a decisão manual prevalece até uma nova decisão explícita do
   usuário.
10. Compras confirmadas, pendentes e `TIPMOV = 'C'` formam estoque futuro nas
    duas filas: `AD_VGFRESEST` e `AD_VGFPROVEST`.
11. No faturamento/liberação, a alteração da quantidade entregue do pedido de
    origem chama `STP_VALIDAFAT_FILARES_PRA`. A rotina valida tudo antes do DML,
    permite corte parcial manual, consome o delta liberado e devolve o delta em
    estorno/cancelamento.

## Correspondência com as respostas do cliente

| Resposta | Regra confirmada | Implementação candidata | Situação |
|---|---|---|---|
| Q01–Q02 | Só operações participantes criam reserva | Trigger + `TOP_CRIAR_RESERVA` + `AD_FILARESERVA` | Implementado |
| Q03 | Manter regra atual de quantidade | `GRAVAR_FILA` preserva quantidade e fragmentos | Implementado; regressão necessária |
| Q04–Q08 | Corte parcial manual e validação existente | `STP_VALIDAFAT_FILARES_PRA` | Implementado; depende de G1 |
| Q05–Q07 | Sem ampliar por empresa/local/lote/alçada | Views preservam consolidação por produto | Implementado |
| Q09 | Sem nova alçada especial | Ação 161 preservada | Implementado |
| Q10–Q12 | 120 dias úteis, configurável, mesmo critério nacional/importado | Configuração + `TSIFER` + pacote | Implementado; calendário deve ser homologado |
| Q13 | Saneamento atribuído à Fase 1 | Script apenas de auditoria, sem DML automático | Divergência; depende de G2 |
| Q14 | Prioridade manual prevalece | Ação 161 altera `SEQENT`; automação só movimenta `S` | Implementado |
| Q15–Q16 | Compra futura nas duas visões | `TIPMOV = 'C'` nas duas views | Implementado |
| Q17 | OE não pode usar estoque reservado | Validação no pedido de origem + views | Implementado no código; depende de G1 |

## Transação e concorrência

- Scheduler: `JapeSession.open()` + `execWithTX`; qualquer exceção é propagada.
- Repositório Java: o `JdbcWrapper` abre conexão vinculada à sessão Jape.
- Procedures candidatas: sem `COMMIT` e sem `PRAGMA AUTONOMOUS_TRANSACTION`.
- Botão 195: usa a transação da ação Sankhya e chama o mesmo STP do scheduler.
- Ação 161: preserva `SAVEPOINT`/`ROLLBACK TO SAVEPOINT` apenas para colisões
  funcionais já tratadas.
- O pacote bloqueia os registros existentes de `AD_TGFSEQENT` por produto; não
  cria tabela técnica de lock.

## Gates obrigatórios antes de afirmar fidelidade total em produção

### G1 — comprovar o vínculo OE → pedido de origem

As 23 TOPs candidatas de OE recebidas têm `AD_FILARESERVA = 'N'`, portanto não
criam posição própria — coerente com Q02. O bloqueio de Q17 pressupõe que o
processo de OE/faturamento altere `TGFITE.QTDENTREGUE` do pedido enfileirado,
disparando a validação. Essa chamada precisa ser comprovada com
`validation/07_COLETA_FLUXO_OE.sql` e um teste real de OE acima do saldo livre.

### G2 — resolver a divergência de itens sem previsão

Q13 informa 135 itens saneados na Fase 1, mas a exportação atual atingiu o limite
de 5.000 registros ativos sem `DTPREV`; 135 deles tinham fonte candidata. A
contagem integral deve ser obtida com
`validation/06_VALIDAR_EVIDENCIAS_RECEBIDAS.sql` e validada com o cliente. Até
lá, data nula permanece ativa (`PROVISAO = 'N'`) para não retirar pedidos da fila
sem autorização.

## Configuração operacional ainda necessária

- publicar o JAR em homologação;
- cadastrar a classe
  `br.com.zaya.proauto.filareserva.sankhya.schedulers.AtualizaProvisaoScheduledAction`
  em Ações Agendadas;
- definir horário e periodicidade, pois o documento define o critério, mas não a
  frequência operacional;
- manter o botão 195 como execução manual do mesmo núcleo;
- executar pré-deploy, compilação Oracle, pós-deploy, matriz das 72 TOPs,
  faturamento parcial, estorno, concorrência e rollback;
- repetir o build com JARs/SDK compatíveis com Sankhya 4.36b124.
