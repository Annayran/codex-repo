# Serviço externo de suporte

Tecnologia proposta: Java e Quarkus; versões e persistência a selecionar antes do build. Nenhuma dependência foi instalada.

Contrato HTTP proposto:
- POST /v1/occurrences: autenticar ambiente, validar escopo/schema e chave de idempotência, persistir pacote, retornar HTTP 201 com occurrenceId e status RECEIVED.
- GET /v1/occurrences/{id}: validar acesso ao cliente/ambiente e retornar pacote autorizado.
- Reenvio idêntico com mesma chave retorna protocolo existente; mesma chave com conteúdo diferente deve ser rejeitada como conflito.

A recepção persistente não depende de IA. Idempotência deve usar unicidade atômica por cliente, ambiente e chave. Credenciais determinam o tenant; rejeitar divergência com identificadores declarados no pacote.

Definir limites de payload, retenção, mascaramento, permissões da equipe e timeouts antes da implantação. Persistência e protocolo somente serão considerados disponíveis após implementação e testes.
