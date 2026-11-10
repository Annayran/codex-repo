# Nível 1 — automação da provisão

Objetivo: automatizar a rotina já existente, mantendo o botão manual, a tela e
a prioridade manual atuais.

## Antes de começar

1. Confirmar conexão `USER/SCHEMA = TESTE/TESTE`.
2. Desabilitar temporariamente qualquer ação agendada da fila.
3. Não usar o botão 195 durante o deploy.
4. Exportar o DDL atual de:
   - `TRG_IUD_TGFITE_FILRES_PRA`;
   - `STP_GRAVAFILA_PRA`;
   - `STP_ATUALIZA_PROV_FILA_PRA`;
   - `STP_ATUALIZA_PROV_FILA_BTN`.
   - `STP_SETSEQPREVEST_PRA`.
5. Executar `validation/00_PRE_NIVEL1.sql` e guardar o resultado.

## Arquivos para executar, nesta ordem

1. `../../../candidate/fase2/deploy/01_PRA_PKG_FILARES.sql`
2. `../../../candidate/fase2/deploy/03_STP_GRAVAFILA_PRA.sql`
3. `../../../candidate/fase2/deploy/04_STP_ATUALIZA_PROVISOES.sql`
4. `../../../candidate/fase2/deploy/05_STP_ATUALIZA_PROV_FILA_BTN.sql`
5. `../../../candidate/fase2/deploy/07_TRG_IUD_TGFITE_FILRES_PRA.sql`
6. `../../../procedures/STP_SETSEQPREVEST_PRA_2026-07-20.sql`
7. Executar `validation/01_POS_NIVEL1.sql`.
8. Publicar `../../../../build/libs/proauto-fila-reserva.jar` no Cadastro de
   Módulos Java.

Não executar no Nível 1: `00`, `02`, `06`, `08`, `09`, `10` ou `11`.

## Configuração Sankhya

- Botão 195 continua apontando para `STP_ATUALIZA_PROV_FILA_BTN`.
- Ação 161 continua apontando para `STP_SETSEQPREVEST_PRA`; sua lógica funcional
  é preservada e recebe apenas o bloqueio transacional antes das leituras.
- Classe da ação agendada:
  `br.com.zaya.proauto.filareserva.sankhya.schedulers.AtualizaProvisaoScheduledAction`.
- Cadastrar a ação inicialmente desabilitada.

## Teste controlado

1. Criar pela interface um pedido participante dentro da janela.
2. Criar outro pedido participante fora de 120 dias úteis.
3. Confirmar `PROVISAO = N` para o primeiro e `S` para o segundo.
4. Executar o botão 195 uma vez.
5. Executar a ação agendada manualmente uma vez, ainda sem recorrência.
6. Alterar uma prioridade pela ação 161.
7. Executar novamente botão/agendamento e confirmar que a prioridade manual
   ativa não foi desfeita.
8. Só então definir a periodicidade e observar o log.

## Critério de aprovação

- todos os objetos `VALID`;
- `USER_ERRORS` vazio para os objetos do nível;
- botão e scheduler terminam sem erro;
- pedidos são classificados pelos 120 dias úteis;
- nenhuma posição manual ativa é reordenada pelo scheduler;
- não há `COMMIT` autônomo nas procedures candidatas.

## Se quebrar

Parar imediatamente e seguir `rollback/README.md`. Não iniciar o Nível 2.
