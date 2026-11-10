# PROAUTO — Fila de Reserva Fase 2

Pacote para publicação controlada no schema `TESTE` em 19/08/2026.

Não executar em produção. Executar um SQL por vez e parar nos pontos de
validação. Não juntar os arquivos em uma execução única.

## Escopo de objetos

### Objeto novo criado por SQL

- `PRA_PKG_FILARES` — package Oracle com as regras compartilhadas.

Package Oracle não é tabela nem instância DWF e não é criado no Construtor de
Telas.

### Objetos existentes substituídos por `CREATE OR REPLACE`

- `STP_GRAVAFILA_PRA`;
- `STP_ATUALIZA_PROV_FILA_PRA`;
- `STP_ATUALIZA_PROV_FILA_BTN`;
- `STP_SETSEQPREVEST_PRA`;
- `TRG_IUD_TGFITE_FILRES_PRA`;
- `STP_VALIDAFAT_FILARES_PRA`;
- `AD_VGFRESEST`;
- `AD_VGFPROVEST`;
- `AD_VGFFRPROD`.

Não existe tabela, campo adicional, instância ou tela nova neste pacote.

## 1. Preparação

1. Confirmar usuário e schema com `validacao/00_PRE_DEPLOY_NIVEL1.sql`.
2. O resultado deve ser `TESTE/TESTE`.
3. Desabilitar a ação agendada.
4. Suspender temporariamente botão 195, ação 161, faturamento e alteração de
   pedidos durante a publicação.
5. Exportar novamente o DDL atual dos nove objetos substituídos.
6. Executar e salvar:
   - `validacao/00_PRE_DEPLOY_NIVEL1.sql`;
   - `validacao/02_PRE_DEPLOY_NIVEL2.sql`.

## 2. Deploy — bloco A: automação

Executar exatamente nesta ordem:

1. `deploy/01_PRA_PKG_FILARES.sql`
2. `deploy/02_STP_GRAVAFILA_PRA.sql`
3. `deploy/03_STP_ATUALIZA_PROV_FILA_PRA.sql`
4. `deploy/04_STP_ATUALIZA_PROV_FILA_BTN.sql`
5. `deploy/05_TRG_IUD_TGFITE_FILRES_PRA.sql`
6. `deploy/06_STP_SETSEQPREVEST_PRA.sql`
7. `validacao/01_POS_DEPLOY_NIVEL1.sql`

### Ponto de parada A

Somente continuar se todos os objetos estiverem `VALID`, `USER_ERRORS` não
retornar linhas e a janela retornar 120. Se falhar, executar o rollback do
bloco A e não instalar o bloco B.

## 3. Deploy — bloco B: estoque futuro e OE

1. `deploy/07_STP_VALIDAFAT_FILARES_PRA.sql`
2. `deploy/08_AD_VGFRESEST.sql`
3. `deploy/09_AD_VGFPROVEST.sql`
4. `deploy/10_AD_VGFFRPROD.sql`
5. `validacao/03_POS_DEPLOY_NIVEL2.sql`

### Ponto de parada B

Somente continuar se procedure e views estiverem `VALID`, `USER_ERRORS` não
retornar linhas e as três views responderem. Se falhar, executar apenas o
rollback do bloco B; o bloco A pode permanecer instalado para diagnóstico.

## 4. Módulo Java

1. Publicar `java/proauto-fila-reserva.jar` no Cadastro de Módulos Java.
2. Preservar o JAR anterior.
3. Cadastrar a classe informada em `java/INSTRUCOES_ACAO_AGENDADA.md`.
4. Manter a recorrência desabilitada.
5. Executar manualmente uma vez depois do teste do botão 195.

## 5. Smoke test

1. Abrir a tela da fila.
2. Executar o botão 195 uma vez.
3. Executar a ação agendada manualmente uma vez.
4. Conferir log sem exceção.
5. Consultar as três views.
6. Confirmar que a ação 161 mantém seus parâmetros.

## 6. Rollback

### Falha no bloco B

Executar `rollback/01` até `rollback/04`, nesta ordem.

### Falha no bloco A ou retorno completo

Depois de restaurar o bloco B, executar `rollback/05` até `rollback/10`, nesta
ordem. O package deve ser removido por último.

## Arquivos intencionalmente ausentes

- nenhuma criação de tabela;
- nenhuma criação de índice;
- nenhum `ALTER TABLE`;
- nenhum saneamento de dados;
- nenhum DML em tabela padrão Sankhya;
- nenhum arquivo do antigo lock/configuração;
- nenhuma view ou função auxiliar de estoque futuro.

