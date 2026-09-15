# Changelog

Todas as mudanças relevantes nos padrões serão registradas neste arquivo.

## Não publicado

### Adicionado

- Arquitetura de contexto mínimo com AGENTS global enxuto e skill como roteador.
- Classificador de tarefas e exploration budget inicial.
- Fingerprint e validação de freshness no project profile.
- Freshness estrutural independente do commit que versiona o próprio profile, evitando invalidação circular.
- Consultas PowerShell objetivas para tabelas, campos e relações.
- Ponto de entrada estável `scripts/Get-ZayaProjectProfile.ps1` para o gerador canônico da skill.
- CLI leve `scripts/zaya.ps1`.
- Templates de arquitetura, decisões e problemas conhecidos por projeto.
- Sincronização da skill por hash e testes Pester.
- Telemetria local opcional em diretório ignorado.
- Proteções de Git ampliadas para segredos, dumps, logs, dados locais, IDE e artefatos de build.
- Checklist de secrets antes do commit e revisão limitada ao Git diff.
- Estrutura inicial do repositório de engenharia.
- Padrões iniciais de projeto, Git, Merge Request, Java, SQL, HTML5/JavaScript e objetos de banco.
- Checklists Sankhya, revisão, dashboard, banco e publicação.
- Convenção de pacotes `br.com.zaya.<cliente>.<solucao>` e separação por responsabilidade.
- Padrão Gradle para Java 8, wrapper versionado e dependências Sankhya configuráveis.
- Template reutilizável `templates/repository/java-sankhya`.
- Padrão MVC para novos projetos Java/Sankhya, preservando FQCNs legados.
- Convenção de JAR `ZAYA-<SIGLA>-V<MAJOR>.<MINOR>.jar` controlada pelo Gradle.
- Orientações para IntelliJ, Add-on Studio e metadados declarativos.
- Skill `zaya-sankhya-development` para descoberta e perfil técnico automático.
- Catálogo sanitizado de padrões recorrentes observados nos projetos.
- Mapa navegável e sanitizado do dicionário Sankhya `4.35b815`, com índices de
  tabelas, campos, instâncias, módulos e relações DWF.

### Justificativa

- Padronizar novos projetos a partir do piloto de apontamento de produção validado por build em 2026-07-16.
- Reduzir caminhos absolutos divergentes e preservar FQCNs vinculados a cadastros Sankhya.
- Reduzir o tempo de consulta ao dicionário de dados sem distribuir metadados de
  identidade, domínio ou conteúdo integral de scripts da exportação original.
- Fazer cada novo projeto registrar seu próprio perfil técnico, mantendo a
  biblioteca central sanitizada e reutilizável.
- Tornar builds, nomes de artefatos e organização MVC previsíveis entre projetos.
- Reduzir leitura repetida, exploração ampla e consumo de tokens.
- Corrigir o falso positivo de Gradle causado por arquivos dentro de `templates/`.
- Referência formal de Issue ou OS: **Pendente de registro antes do merge**.

> Estado desta versão: Pendente de validação pela equipe.
