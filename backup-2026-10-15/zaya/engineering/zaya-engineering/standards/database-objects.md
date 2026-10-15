# Objetos de banco

## Obrigatória

Cada mudança deve possuir:

- script de deploy idempotente quando tecnicamente possível;
- script ou consulta de validação;
- procedimento ou script de rollback;
- ordem de execução;
- dependências e permissões;
- owner técnico e justificativa;
- impacto em objetos consumidores.

Nunca substituir objeto padrão Sankhya sem autorização explícita e análise de impacto.

## Recomendada

Manter um arquivo por objeto ou mudança lógica e registrar versão no CHANGELOG do projeto.

Convenção oficial para objetos customizados: **Pendente de validação pela equipe**.