# Banco de dados

Nenhuma mudança de banco foi implementada neste incremento.

As estruturas persistentes de execução e auditoria serão criadas somente após a
definição do modelo do Dynaform. Essa futura mudança deverá conter, no mínimo:

- `deploy/` com instalação idempotente quando tecnicamente possível;
- `validation/` com verificações pré e pós-implantação;
- `rollback/` com retorno compatível;
- ordem de execução, permissões e impacto documentados.

Não é autorizado DML direto nas tabelas produtivas padrão do Sankhya.
