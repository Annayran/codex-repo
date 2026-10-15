# Stack Tecnica

## Linguagem

Java 8.

## Frameworks

- Sankhya MGE.
- Acao Agendada Java via `com.sankhya.schedule.ScheduleAction`.
- Gradle com plugin `java`.

## Bibliotecas

Dependencias configuradas em `build.gradle`:

- Jackson Databind `2.17.0`.
- Jackson XML `2.17.0`.
- Gson `2.10.1`.
- JDOM `1.1`.
- JDOM2 `2.0.6.1`.
- Commons Codec `1.16.0`.
- JUnit Jupiter `5.10.0` para testes.

Dependencias locais Sankhya esperadas em `C:/zy-tools/modulos-sankhya/Dependencias`:

- `Jar/*.jar`
- `4_35/*.jar`
- `4_35/4_35/*.jar`
- `jrepository/*.jar`
- `platina/*.jar`

## Banco de Dados

Banco de dados do ambiente Sankhya, com SQL para VIEW e tabela de controle.

Objetos documentados:

- VIEW `AD_VW_GENESIS_ESTOQUE`.
- Tabela `AD_GENMOVEST`.
- Campos adicionais: `AD_GARANTIA` e `AD_STATUSEST`, com local final ainda a confirmar.

Tabelas Sankhya citadas:

- TGFCAB, TGFITE, TGFVAR, TGFESE, TGFSER, TGFEST.
- TSIEMP, TGFPAR, TGFPRO, TGFGRU.
- TSIEND, TSIBAI, TSICID, TSIUFS/TSIVUFS, TGFLOC.

## Build e Execucao

- Build: Gradle Java 8.
- Arquivo principal: `build.gradle`.
- JAR configurado: `MA-HOSPITALAR-GENESIS-1.0-SNAPSHOT.jar`.
- Source sets:
  - `src/main/java`
  - `src/sankhya/java`
  - `src/test/java`

## Comandos Uteis

```powershell
gradle clean build
gradle test
gradle jar
```

Validacao alternativa quando Gradle nao estiver no PATH:

```powershell
javac -encoding UTF-8 -d target/classes <arquivos de src/main/java>
```

## Ambiente de Execucao

- Sankhya Cloud do cliente: A confirmar nos parametros seguros do projeto.
- Genesis API V1: host + `/G4VAPI`, recurso `/api/v1/estoques/`.
- Ambientes homologacao/producao devem ser parametrizados, nunca fixados no codigo.

## A Confirmar

- SDK Sankhya/local de libs completo no ambiente de desenvolvimento.
- Forma oficial de armazenamento seguro de token no ambiente Sankhya do cliente.
- Unidade dos campos de data epoch na API Genesis: segundos ou milissegundos.
- Se `codigoExterno` sera chave de upsert/idempotencia.

