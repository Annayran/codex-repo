# Stack Tecnica

## Linguagem

- Java 8 para compatibilidade Sankhya (`sourceCompatibility`/`targetCompatibility` como `JavaVersion.VERSION_1_8`).
- JSP/HTML/CSS/JavaScript no componente HTML5.
- PowerShell para scripts de contexto.

## Frameworks

- Gradle com plugin `java`.
- APIs Sankhya:
  - `AcaoRotinaJava`
  - `ContextoAcao`
  - `Registro`
  - `NativeSql`
  - `JapeFactory`
  - `DynamicVO`
  - `EntityFacadeFactory`

## Bibliotecas

Declaradas no `build.gradle`:

- `jackson-databind`
- `pdfbox`
- `slf4j-api`
- `logback-classic`
- JUnit 5

Dependencias Sankhya locais:

```text
C:/zy-tools/modulos-sankhya/Dependencias/Jar
C:/zy-tools/modulos-sankhya/Dependencias/4_35/4_35
```

Uso efetivo de Jackson/PDFBox/Logback neste fluxo: A confirmar.

## Banco de Dados

Confirmado por codigo:

- consultas via `NativeSql`;
- tabelas `AD_VGLDOC`, `TGFCAB`, `TGFITE`, `TGFACT`, `TGFPRO`, `AD_TGLCAB`, `AD_TCRNEGCAB`;
- uso de sintaxe Oracle/Sankhya (`NVL`, `ROW_NUMBER()`).

Banco/versao exata: A confirmar.

## Build e Execucao

Modulo principal:

```text
momento-negociacao
```

Artefatos:

```text
momento-negociacao/build/libs/CreativeLike.jar
momento-negociacao/outputs/modal-momento-negociacao-html5.zip
```

## Comandos Uteis

Build Java:

```powershell
cd .\momento-negociacao
.\gradlew.bat clean build
```

Validar bytecode Java 8:

```powershell
javap -verbose -classpath "build\libs\CreativeLike.jar" br.com.zaya.like.Actions.CancelarDocumentoNegociacaoAction | Select-String "major version"
```

Validar JavaScript do JSP:

```powershell
# usar Node local disponivel no ambiente Codex quando necessario
```

Gerar ZIP HTML5:

```powershell
Compress-Archive -Path "momento-negociacao\prototipos\modal-momento-negociacao\html5-component\modalMomentoNegociacao.jsp" -DestinationPath "momento-negociacao\outputs\modal-momento-negociacao-html5.zip" -Force
```

## A Confirmar

- Versao exata do Sankhya.
- Versao exata do banco.
- Comando oficial de deploy do JAR no ambiente.
- `nuGdg` definitivo por ambiente.

