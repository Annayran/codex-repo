# Deploy completo em TESTE — 19/08/2026

Objetivo: instalar hoje os dois níveis da Fase 2 para homologação final em
20/08/2026 e preparação do GO LIVE em 21/08/2026.

Não executar em produção. Mesmo na mesma janela, manter os pontos de parada A e
B. Se um ponto falhar, executar o rollback correspondente antes de continuar.

## 0. Preparação obrigatória

1. Confirmar `USER/SCHEMA = TESTE/TESTE`.
2. Desabilitar a ação agendada da fila.
3. Suspender temporariamente botão 195, ação 161, faturamento e alterações de
   pedidos durante o deploy.
4. Exportar o DDL atual dos nove objetos substituídos:
   - `TRG_IUD_TGFITE_FILRES_PRA`;
   - `STP_GRAVAFILA_PRA`;
   - `STP_ATUALIZA_PROV_FILA_PRA`;
   - `STP_ATUALIZA_PROV_FILA_BTN`;
   - `STP_SETSEQPREVEST_PRA`;
   - `STP_VALIDAFAT_FILARES_PRA`;
   - `AD_VGFRESEST`;
   - `AD_VGFPROVEST`;
   - `AD_VGFFRPROD`.
5. Executar e salvar:
   - `../nivel1_automacao/validation/00_PRE_NIVEL1.sql`;
   - `../nivel2_estoque_oe/validation/00_PRE_NIVEL2.sql`.

## A. Instalar automação

Executar um arquivo por vez:

1. `../../../candidate/fase2/deploy/01_PRA_PKG_FILARES.sql`
2. `../../../candidate/fase2/deploy/03_STP_GRAVAFILA_PRA.sql`
3. `../../../candidate/fase2/deploy/04_STP_ATUALIZA_PROVISOES.sql`
4. `../../../candidate/fase2/deploy/05_STP_ATUALIZA_PROV_FILA_BTN.sql`
5. `../../../candidate/fase2/deploy/07_TRG_IUD_TGFITE_FILRES_PRA.sql`
6. `../../../procedures/STP_SETSEQPREVEST_PRA_2026-07-20.sql`
7. Executar `../nivel1_automacao/validation/01_POS_NIVEL1.sql`.

### Ponto de parada A

Continuar somente se todos os objetos estiverem `VALID`, `USER_ERRORS` estiver
vazio e a janela retornar 120. Em caso de falha, executar o rollback do Nível 1
e não instalar o Nível 2.

## B. Instalar estoque futuro e proteção

1. `../../../candidate/fase2/deploy/06_STP_VALIDAFAT_FILARES_PRA.sql`
2. `../../../candidate/fase2/deploy/08_AD_VGFRESEST.sql`
3. `../../../candidate/fase2/deploy/10_AD_VGFPROVEST.sql`
4. `../../../candidate/fase2/deploy/11_AD_VGFFRPROD.sql`
5. Executar `../nivel2_estoque_oe/validation/01_POS_NIVEL2.sql`.

### Ponto de parada B

Continuar somente se os quatro objetos estiverem `VALID`, `USER_ERRORS` estiver
vazio e as três views responderem. Se apenas o Nível 2 falhar, executar seu
rollback e manter o Nível 1 para diagnóstico.

## C. Publicar e cadastrar o JAR

1. Publicar `../../../../build/libs/proauto-fila-reserva.jar` no Cadastro de
   Módulos Java.
2. Preservar o JAR anterior para retorno.
3. Cadastrar a classe:
   `br.com.zaya.proauto.filareserva.sankhya.schedulers.AtualizaProvisaoScheduledAction`.
4. Manter a ação sem recorrência.
5. Executá-la manualmente uma vez após os testes do botão.
6. Habilitar recorrência somente depois da homologação funcional.

## D. Smoke test ainda na janela

1. Abrir a tela de fila e consultar produtos conhecidos.
2. Executar o botão 195 uma vez.
3. Executar a ação agendada manualmente uma vez.
4. Conferir log sem exceção.
5. Abrir `AD_VGFRESEST`, `AD_VGFPROVEST` e `AD_VGFFRPROD`.
6. Confirmar que a ação 161 abre seu formulário e mantém os parâmetros.
7. Não realizar GO LIVE com base apenas neste smoke test; executar o checklist
   de homologação no dia seguinte.

## Rollback completo

Se for necessário remover toda a Fase 2:

1. executar primeiro `../nivel2_estoque_oe/rollback/README.md`;
2. executar depois `../nivel1_automacao/rollback/README.md`;
3. validar novamente os objetos baseline;
4. manter o scheduler desabilitado.

## Arquivos que não devem ser executados

- `00_PRA_FILARES_LOCK.sql`;
- `02_PRA_FNC_ESTOQUE_FUTURO.sql`;
- `09_SANEAR_SEM_PREVISAO.sql`.

Os dois primeiros foram descontinuados; o terceiro é somente auditoria e não
faz parte deste deploy.

