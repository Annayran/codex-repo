# Changelog

## [Não publicado] - 2026-09-14

### Documentação

- Criado o manual do usuário da Fila de Reserva de Estoque - Fase 2 em PDF.
- Documentados o uso das abas Reserva de Estoque e Reserva de Estoque Provisionado, os parâmetros operacionais e a notificação comercial.
- Incorporadas as evidências dos testes funcionais dos produtos 1070, 986, 774 e 90, com linguagem orientada ao usuário.
- Incluídos guia de consulta, interpretação dos campos, tratamento de divergências e resumo de homologação.
- Removidos do manual os blocos "Situação do projeto" e "Encerramento", conforme revisão solicitada.

## [Não publicado] - 2026-09-08

### Preparado

- Pacote V1 de produção da parametrização da fila de reserva, separado em Lote A (trigger) e Lote B condicional (notificação).
- Pré-validação, captura do DDL vigente, pós-validação, smoke test e orientações de rollback.
- Escopo reduzido aos dois objetos relacionados à solicitação atual, preservando procedures e views existentes.

### Pendente para produção

- Comparação com o DDL vigente da trigger em produção e geração do rollback oficial da janela.
- GO técnico e funcional para o Lote A.
- Confirmação de `DTNOTINT`, `GRPCOMINT`, grupos destinatários e autorização para uso de `TSIAVI/TGFNUM` antes do Lote B.

## [0.1.0-SNAPSHOT] - 2026-08-18

### Adicionado

- Projeto Gradle com bytecode Java 8 e dependências Sankhya `compileOnly`.
- Ação agendada para atualização automática de provisões.
- Serviço de aplicação e porta de persistência reutilizáveis em testes.
- Repositório Oracle que delega à procedure já utilizada pelo botão manual.
- Scripts candidatos de limpeza do botão, validação do calendário e rollback.
- Pacote Oracle central para elegibilidade, dias úteis, prioridade e estoque futuro.
- Trigger, rotinas de fila, provisão, faturamento e botão preparadas sem transação autônoma.
- Contagem de registros processados devolvida ao botão e à ação agendada Java.
- Bloqueio por produto integrado à ação manual de prioridade.
- Auditoria pós-saneamento da Fase 1 em modo somente leitura.
- Baselines integrais de `AD_VGFPROVEST` e `AD_VGFFRPROD` recebidas do TESTE.
- Estoque futuro incorporado às views ativa e provisionada.
- Compatibilidade preservada para registros sem `DTPREV` e fragmentos mistos N/S.
- Consultas consolidadas para OE, registros sem previsão e evidências recebidas.

### Pendente

- Homologação funcional dos feriados municipais/estaduais/recorrentes na `TSIFER`.
- Contagem integral dos registros ativos sem `DTPREV`; a exportação detalhada atingiu 5.000 linhas.
- Comprovação do vínculo entre as 23 TOPs de OE e o pedido de origem no faturamento.
- Definição do horário e da periodicidade da ação agendada.
