# Transações Sankhya

## Obrigatória

Antes de gravar dados, confirme se a execução já está em transação gerenciada. Verifique o padrão existente para `JapeSession`, `JdbcWrapper`, `NativeSql`, `EntityFacade` e serviços internos. Não faça commit ou rollback manual sem evidência de que esse é o padrão correto.

Em erro crítico, propague exceção para permitir rollback. Não deixe alterações parciais e feche recursos conforme o padrão do projeto.

Detalhes de comportamento por versão do Sankhya: **Pendente de validação pela equipe**.