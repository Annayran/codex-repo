# Contexto mínimo e fronteiras de conhecimento

## Obrigatória

Use o princípio **Minimum Sufficient Context**: carregue somente o contexto
necessário para decidir, alterar e validar a tarefa atual.

Fluxo padrão:

1. instruções globais enxutas;
2. `docs/zaya/project-profile.md` válido;
3. classificação da tarefa;
4. no máximo duas referências iniciais da biblioteca;
5. busca textual direcionada;
6. leitura de três a cinco arquivos-fonte iniciais;
7. alteração mínima;
8. validação proporcional ao risco;
9. revisão exclusiva do diff.

Os limites iniciais não são absolutos. Expanda o contexto quando existir evidência
concreta de necessidade e registre brevemente o motivo.

Não use listagem recursiva seguida de leitura massiva como estratégia padrão. Não
carregue toda a biblioteca ou todo o dicionário “por segurança”. Use índices, CSVs
e scripts de consulta.

## Conhecimento corporativo e de projeto

`zaya-engineering` responde **como desenvolvemos**. O repositório do projeto
responde **como este cliente e esta solução funcionam**.

Pode entrar na biblioteca central:

- padrão sanitizado de Java, Gradle, Jape, SQL, banco, transação ou documento;
- template e checklist reutilizável;
- erro técnico genérico comprovado;
- exigência de documentação oficial.

Deve permanecer no projeto:

- regra funcional, TOP, identificador ou contrato específico de cliente;
- código, nome interno, URL privada ou dado de negócio identificável;
- credencial, segredo, conexão ou metadado sensível;
- decisão e problema conhecidos daquela solução.

Todo conhecimento promovido precisa de evidência, compatibilidade, classificação
normativa, CHANGELOG e revisão.

## Agentes

Tarefas simples permanecem com um único agente.

- Principal/worker: executa o fluxo normal e mantém o contexto.
- Explorer: somente quando a investigação complexa puder ser isolada com escopo
  e resultado objetivos.
- Reviewer: somente para mudança relevante; revisa o diff atual e consulta
  contexto adicional apenas quando necessário.

Não crie cinco ou mais subagentes por padrão. Paralelismo deve reduzir tempo sem
duplicar exploração ou espalhar conhecimento confidencial.
