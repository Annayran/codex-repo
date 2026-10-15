# Migracao Codex - TennisPlace

Data: 2026-06-11

## Objetivo

Normalizar o projeto Gradle Java 8 em `C:/zy-tools/workspace/TennisPlace`.

## Ajustes

- `build.gradle` passou a usar `C:/zy-tools/modulos-sankhya/Dependencias`.
- Wrapper Gradle atualizado para `9.0.0`, evitando download de `8.13`.

## Validacao

Comando:

```powershell
.\gradlew.bat clean build
```

Resultado:

`BUILD SUCCESSFUL`

Artefato:

`build/libs/CRMBonusIntegration.jar`
