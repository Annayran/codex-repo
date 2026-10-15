# Relatorio - Migracao Skala/Sankhya para Gradle

Data: 2026-06-11

## Workspace atualizado

`C:\zy-tools\workspace\Skala\sankhya-om-plataforma-main`

## O que foi feito

- Adicionado `settings.gradle` multi-modulo.
- Adicionado `build.gradle` Gradle Java 8.
- Copiado Gradle Wrapper (`gradlew`, `gradlew.bat`, `gradle/`).
- Atualizado `.gitignore` para ignorar builds Gradle, `bin`, `out`, `.gradle` e arquivos de IDE.
- Criado backup da `.gitignore` anterior em `.gitignore.codex-backup`.
- Criado `MIGRACAO_CODEX.md` na raiz do workspace.

## Modulos Gradle

- `SKL-Core-Model`
- `SKL-Core-Action`
- `SKL-Core-VC`

## Configuracoes importantes

- Java 8: `sourceCompatibility` e `targetCompatibility`.
- Encoding: `windows-1252`, necessario por causa dos fontes legados com acentos.
- Dependencias Sankhya: `C:\zy-tools\modulos-sankhya\Dependencias`.
- O caminho das dependencias pode ser sobrescrito com `SANKHYA_LIBS`.

## Validacao executada

```powershell
.\gradlew.bat clean distAll check
```

Resultado:

`BUILD SUCCESSFUL`

## Artefatos gerados

- `SKL-Core-Model\build\libs\skl-core-model-1.0-SNAPSHOT.jar`
- `SKL-Core-Action\build\libs\skl-core-action-1.0-SNAPSHOT.jar`
- `SKL-Core-VC\build\libs\skl-core-vc-1.0-SNAPSHOT.jar`

## Observacoes

- O projeto do workspace nao estava como repositorio Git nessa pasta.
- Foram encontrados pacotes menores no Codex, como `Skala-2-gradle`, `Skala-final-lote*`, `Skala-xml-auto-remessa-tgfvar-package` e `Skala-botao-acao-remessa-tgfvar-package`.
- Esses pacotes parecem entregas especificas e devem ser consolidados por diff antes de entrarem no projeto principal.
- Restaram apenas avisos de Java 8 em JDK moderno e `unchecked` em codigo legado.
