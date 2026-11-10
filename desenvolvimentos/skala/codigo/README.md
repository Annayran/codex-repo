# Zaya / Skala - Particionamento de Pedidos

Projeto Java puro para validar o algoritmo de particionamento antes da integracao com Sankhya.

## Como abrir no IntelliJ

1. Abra esta pasta como projeto.
2. Importe como Gradle.
3. Use uma JDK instalada, preferencialmente 17 ou 21 para desenvolvimento local.
4. O codigo esta configurado com compatibilidade Java 8 para facilitar posterior adaptacao ao ambiente Sankhya.

## Tarefas Gradle

```powershell
.\gradlew.bat run
.\gradlew.bat runScenarios
.\gradlew.bat check
```

Para a camada Sankhya, coloque os JARs do ambiente em `libs/sankhya` e rode:

```powershell
.\gradlew.bat compileSankhya
.\gradlew.bat sankhyaJar
```

## Estrutura

- `src/main/java`: algoritmo puro e models.
- `src/test/java`: runner simples com cenarios do PDD, sem JUnit.
- `src/sankhya/java`: acao customizada Sankhya, repository e gerador de OE.
- `libs/sankhya`: pasta para os JARs do Sankhya/MGE usados na compilacao.

## Proxima etapa

Antes de publicar no Sankhya:

- Configurar o parametro numerico `QTDE_PALETES` no botao da acao.
- Conferir campos obrigatorios por TOP no ambiente Skala.
- Colocar os JARs Sankhya em `libs/sankhya`.
- Executar `.\gradlew.bat compileSankhya`.
