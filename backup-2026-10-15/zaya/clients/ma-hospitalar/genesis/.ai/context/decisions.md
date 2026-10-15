# Decisoes Tecnicas

## Como registrar uma decisao

Use o formato:

### AAAA-MM-DD - Titulo da decisao

**Contexto:**  
Descricao breve.

**Decisao:**  
O que foi decidido.

**Motivo:**  
Por que foi decidido.

**Impacto:**  
Consequencias tecnicas ou operacionais.

---

## Decisoes Registradas

### 2026-06-30 - Manter contexto de IA no projeto

**Contexto:**  
O projeto precisa ser retomado em sessoes futuras sem depender de conversas longas.

**Decisao:**  
Centralizar arquitetura, stack, padroes, regras, decisoes, pendencias e checkpoints em `.ai/context`.

**Motivo:**  
Reduzir consumo de tokens e preservar contexto util.

**Impacto:**  
Novas conversas devem comecar lendo `.ai/context`.

---

### 2026-07-01 - Usar Gradle Java 8 como build oficial

**Contexto:**  
A customizacao deve seguir o padrao usado para projetos Sankhya da Zaya.

**Decisao:**  
Usar Gradle com Java 8, source sets `src/main/java` e `src/sankhya/java`, dependencias Sankhya locais em `C:/zy-tools/modulos-sankhya/Dependencias` e testes JUnit 5.

**Motivo:**  
Alinhar o projeto ao ambiente de desenvolvimento/publicacao esperado.

**Impacto:**  
O artefato final e `MA-HOSPITALAR-GENESIS-1.0-SNAPSHOT.jar`. Nao recriar Maven/POM.

---

### 2026-07-01 - Usar AD_GENMOVEST como tabela de controle

**Contexto:**  
A integracao precisa rastrear fila, status, tentativas, retorno HTTP, erros e idempotencia.

**Decisao:**  
Usar a tabela `AD_GENMOVEST` como controle/fila da integracao.

**Motivo:**  
Nome curto, aderente a objeto adicional Sankhya e suficiente para Genesis Movimento Estoque.

**Impacto:**  
Fluxo Java deve registrar pendencia antes do POST e atualizar a tabela apos o retorno da API.

---

### 2026-07-01 - Smoke tests da API devem ser opt-in

**Contexto:**  
Chamadas reais podem consultar ou criar registros na Genesis.

**Decisao:**  
Testes reais de API ficam marcados com `@Tag("smoke")` e exigem variaveis de ambiente como `GENESIS_SMOKE_ENABLED`, `GENESIS_BASE_URL` e `GENESIS_TOKEN`.

**Motivo:**  
Evitar chamadas reais acidentais e nao versionar credenciais.

**Impacto:**  
`gradlew test` permanece seguro. Chamadas reais devem usar `gradlew smokeTest` ou main controlada.

---

### 2026-07-02 - Controlar garantia e Status Estoque por serie na TGFSER

**Contexto:**  
O escopo aponta controle por serie/equipamento, e nao apenas por item historico da nota.

**Decisao:**  
Tratar `AD_GARANTIA` e `AD_STATUSEST` em `TGFSER`, mantendo aliases na view para compatibilidade com Java/dashboard/API.

**Motivo:**  
A serie representa o equipamento real da base instalada.

**Impacto:**  
A view deve ler `SER.AD_GARANTIA` e `SER.AD_STATUSEST`. Campos criados anteriormente em `TGFITE`, se existirem, ficam como legado a decidir.

---

### 2026-07-03 - Usar AD_VW_GENESIS_ESTOQUE como fonte de pendentes

**Contexto:**  
A view foi validada/subida e retorna os campos necessarios para montar o payload.

**Decisao:**  
Buscar pendentes em `AD_VW_GENESIS_ESTOQUE` com `SERIE IS NOT NULL`, cruzando com `AD_GENMOVEST` para nao reenviar o que ja estiver `ENVIADO`.

**Motivo:**  
A view concentra a regra de dados e a tabela de controle garante idempotencia operacional.

**Impacto:**  
A acao pode processar todos os registros elegiveis da view. O volume deve ser monitorado na primeira execucao.

---

### 2026-07-03 - Implementar acao agendada Sankhya com org.cuckoo.core.ScheduledAction

**Contexto:**  
As bibliotecas locais Sankhya expuseram a interface real de agendamento em `org.cuckoo.core`.

**Decisao:**  
`IntegracaoGenesisScheduleAction` implementa `org.cuckoo.core.ScheduledAction` e o metodo `onTime(ScheduledActionContext context)`.

**Motivo:**  
Permitir cadastro/execucao da classe como acao agendada no Sankhya.

**Impacto:**  
`onTime(context)` e o entrypoint do Sankhya. `runOnce()` permanece para testes locais.
---

### 2026-07-06 - Usar HTML5/JSP/JavaScript como dashboard analitico

**Contexto:**  
Foi solicitada avaliacao da possibilidade de tratar a integracao usando HTML5/JSP/JavaScript, conforme padrao usado em outros desenvolvimentos.

**Decisao:**  
HTML5/JSP/JavaScript pode ser usado como dashboard analitico para consolidar `AD_VW_GENESIS_ESTOQUE` e `AD_GENMOVEST`, mostrando entradas, saidas, series/estoque, status de estoque e resultado da integracao. A chamada externa para Genesis continua no backend Java/ScheduledAction.

**Motivo:**  
O dashboard deve apoiar analise e homologacao, mas o token Genesis nao deve ser exposto no navegador e o controle de idempotencia/status precisa permanecer centralizado na `AD_GENMOVEST`.

**Impacto:**  
Foi criado um prototipo em `src/main/webapp/genesis`. Ainda sera necessario implementar endpoints internos Sankhya para a tela consumir dados analiticos da view e da tabela de controle.
### 2026-07-06 - Leitura inicial de parametros Genesis

**Contexto:**  
A acao agendada ja estava sendo chamada pelo Sankhya, mas parava porque `lerParametros()` ainda nao estava implementado.

**Decisao:**  
Implementar a leitura de `AD_CENTPARAM`/`AD_CENTPARAMTOP` com `NUPAR = 1` para obter TOPs elegiveis. URL base e token permanecem fixos temporariamente no codigo.

**Motivo:**  
Permitir a validacao operacional imediata da acao agendada no ambiente MA Hospitalar enquanto os campos seguros de URL/token ainda nao existem na central de parametros.

**Impacto:**  
A integracao passa a continuar ate a consulta de pendentes e envio Genesis. Antes de fechamento definitivo, URL/token devem ser migrados para parametro seguro e removidos do codigo.

### 2026-07-07 - Dashboard analitico via snk:query

**Contexto:**  
O dashboard visual carregava corretamente, mas retornava HTTP 404 porque tentava consumir endpoints internos que nao estavam publicados no Sankhya.

**Decisao:**  
Remover a dependencia de endpoints REST internos para o dashboard e usar `snk:query` no proprio JSP, consultando `AD_VW_GENESIS_ESTOQUE` e `AD_GENMOVEST`.

**Motivo:**  
O dashboard e analitico e deve apenas consolidar dados ja existentes no banco Sankhya. A consulta direta via JSP reduz dependencia de backend adicional e evita erro 404.

**Impacto:**  
A tela passa a carregar dados diretamente da base Sankhya. Filtros, cards e agrupamentos sao feitos no JavaScript com base nos dados carregados pela `snk:query`.
