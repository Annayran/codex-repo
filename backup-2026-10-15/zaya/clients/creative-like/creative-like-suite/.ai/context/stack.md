# Stack Tecnica

## Linguagem

- Java 8 (`sourceCompatibility` e `targetCompatibility` configurados como `JavaVersion.VERSION_1_8`).
- JavaScript para botao de acao e interacao do prototipo HTML5.
- JSP/HTML/CSS para componente HTML5.

## Frameworks

- Gradle com plugin `java`.
- APIs Sankhya para acao de rotina e acesso a dados:
  - `br.com.sankhya.extensions.actionbutton.AcaoRotinaJava`
  - `ContextoAcao`
  - `Registro`
  - `NativeSql`
  - `EntityFacadeFactory`
  - `JdbcWrapper`

## Bibliotecas

Declaradas em `build.gradle`:

- `com.fasterxml.jackson.core:jackson-databind:2.17.0`
- `org.apache.pdfbox:pdfbox:2.0.30`
- `org.slf4j:slf4j-api:2.0.9`
- `ch.qos.logback:logback-classic:1.4.14`
- JUnit 5 para testes.

Dependencias locais Sankhya:

- `C:/zy-tools/modulos-sankhya/Dependencias/Jar`
- `C:/zy-tools/modulos-sankhya/Dependencias/4_35/4_35`

## Banco de Dados

O codigo usa SQL nativo via `NativeSql` e funcoes/sintaxe comuns do ambiente Sankhya/Oracle, como `NVL` e `OPTION_LABEL`.

Tabelas identificadas no codigo:

- `AD_TGLCAB`
- `AD_TCRNEGCAB`
- `AD_TCRNEGITE`
- `AD_TGLITEART`
- `TGFPRO`

Banco exato e versao: A confirmar.

## Build e Execucao

Build configurado por Gradle.

Artefato:

- `CreativeLike.jar`

Pacote HTML5:

- `outputs/modal-momento-negociacao-html5.zip`

## Comandos Uteis

```powershell
.\gradlew build
.\gradlew test
.\gradlew jar
```

Observacao: o wrapper `gradlew` nao foi identificado no repositorio atual. Se nao existir no ambiente, usar Gradle instalado localmente:

```powershell
gradle build
gradle test
gradle jar
```

Conferir conteudo do zip HTML5:

```powershell
tar -tf outputs\modal-momento-negociacao-html5.zip
```

## Ambiente de Execucao

- Java 8 para compilacao e compatibilidade com Sankhya.
- Sankhya para execucao das acoes Java e JavaScript.
- Componente HTML5 do Sankhya para importacao do zip.
- Windows como ambiente local de desenvolvimento, conforme paths do projeto.

## A Confirmar

- Versao exata do Sankhya.
- Versao exata do banco de dados.
- Se `gradle` ou `gradlew` sera o comando oficial no ambiente.
- `nuGdg` real do componente HTML5 em homologacao/producao.
- Se as dependencias Jackson/PDFBox/Logback sao usadas por codigo futuro ou herdadas de template.
