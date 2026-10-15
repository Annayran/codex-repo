# Relatorio - Polyvox ImportadorPedido

Data: 2026-06-11

## Workspace

`C:\zy-tools\workspace\Polyvox\ImportadorPedido`

## Objetivo

Converter projeto legado IntelliJ para Gradle Java 8.

## O que foi feito

- Criado `settings.gradle`.
- Criado `build.gradle`.
- Copiado Gradle Wrapper.
- Configurado Java 8.
- Source set configurado para `action`, `controller`, `helper`, `models`, `repository` e `service`.
- Jar final configurado como `ImportadorPVX.jar`.

## Validacao

```powershell
.\gradlew.bat clean build
```

Resultado:

`BUILD SUCCESSFUL`

## Artefato

- `build\libs\ImportadorPVX.jar`
