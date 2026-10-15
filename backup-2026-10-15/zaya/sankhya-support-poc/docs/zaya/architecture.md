# Arquitetura proposta

Cliente no Portal -> botão/adaptador -> coleta delimitada -> receptor externo -> persistência -> protocolo -> consulta pela equipe.

A primeira entrega funcional será recepção de evidências, sem alteração operacional. IA, MCP, eventos automáticos e workers de análise são evoluções condicionadas à utilidade da coleta.

O coletor identifica o documento e reúne evidências permitidas. O receptor valida cliente e ambiente usando credenciais, registra pacote de forma idempotente e controla acesso da equipe. Credenciais não seguem no corpo da ocorrência.

Falha antes de persistir não produz protocolo de sucesso. Resposta perdida pode ser recuperada por reenvio da mesma chave. Estado atual não reconstitui falha passada. Classificação entre sistema, configuração, personalização e infraestrutura será hipótese até confirmação por evidências.

Não há mudança de banco Sankhya nesta etapa. Qualquer mudança futura terá deploy, validação e rollback.
