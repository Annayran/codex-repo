# Migracao Codex - Polyvox ImportadorPedido

Data: 2026-06-11

## Objetivo

Adicionar Gradle Java 8 ao projeto legado `ImportadorPedido`.

## Ajustes

- Criado `settings.gradle`.
- Criado `build.gradle`.
- Copiado Gradle Wrapper.
- Source set aponta para os diretorios legados: `action`, `controller`, `helper`, `models`, `repository`, `service`.
- Jar final: `ImportadorPVX.jar`.

## Validacao

Comando:

```powershell
.\gradlew.bat clean build
```

Resultado:

`BUILD SUCCESSFUL`

Artefato:

`build/libs/ImportadorPVX.jar`
