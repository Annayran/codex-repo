# Migracao Codex - Skala/Sankhya

Data: 2026-06-11

## Objetivo

Adicionar uma camada Gradle Java 8 ao projeto `sankhya-om-plataforma-main` sem apagar a estrutura original Eclipse/IntelliJ.

## Estrutura alvo

- `settings.gradle` na raiz do projeto.
- `build.gradle` na raiz do projeto.
- Modulos Gradle:
  - `SKL-Core-Model`
  - `SKL-Core-Action`
  - `SKL-Core-VC`

## Dependencias locais

O build usa as libs Sankhya em:

`C:/zy-tools/modulos-sankhya/Dependencias`

Esse caminho pode ser sobrescrito com a variavel de ambiente:

`SANKHYA_LIBS`

## Encoding

Os fontes legados do projeto compilam com `windows-1252`. O Gradle foi configurado com esse encoding para preservar os arquivos atuais sem conversao massiva de acentos.

## Origem analisada

- Workspace atual: `C:/zy-tools/workspace/Skala/sankhya-om-plataforma-main`
- Material Codex completo: `C:/Users/annayran.ferreira/Documents/Codex/2026-06-05/files-mentioned-by-the-user-intpdd/work/Skala-2-clean/skala`
- Pacotes Gradle menores encontrados no Codex:
  - `Skala-2-gradle`
  - `Skala-final-lote*`
  - `Skala-xml-auto-remessa-tgfvar-package`
  - `Skala-botao-acao-remessa-tgfvar-package`

## Observacao

Os pacotes menores parecem entregas especificas e nao substituem automaticamente o projeto completo. Eles devem ser consolidados depois por diff de classes/pacotes antes de entrar no projeto principal.

## Validacao

Comando executado na raiz do workspace:

```powershell
.\gradlew.bat clean distAll check
```

Resultado:

`BUILD SUCCESSFUL`

Artefatos gerados:

- `SKL-Core-Model/build/libs/skl-core-model-1.0-SNAPSHOT.jar`
- `SKL-Core-Action/build/libs/skl-core-action-1.0-SNAPSHOT.jar`
- `SKL-Core-VC/build/libs/skl-core-vc-1.0-SNAPSHOT.jar`

Avisos restantes:

- Java 8 (`sourceCompatibility`/`targetCompatibility`) em JDK moderno gera aviso de opcao obsoleta.
- Algumas classes legadas em `SKL-Core-Action` geram avisos `unchecked`.
- Nao ha testes executaveis nos modulos atuais; o Gradle reportou `NO-SOURCE` para testes.
