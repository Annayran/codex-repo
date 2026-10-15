# Arquitetura de contexto do Codex

## Objetivo

Reduzir exploração e tokens usando contexto mínimo suficiente, sem reconstruir a
biblioteca nem misturar conhecimento corporativo com fatos de cliente.

## Fluxo

    AGENTS global (thin kernel)
      -> zaya-sankhya-development (router)
      -> project profile + task classifier
      -> contexto local | referência específica | consulta ao dicionário
      -> busca no código
      -> patch mínimo
      -> validação
      -> revisão do diff quando necessária

## Decisões preservadas

- A biblioteca canônica permanece em C:\dev\zaya\engineering\zaya-engineering.
- A skill instalada permanece em
  C:\Users\annayran.ferreira\.agents\skills\zaya-sankhya-development.
- O dicionário 4.35b815 permanece sanitizado e versionado por arquivos.
- Java, Gradle, SDK Sankhya, FQCNs e contratos são preservados por projeto.
- Nenhum projeto de cliente é alterado automaticamente.
- O remote canônico é privado e nenhum push ocorre sem autorização explícita.

## Baseline anterior à evolução

- AGENTS global: 426 linhas e SHA-256
  E4842DF9AC74F49458B624846840D50F1E957979AA0DDA4B7FF3082CCD0F3EF8.
- Repositório local sem commit, sem remote e com 1.726 arquivos não rastreados.
- Skill canônica e instalada sincronizadas por conteúdo.
- Descoberta recursiva produzia falso positivo Gradle por ler templates/.
- Não existiam .gitignore, testes, CLI, freshness ou lookups do dicionário.
