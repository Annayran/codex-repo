# Arquitetura do Projeto

## Visão Geral

O workspace `Polyvox` contém mais de um artefato, mas o escopo identificado para este contexto inicial é:

- `PolyvoxTelecontrol`: projeto Java/Gradle de integração entre Sankhya e Telecontrol, empacotado como `TelecontrolPolyvox.jar`.
- `dashboard_os_tlc`: dashboard JSP para acompanhamento e acionamento operacional de pagamentos/geração documental de OS TLC.
- `Dashboards/dashboard_os_tlc`: cópia/variante do dashboard `dashboard_os_tlc`.

O projeto Java roda dentro do ecossistema Sankhya, usando ações de botão (`AcaoRotinaJava`), rotinas agendadas (`ScheduledAction`) e APIs Jape/NativeSql para persistência e operações ERP. A integração externa principal é a API Telecontrol, acessada via HTTP com cabeçalhos `Access-Application-Key` e `Access-Env`.

## Camadas

- **Actions / Entrada Sankhya**: classes `*Bt`, `*Sc`, schedulers e actions em pacotes por feature. São pontos de entrada para botões, rotinas agendadas e ações registradas no Sankhya.
- **Application / Casos de uso**: fachadas e orquestradores por feature, como `osintegracao/application`, `pedidos/application`, `produto/application`, `ordemservico/application`.
- **Infraestrutura / Integração**: clientes HTTP, adapters, DAOs legados e acesso ao Sankhya/Telecontrol.
- **Models / DTOs**: objetos de transporte para respostas e payloads Telecontrol, pedidos, peças, OS, faturamento, família, linha etc.
- **Parsers / Mappers**: conversão entre entidades Sankhya, JSON e modelos Java.
- **Dashboard JSP**: camada de consulta, visualização e disparo de ações para OS finalizadas e pagamentos.

## Módulos Principais

- `PolyvoxTelecontrol/src/main/java/br/zaya/telecontrol/actions`: bridges legadas de compatibilidade com metadata Sankhya, marcadas no documento de organização como pontos a remover somente após migração validada.
- `.../osintegracao/actions`: `SincronizarOsAction`, `OsIntegracaoScheduler`, `GerarDocumentoOsAction`, `GerarDocumentoOsScheduler`.
- `.../osintegracao/application`: `OsIntegracaoUseCase`, `LegacyOsIntegracaoUseCase`, `OsIntegracaoDocumentoService`, `SincronizacaoResultado`.
- `.../services`: implementação legada, incluindo `Api`, `Autenticacao`, `OsIntegracaoService`, `OsIntegracaoDAO` e serviços de envio/busca.
- `.../shared/config/ConfigTelecontrol.java`: carrega configuração da integração a partir de `AD_TCLCFG` e parâmetro `IDCONFIGTLC`.
- `.../shared/infrastructure/http`: cliente HTTP compartilhado para Telecontrol.
- `.../shared/utils`: utilitários como `LogUtil`, `LockManager`, `DateUtil`, `PedidoNotaHelper`.
- `dashboard_os_tlc/dashboard_os_tlc.jsp`: dashboard Sankhya/JSP para consulta de `AD_OSTLC`, identificação de OS com/sem peça e disparo de rotinas.

## Fluxo Principal

Fluxo de sincronização de OS, conforme arquivos Java analisados:

1. Uma action ou scheduler Sankhya chama um caso de uso de OS.
2. O caso de uso delega para serviços/adapters legados quando necessário.
3. A configuração é carregada de `AD_TCLCFG` via `ConfigTelecontrol`.
4. A API Telecontrol é chamada via HTTP.
5. As respostas são parseadas para models Java.
6. OS e peças são persistidas/atualizadas em tabelas customizadas, especialmente `AD_OSTLC` e `AD_OSPECA`.
7. O pós-processamento documental agrupa OS finalizadas por CNPJ e cria nota de compra ou financeiro direto conforme regras identificadas.

Fluxo do dashboard `dashboard_os_tlc`:

1. JSP executa consultas sobre `AD_OSTLC`, `TGFPAR` e `AD_OSPECA`.
2. Mostra somente OS com `STATUSOS = 'Finalizada'`.
3. Consolida valores por parceiro/CNPJ usando `AD_TAXAMANUTENCAO`/valor fixo de serviço.
4. Diferencia OS com peça e sem peça.
5. Permite disparar rotinas de geração/desfazer por IDs configuráveis.

## Integrações

- **Sankhya ERP**: Jape (`JapeFactory`, `JapeWrapper`, `DynamicVO`), `EntityFacade`, `NativeSql`, actions de botão, schedulers Cuckoo e parâmetros do sistema.
- **Telecontrol API**: chamadas HTTP GET/POST/PUT/DELETE com token e ambiente vindos de `AD_TCLCFG`.
- **Banco do Sankhya**: tabelas customizadas e nativas usadas no código/JSP, como `AD_TCLCFG`, `AD_OSTLC`, `AD_OSPECA`, `AD_TCLPED`, `AD_TCLPCA`, `AD_TCLLGT`, `TGFPAR`, `TGFCAB`, `TGFITE`, `TGFVAR`.
- **Documentos financeiros/notas**: geração de nota de compra e financeiro direto via APIs internas do Sankhya.

## Diagrama Textual

```text
Sankhya Action/Scheduler
  -> actions/* ou feature/actions/*
  -> application/use case
  -> services/adapters/DAO
  -> API Telecontrol + tabelas Sankhya
  -> models/parsers
  -> AD_OSTLC / AD_OSPECA / notas / financeiro

Dashboard JSP
  -> queries Sankhya
  -> resumo e detalhes por OS/parceiro
  -> disparo de rotinas Java cadastradas
```

## Pontos de Atenção

- Há uma estratégia incremental de refatoração documentada em `PolyvoxTelecontrol/docs/telecontrol-organizacao.md`.
- As bridges em `actions/*` não devem ser removidas sem migração da metadata Sankhya.
- O lock atual é em memória, e o documento existente recomenda evoluir para lock transacional em banco.
- Há duplicidade/cópia do dashboard em `dashboard_os_tlc` e `Dashboards/dashboard_os_tlc`; a origem oficial precisa ser confirmada.
- O workspace também contém `ImportadorPedido` e `Koncili`, mas eles não foram aprofundados neste checkpoint por não fazerem parte do escopo citado.
