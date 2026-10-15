# Relatório de cópia e reorganização — 2026-07-16

## Escopo executado

Foi realizada cópia conservadora para a nova estrutura em `C:\dev`, mantendo todas as origens intactas. Nenhum arquivo ou diretório de origem foi removido, movido ou renomeado.

## Resultados

- Primeira etapa: 72 mapeamentos, 32.252 arquivos e 7.064.382.445 bytes.
- Segunda etapa: 14 mapeamentos, 6.373 arquivos e 3.436.116.550 bytes.
- Divergências de contagem ou bytes: zero.
- Arquivos `build.gradle`/`build.gradle.kts` preservados na nova estrutura: 160.
- Gradle wrappers preservados: 65.
- Espaço livre após a cópia: aproximadamente 11 GB.

## Organização aplicada

- `C:\dev\tools`: Flutter SDK e ferramentas locais.
- `C:\dev\zaya\clients`: projetos por cliente, incluindo AttTelecontrol em `clients\skala\att-telecontrol`.
- `C:\dev\zaya\platform`: APIs Sankhya, módulos, HTML5 compartilhado e plataforma Skala pendente de validação de ownership.
- `C:\dev\zaya\engineering`: padrões e auditorias.
- `C:\dev\zaya\sandbox\codex-recovered`: projetos com marcadores de build recuperados das tarefas do Codex.
- `C:\dev\zaya\sandbox\codex-session-archives`: cópias integrais das 13 pastas datadas do Codex.
- `C:\dev\zaya\archive\duplicates`: backups e candidatos a duplicação.
- `C:\dev\zaya\archive\zip-backups`: ZIPs copiados sem descompactação.

## Validação Git

Foram comparados origem e destino de PersonalizacoesCompraLike, três repositórios Lidon, Apontamento Produção, Flutter e polyvox-gitlab. Branch, remote origin e quantidade de entradas no `git status` foram preservados.

## Decisões pendentes

- Confirmar se `skala-om-platform` é plataforma compartilhada ou solução específica do cliente Skala.
- Confirmar cliente de FlowContratosServicos, Teleword e TennisPlace.
- Comparar duplicados antes de declarar versões canônicas.
- Resolver alterações locais dos repositórios Lidon e Apontamento Produção.
- Validar caminhos absolutos, builds Java 8, dependências Sankhya e publicação antes de usar somente os novos caminhos.
- Atualizar PATH/IDE para o Flutter apenas após validação do SDK copiado.

## Rollback

As origens permanecem disponíveis em `C:\dev\zy-tools` e `C:\Users\annayran.ferreira\Documents\Codex`. O rollback consiste em continuar usando os caminhos antigos e desconsiderar os destinos novos. Nenhuma exclusão foi autorizada ou executada.