# Arquitetura do Projeto

## Visão Geral

A customização integra o Sankhya à Genesis Sistemas para envio automático de movimentos de estoque controlados por série, vinculados à Base Instalada da MA Hospitalar.

A documentação técnica `ZY-MAH-2026-GENESIS-INTPDD-001.pdf` define a arquitetura-alvo como uma integração no contexto Sankhya MGE composta por VIEW SQL de consolidação, dashboard, tabela de controle, ação agendada Java e cliente HTTP REST para a API Genesis.

Observação: ainda não há código-fonte no workspace analisado. A arquitetura abaixo vem da documentação técnica, não de arquivos implementados no repositório.

## Camadas

- Dados Sankhya: tabelas nativas como TGFCAB, TGFITE, TGFESE, TGFSER, TGFEST, TSIEMP, TGFPAR, TGFPRO, TGFGRU, TSIEND, TSIBAI, TSICID/TSIUFS e TGFLOC.
- Consolidação SQL: VIEW `AD_VW_GENESIS_ESTOQUE`, reunindo empresa, nota, parceiro, produto, série, estoque e campos derivados.
- Controle de integração: tabela `AD_GENMOVEST`, usada para fila, status, tentativas, retorno HTTP, erros e idempotência.
- Aplicação Java Sankhya: `IntegracaoGenesisScheduleAction`, implementando `com.sankhya.schedule.ScheduleAction`.
- Cliente externo: `GenesisHttpClient`, responsável por POST REST para a API Genesis.
- Visualização: dashboard Sankhya "Gestão Estoque por Série" ou "Gestão Base Instalada".

## Módulos Principais

- `IntegracaoGenesisScheduleAction.java`: ponto de entrada da ação agendada; lê parâmetros, busca pendentes, monta payload, envia e registra retorno.
- `GenesisHttpClient.java`: cliente HTTP REST com token, timeouts e classificação de respostas.
- `EstoqueGenesisDTO.java`: DTO espelhando o `EstoqueJson` da Genesis.
- `MovimentoEstoqueRepository.java`: acesso à VIEW e tabela de controle; marca status e tentativas.
- `PayloadBuilder.java`: converte registro consolidado em payload Genesis e valida campos obrigatórios.
- `AD_VW_GENESIS_ESTOQUE`: VIEW base de estoque por série/base instalada.
- `AD_GENMOVEST`: tabela de controle para rastreabilidade e idempotência.

## Fluxo Principal

1. Usuário/processo movimenta estoque no Sankhya usando TOP elegível.
2. A VIEW consolida os dados do movimento seriado.
3. A tabela de controle registra pendência, status e chave de idempotência.
4. A ação agendada roda em intervalo parametrizado.
5. A rotina busca movimentos pendentes/reprocessar.
6. O payload é validado e montado no formato `EstoqueJson`.
7. O cliente HTTP envia `POST /api/v1/estoques/` para a Genesis.
8. O retorno é gravado por movimento: enviado, erro funcional, erro transitório, reprocessar ou ignorado.
9. O dashboard consulta a mesma base consolidada para gestão operacional.

## Integrações

- Genesis API V1, recurso Estoques.
- Método documentado: `POST /api/v1/estoques/`.
- Base Swagger: host + `/G4VAPI`, parametrizada por ambiente.
- Autenticação: header `token`/apiKey, com alternativa basicAuth a confirmar.
- Sucesso esperado: HTTP 201 com `EstoqueJson` criado.
- Erros esperados: HTTP 400, 401 e 500.

## Dependências Arquiteturais Importantes

- Sankhya MGE e mecanismo de `ScheduleAction` Java.
- Banco Sankhya com tabelas nativas citadas na documentação.
- Central de Parâmetros para TOPs elegíveis e parâmetros de execução.
- Cofre/parâmetro seguro para URL, token, timeouts, tentativas e ambiente.
- Contrato Swagger Genesis validado em homologação.

## Diagrama Textual

```txt
Movimento Sankhya com TOP elegível
        |
        v
Tabelas Sankhya -> AD_VW_GENESIS_ESTOQUE -> AD_GENMOVEST
        |                         |                 |
        |                         |                 v
        |                         |     IntegracaoGenesisScheduleAction
        |                         |                 |
        |                         |                 v
        |                         |         GenesisHttpClient
        |                         |                 |
        v                         v                 v
Dashboard Base Instalada      Payload DTO      API Genesis Estoques
```

## Pontos de Atenção

- O código-fonte real ainda precisa ser localizado/adicionado ao workspace.
- A modelagem final de `Status Estoque` por série ainda precisa ser decidida entre TGFITE, TGFSER ou tabela adicional.
- Não gravar token, URL sensível ou credenciais em código, logs, dashboard ou documentação pública.
- Confirmar em homologação se `codigoExterno` será chave de upsert/idempotência na Genesis.

