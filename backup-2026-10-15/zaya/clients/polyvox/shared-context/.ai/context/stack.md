# Stack Técnica

## Linguagem

- Java 8 no projeto `PolyvoxTelecontrol` (`sourceCompatibility` e `targetCompatibility` definidos como `JavaVersion.VERSION_1_8`).
- JSP/HTML/CSS/JavaScript no dashboard `dashboard_os_tlc`.
- SQL Oracle/Sankhya embutido em Java e JSP via Jape/NativeSql/consultas JSP.

## Frameworks

- Gradle com plugin `java`.
- Sankhya ERP SDK/API: `AcaoRotinaJava`, `ContextoAcao`, Jape, `EntityFacade`, `NativeSql`, `DynamicVO`, `JapeFactory`.
- Cuckoo Scheduler (`org.cuckoo.core.ScheduledAction`) para rotinas agendadas.

## Bibliotecas

Confirmadas em `PolyvoxTelecontrol/build.gradle`:

- `com.fasterxml.jackson.core:jackson-databind:2.17.0`.
- `org.apache.pdfbox:pdfbox:2.0.30`.
- `org.slf4j:slf4j-api:2.0.9`.
- `ch.qos.logback:logback-classic:1.4.14`.
- JUnit 5 (`junit-bom:5.10.0`, `junit-jupiter`, `junit-platform-launcher`) para testes.
- Dependências locais Sankhya via `compileOnly fileTree` em `C:/zy-tools/modulos-sankhya/Dependencias`.

Também aparecem imports de Gson em parsers legados, provavelmente vindos das dependências locais Sankhya/legadas. A origem exata deve ser confirmada.

## Banco de Dados

Banco de dados exato: A confirmar.

Evidências apontam para ambiente Sankhya com SQL compatível com Oracle, uso de `NVL`, tabelas nativas `TGFCAB`, `TGFITE`, `TGFVAR`, `TGFPAR` e tabelas customizadas `AD_*`.

Tabelas customizadas identificadas:

- `AD_TCLCFG`: configuração Telecontrol.
- `AD_OSTLC`: ordens de serviço integradas.
- `AD_OSPECA`: peças associadas à OS.
- `AD_TCLPED`: pedidos Telecontrol.
- `AD_TCLPCA`: peças/produtos Telecontrol.
- `AD_TCLLGT`: logs da integração.

## Build e Execução

- Build via Gradle Wrapper no diretório `PolyvoxTelecontrol`.
- Wrapper configurado para Gradle `9.0.0`.
- Artefato final configurado: `build/libs/TelecontrolPolyvox.jar`.
- O projeto depende de jars locais do Sankhya para compilar.
- Execução final ocorre dentro do Sankhya, após cadastro/associação das actions, schedulers e jar.

## Comandos Úteis

Executar a partir de `C:/zy-tools/workspace/Polyvox/PolyvoxTelecontrol`:

```powershell
.\gradlew.bat clean build
.\gradlew.bat test
.\gradlew.bat jar
```

No PowerShell/Linux shell equivalente:

```sh
./gradlew clean build
./gradlew test
./gradlew jar
```

## A Confirmar

- Versão do Java Runtime usada em produção no Sankhya.
- Versão do Sankhya/SDK e jars locais em `C:/zy-tools/modulos-sankhya/Dependencias`.
- Banco exato e versão.
- Processo oficial de deploy do `TelecontrolPolyvox.jar`.
- Se `dashboard_os_tlc` ou `Dashboards/dashboard_os_tlc` é a fonte principal.
