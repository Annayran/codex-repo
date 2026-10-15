# Padrões do Projeto

## Organização de Código

A documentação técnica define a seguinte separação de responsabilidades para a customização Java:

- `IntegracaoGenesisScheduleAction.java`: orquestração da ação agendada.
- `GenesisHttpClient.java`: comunicação HTTP REST com a Genesis.
- `EstoqueGenesisDTO.java`: representação do payload `EstoqueJson`.
- `MovimentoEstoqueRepository.java`: acesso à VIEW e tabela de controle.
- `PayloadBuilder.java`: validação e montagem do payload.

A confirmar no código-fonte quando ele estiver disponível.

## Nomenclatura

- VIEW: `AD_VW_GENESIS_ESTOQUE`.
- Tabela de controle: `AD_GENMOVEST`.
- Campo de status: `AD_STATUSEST`.
- Campo de garantia sugerido: `AD_GARANTIA` ou equivalente, com nome físico a confirmar.
- Classe principal: `IntegracaoGenesisScheduleAction`.

## Separação de Responsabilidades

- A ação agendada não deve conter detalhes de HTTP, SQL complexo ou montagem manual extensa de JSON.
- O cliente HTTP deve cuidar apenas de requisição, headers, timeout, leitura de resposta e classificação básica.
- O repository deve concentrar consultas, controle de fila, status, tentativas e reprocessamento.
- O builder deve validar obrigatórios e montar o DTO.
- A VIEW deve concentrar derivações SQL necessárias para dashboard e integração.

## Acesso a Dados

- Usar uma VIEW única para consolidar dados de empresa, nota, parceiro, produto, série e estoque.
- Usar tabela de controle para idempotência, fila, tentativas, retorno HTTP, erro e reprocessamento.
- TOPs não parametrizadas devem ser ignoradas: não entram na fila nem no dashboard.
- Chave de movimento deve combinar empresa, nota, item, produto, série e sequência/movimento.

## Logs

- Nunca logar token, URL sensível, credenciais ou payload completo com dados sensíveis.
- Mascarar token em qualquer mensagem técnica.
- Registrar retorno por movimento, não apenas erro geral do lote.
- Alertar responsáveis em falha recorrente, token inválido ou rejeição de schema.

## Tratamento de Erros

- Não interromper o lote inteiro por erro em um único registro.
- Erros de dados devem virar erro funcional do movimento e aguardar correção/reprocessamento.
- Timeout/indisponibilidade devem virar erro transitório com incremento de tentativa.
- HTTP 401 e rejeição de schema exigem alerta e diagnóstico.
- Reenvio deve respeitar idempotência por `codigoExterno` e/ou hash do payload.

## Queries

- Evitar duplicar SQL do dashboard e da integração; a mesma VIEW deve servir os dois usos.
- CEP e telefone devem ser formatados por função ou expressão SQL.
- `DTINSTALACAO` deriva de `DTNEG`.
- `PODER` deriva de `TGFEST.CODPARC`: `0 = PROPRIO`, diferente de `0 = TERCEIRO`.
- `TIPO` deriva de `TGFESE.ATUALESTOQUE` e `TGFESE.ATUALESTTERC`.

## Boas Práticas Obrigatórias

- Ler `/.ai/context` antes de propor alterações futuras.
- Não assumir arquitetura, stack ou regra de negócio sem evidência no código ou documentação legível.
- Guardar segredos em cofre/parâmetro seguro, nunca em código ou documentação versionada.
- Registrar decisões relevantes em `decisions.md`.
- Atualizar `checkpoint.md` e `current-sprint.md` ao fim de sessões relevantes.
- Preservar código-fonte e arquivos existentes; mudanças devem ser pequenas, rastreáveis e justificadas.

## Anti-padrões a Evitar

- Hardcode de URL, token, credenciais, TOPs ou ambiente.
- Reprocessar movimento enviado sem idempotência.
- Enviar TOP não parametrizada.
- Depender somente de histórico de item para status vigente da série sem validar a modelagem final.
- Interromper todo o lote por erro isolado.
- Expor token em logs ou telas.

