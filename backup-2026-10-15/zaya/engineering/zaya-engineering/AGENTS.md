# Regras para manutenção dos padrões

## Escopo

Este repositório contém somente padrões, templates, checklists e exemplos genéricos da Zaya.IT.

## Regras obrigatórias

- Toda mudança deve possuir Issue ou OS e justificativa documentada.
- A justificativa deve explicar problema, impacto, alternativas e compatibilidade.
- É proibido inserir nomes, regras funcionais, código, URLs internas, credenciais ou qualquer informação confidencial específica de clientes.
- Nunca reproduza senha, token, chave, certificado ou segredo em exemplos.
- Atualize `CHANGELOG.md` para toda alteração aprovada de regra, template ou checklist.
- Toda mudança exige revisão por outra pessoa antes do merge.
- Identifique cada orientação como **Obrigatória**, **Recomendada** ou **Pendente de validação pela equipe**.
- Não apresente hipótese sobre Sankhya como regra comprovada.
- Preserve compatibilidade com padrões já adotados ou documente claramente a quebra.
- Não faça commit, merge, tag ou publicação sem autorização no contexto da tarefa.

## Edição

Prefira mudanças pequenas e rastreáveis. Atualize documentos relacionados na mesma proposta. Exemplos devem ser mínimos, fictícios e livres de dados de clientes.

Use o fluxo `search → targeted read → patch → validation`. Comece com até duas
referências da biblioteca e não percorra todo o dicionário; use os scripts em
`scripts/`.

## Aprovação

Mudanças obrigatórias precisam de aprovação da engenharia responsável e, quando afetarem Sankhya, de revisão de alguém com domínio do processo funcional envolvido.

## Biblioteca de conhecimento e novos projetos

- **Obrigatória:** mantenha regras corporativas reutilizáveis neste repositório e
  fatos específicos no repositório do respectivo projeto.
- **Obrigatória:** ao abrir um novo projeto Zaya, use a skill
  `zaya-sankhya-development`, valide a freshness de
  `docs/zaya/project-profile.md` e atualize apenas quando necessário.
- **Obrigatória:** não copie código, nomes internos, URLs, credenciais, regras
  funcionais ou metadados sensíveis de clientes para a biblioteca central.
- **Obrigatória:** novos projetos Java/Sankhya usam Gradle, são importados no
  IntelliJ como projetos Gradle e adotam MVC conforme `standards/mvc.md`.
- **Obrigatória:** o JAR segue `ZAYA-<SIGLA>-V<MAJOR>.<MINOR>.jar` e a versão é
  controlada pelo Gradle conforme `standards/gradle.md`.
- **Recomendada:** registre padrões recorrentes de forma anonimizada em
  `knowledge/project-patterns.md`, sempre distinguindo fatos de inferências.
- **Obrigatória:** preserve FQCNs e estruturas existentes; a adoção do padrão em
  projetos legados exige migração compatível, homologação e rollback.
- **Obrigatória:** revisão automatizada ou humana deve começar pelo Git diff e
  não procurar melhorias fora das linhas afetadas.

## Classificação normativa

- **Obrigatória:** deve ser atendida ou possuir exceção formal registrada.
- **Recomendada:** prática preferencial, com adaptação justificável.
- **Pendente de validação pela equipe:** proposta sem força normativa até revisão formal.
