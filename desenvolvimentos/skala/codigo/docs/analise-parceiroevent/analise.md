# Análise do evento ParceiroEvent

Fonte: classe br.zaya.events.ParceiroEvent e dependência br.zaya.rules.ParceiroRegras, recuperadas seletivamente de 70_Skala.jar no ZIP fornecido. Inspeção estática com javap; não houve execução no Sankhya nem confirmação do artefato efetivamente implantado.

## Causa comprovada no artefato

- afterUpdate chama validaCamposObrigatoriosParceiro em toda atualização, sem filtro por campo modificado.
- afterInsert também chama a mesma validação; beforeInsert a chama quando TIPPESSOA=J.
- RN0006: quando CLIENTE=true e FORNECEDOR=false, CODEMPPREF e CODTAB devem ser não nulos. Basta um deles nulo para bloquear. Não há teste de usuário, grupo de autorização ou ATIVO nessa condição. O código não testa zero nesses dois campos.
- Para o mesmo perfil de parceiro, se ATIVO=true, também exige CODVEND diferente de zero (nulo é convertido para zero).
- beforeUpdate possui outra regra: alterações em LIMCRED, LIMCREDMENSAL ou GRUPOAUTOR chamam validaAlteracaoCredito. A RN0007 exige AD_PERMALTLIM=S no usuário atual. Essa autorização não dispensa a RN0006.

## Relação com o chamado

O bloqueio relatado ao alterar grupo de autorização é compatível com a validação geral de afterUpdate, caso o parceiro esteja marcado como cliente e não fornecedor e CODTAB esteja nulo. Preencher somente a tabela resolve essa etapa apenas se CODEMPPREF também estiver preenchido; outras validações continuam aplicáveis.

O artefato não comprova por que o fluxo funcionava antes, nem como os parceiros citados foram incluídos com campos vazios. Isso exige histórico de implantação, configuração do evento e conferência dos registros no ambiente. Não houve acesso ao banco.

## Proposta para alinhamento funcional

Confirmar formalmente o marco do fluxo que torna Empresa preferencial e Tabela de Preço obrigatórias. Caso o pré-cadastro possa permanecer incompleto, definir como ele é identificado e quando a validação passa a ser obrigatória. O evento atual também valida na inclusão; ajustar apenas afterUpdate pode não atender ao fluxo completo.

Preservar RN0007, validação do vendedor e demais comportamentos. Não remover a chamada geral sem separar suas responsabilidades. Após definição, validar inclusão e atualização de cliente, cliente/fornecedor, ativo/inativo, alterações de grupo e negociação, campos obrigatórios nulos e preenchidos, e usuários com e sem permissão.

Nenhuma regra funcional foi alterada; nenhum deploy, DML, commit ou ação remota foi realizado.
