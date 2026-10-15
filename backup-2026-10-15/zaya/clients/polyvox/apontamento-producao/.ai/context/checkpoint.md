# Checkpoint Técnico

## Data

2026-06-22

## Estado Atual

Projeto Java/Gradle clonado em `C:/zy-tools/workspace/Polyvox/Apontamento Produção`. O fluxo principal identificado é uma ação agendada para apontamento de produção no Sankhya, consultando registros pendentes em `AD_VGFAPO` e usando APIs Jape/produção para criar, validar, confirmar e atualizar apontamentos e séries.

O `build.gradle` foi ajustado para Java 8, encoding UTF-8, dependências locais Sankhya em `C:/zy-tools/modulos-sankhya/Dependencias` e geração de `ApontamentoProducao.jar`. O build ainda não pôde ser validado porque o Gradle wrapper está incompleto e não há Gradle global no PATH.

## Arquivos/Pastas Relevantes

- `build.gradle`: configuração Gradle Groovy atualizada.
- `build.gradle.kts`: configuração Kotlin DSL gerada, divergente do Groovy.
- `settings.gradle.kts`: define `rootProject.name = "polivox-apontamento-producao"` e inclui `plugin`.
- `gradle.properties`: configuration cache, parallel e build cache ativados.
- `gradle/libs.versions.toml`: arquivo gerado, sem versões configuradas.
- `src/main/java/br/com/zaya/apontamentoProducao/scheduleActions/ApontamentoProducaoSchedule.java`: rotina principal.
- `src/main/java/br/com/zaya/apontamentoProducao/scheduleActions/queApo.sql`: consulta dos apontamentos pendentes.
- `src/main/java/org/example/Main.java`: classe de exemplo/template.
- `old/`: artefatos legados.
- `.ai/context`: documentação de contexto para IA.

## O que já está claro

- A aplicação é Java e deve permanecer compatível com Java 8 por enquanto.
- A principal integração é com Sankhya/Jape e scheduler Cuckoo.
- O processamento parte de `AD_VGFAPO` com `STATUS = 'P'`.
- O código manipula apontamentos de produção, quantidade apontada, confirmação e séries.
- O projeto depende de jars locais do ecossistema Sankhya.

## O que precisa ser confirmado

- Versão do Sankhya e Java do ambiente de destino.
- SGBD usado no ambiente real.
- Processo oficial de empacotamento/deploy.
- Se o módulo `plugin` deveria existir.
- Se `build.gradle.kts` deve ser removido, sincronizado ou ignorado.
- Quem altera status de `AD_VGFAPO` após o processamento.
- Regras funcionais exatas para flags de confirmação e série.

## Próxima melhor ação sugerida

Corrigir a estrutura do Gradle wrapper ou instalar Gradle global, ajustar/remover a referência ao módulo `plugin` inexistente se confirmado, e rodar `clean build`/`jar` para obter uma validação inicial do projeto antes de qualquer alteração de código.