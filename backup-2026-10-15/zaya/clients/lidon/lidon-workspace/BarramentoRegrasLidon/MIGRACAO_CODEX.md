# Migracao Codex - Lidon BarramentoRegrasLidon

Data: 2026-06-11

## Objetivo

Adicionar Gradle Java 8 ao projeto IntelliJ legado `BarramentoRegrasLidon`.

## Ajustes

- Criado `settings.gradle`.
- Criado `build.gradle`.
- Copiado Gradle Wrapper.
- Mantido `src` como diretorio de fontes.
- Configurado jar final como `BarramentosRegrasLidon.jar`.
- Corrigidos imports ausentes em `src/ReservaEstoque/repository/ReservaRepository.java`.

## Dependencias locais

`C:/zy-tools/modulos-sankhya/Dependencias`

## Validacao

Comando:

```powershell
.\gradlew.bat clean build
```

Resultado:

`BUILD SUCCESSFUL`

Artefato:

`build/libs/BarramentosRegrasLidon.jar`
