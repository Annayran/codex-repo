# SQL

## Obrigatória

- Usar bind parameters sempre que possível.
- Evitar concatenação de entrada externa.
- Avaliar cardinalidade, duplicidade e efeito de filtros sobre `LEFT JOIN`.
- Preservar aliases consumidos por dashboards, relatórios e integrações.
- Evitar consultas repetidas dentro de loops.
- Documentar impacto, volume esperado e plano de rollback.
- Não executar DML em tabelas padrão Sankhya sem autorização explícita.

## Recomendada

Separar consultas de diagnóstico de scripts de deploy. Incluir validações pré e pós-execução.

Convenções de nomenclatura e tuning por ambiente: **Pendente de validação pela equipe**.