# Stack Técnica

## Linguagem

- Java.
- Compatibilidade configurada para Java 8 (`sourceCompatibility` e `targetCompatibility` como `JavaVersion.VERSION_1_8`).

## Frameworks

- Gradle Java Plugin.
- APIs Sankhya/Jape para acesso a entidades, sessão, DAOs e contexto.
- Cuckoo Scheduler (`org.cuckoo.core.ScheduledAction`) para execução agendada.

## Bibliotecas

Configuradas em `build.gradle`:

- `org.json:json:20231013`.
- `com.fasterxml.jackson.core:jackson-databind:2.17.0`.
- `org.apache.pdfbox:pdfbox:2.0.30`.
- `org.slf4j:slf4j-api:2.0.9`.
- `ch.qos.logback:logback-classic:1.4.14`.
- JUnit 5 (`org.junit:junit-bom:5.10.0`, `org.junit.jupiter:junit-jupiter`).
- `org.junit.platform:junit-platform-launcher` para runtime de testes.
- Dependências locais Sankhya via `fileTree` em `C:/zy-tools/modulos-sankhya/Dependencias`.

## Banco de Dados

- Banco usado pelo ambiente Sankhya, acessado via `JdbcWrapper` e `NativeSql`.
- Tipo específico do SGBD: A confirmar.
- Objetos identificados: `AD_VGFAPO`, `TPRAPO`, `TPRAPF`, `TPRIATV`, `TGFCAB`, `TGFCON2`, `TGFIVC`, `TGFPRO`, `TPRSERPA`.

## Build e Execução

- Build tool: Gradle.
- Arquivo ativo observado: `build.gradle` (Groovy DSL).
- Arquivos adicionais: `build.gradle.kts`, `settings.gradle.kts`, `gradle.properties`, `gradle/libs.versions.toml`.
- Jar configurado: `ApontamentoProducao.jar`.
- Artefato antigo/gerado observado: `build/libs/polivox-apontamento-producao-V_1.0.0.jar`.

## Comandos Úteis

```powershell
# Quando o Gradle wrapper estiver completo
.\gradlew.bat clean build
.\gradlew.bat jar
.\gradlew.bat test

# Com Gradle instalado globalmente
gradle clean build
gradle jar
gradle test
```

## A Confirmar

- Versão real do Java usada no ambiente Sankhya de destino.
- Versão exata da plataforma Sankhya e dos jars locais.
- SGBD do ambiente de produção.
- Processo oficial de implantação do `.jar` no Sankhya.
- Se `build.gradle.kts` deve ser removido/ignorado ou convertido para manter apenas um DSL.
- Como corrigir o Gradle wrapper: adicionar `gradle/wrapper/gradle-wrapper.jar` ou padronizar Gradle global.