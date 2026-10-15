# Roteamento seletivo da biblioteca Zaya

Resolva a raiz nesta ordem:

1. variável `ZAYA_ENGINEERING_HOME`;
2. `C:\dev\zaya\engineering\zaya-engineering`.

Se a biblioteca não existir, use apenas o contexto do projeto e informe a
limitação. Não procure cópias em pastas do Codex ou sandbox.

## Classificação e referências iniciais

Leia somente a linha correspondente à tarefa. Comece com no máximo dois
documentos; expanda quando a mudança realmente cruzar domínios.

| Classe | Referências iniciais |
|---|---|
| `JAVA` | `standards/java.md`; `standards/project-structure.md` |
| `SQL` | `standards/sql.md`; consulta pontual ao dicionário |
| `DATABASE` | `standards/database-objects.md`; `checklists/database.md` |
| `JAPE` | `sankhya/transactions.md`; `sankhya/development-checklist.md` |
| `DOCUMENT` | `sankhya/document-generation.md`; `checklists/sankhya-document.md` |
| `DASHBOARD` | `sankhya/dashboards.md`; `checklists/dashboard.md` |
| `REPORT` | documento do mecanismo comprovado; `standards/sql.md` quando aplicável |
| `HTML5` | `standards/html5-javascript.md`; `standards/context-boundaries.md` |
| `JAVASCRIPT` | `standards/html5-javascript.md`; `standards/context-boundaries.md` |
| `ADDON` | `sankhya/addon-studio-metadata.md`; `sankhya/official-development.md` |
| `BUILD` | `standards/gradle.md`; `standards/project-structure.md` |
| `DEPLOY` | `checklists/production-release.md`; `templates/merge-request/Default.md` |
| `DEBUG` | `sankhya/common-errors.md`; documento da classe técnica identificada |
| `REVIEW` | `checklists/code-review.md`; somente o diff atual |

Para tarefa Sankhya que dependa de API ou comportamento de versão, use
`sankhya/official-development.md` somente quando a compatibilidade for material.

## Dicionário

Não abra milhares de páginas. Para versão compatível com `4.35b815`, use:

- `scripts/Get-ZayaTable.ps1` para tabela;
- `scripts/Get-ZayaField.ps1` para campo;
- `scripts/Get-ZayaRelation.ps1` para relação;
- `scripts/Search-ZayaDictionary.ps1` para busca mista ou em lote.

Abra uma página em `tables/` somente depois que a consulta apontar a tabela.
Relações são DWF e não comprovam foreign keys físicas.

## Criação de projeto

- Java legado ou módulo independente: `templates/repository/java-sankhya`.
- Add-on Studio: template oficial do componente; preserve módulos `model` e `vc`.
- Documentação local: `templates/documentation/zaya`.

## Promoção de conhecimento

Promova somente conteúdo sanitizado, comprovado por fonte oficial ou repetido em
mais de um projeto, com compatibilidade, classificação normativa, CHANGELOG e
revisão. Regras e fatos de cliente permanecem no projeto.
