# Retirada temporária do bloqueio — 29/10/2026

Autorização explícita do cliente: “Retira isso, por hora não precisa”, após
explicação do bloqueio AUTORIZACAO_PENDENTE e do passo2 não implementado.

ConfiguracaoServicosBelmicro deixa de negar todas as operações. O provider do
projeto permite CONSULTAR e SIMULAR (inclui recálculo) com usuário autenticado
positivo, origem/id válidos e operação não nula. Não aplica restrição adicional
por perfil ou empresa nesta fase. Isso é uma liberação temporária autorizada,
não a implementação da integração granular de permissões do Sankhya.

AcaoFinanceiroRateio e ComandosSankhyaRateio continuam obtendo a sessão do
servidor e verificando a identidade autenticada. Payload não aceita usuário,
base arbitrária ou valores finais. Origem canônica, marcador de auxiliar,
validações de cadastros, soma/precisão, revisão e reenvio permanecem. Sessão,
origem ou operação inválidas são rejeitadas com SEM_PERMISSAO.

Efetivação permanece bloqueada pelo serviço padrão não implementado; este
pedido não implementa nem libera geração financeira. Providers específicos
externos/legados preservam a prioridade de carregamento e sua própria política.
Não houve alteração de cadastros de permissões no Sankhya ou DML no cliente.

Entregável: build/libs/ZAYA-BLM-V7.5.jar. Atualizar somente o módulo Java,
mantendo IDs, FQCNs e dashboard existente. Sem instalação remota nesta etapa.
Para rollback, reinstalar o pacote anterior com provider fechado.

Validação:125 cenários Java no build SDK, incluindo permissão temporária para
sessão válida e rejeição de usuário/origem/operação inválidos, sem banco real.
Revisão independente estática do provider/teste sem achados. Política granular
fica como dívida explícita para posterior implementação.
