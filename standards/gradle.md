# Gradle

## Obrigatória

- Versionar o Gradle Wrapper completo: `gradlew`, `gradlew.bat`,
  `gradle/wrapper/gradle-wrapper.jar` e `gradle-wrapper.properties`.
- Manter uma única linguagem principal de build por projeto. A coexistência de
  `build.gradle` e `build.gradle.kts` exige justificativa documentada.
- Declarar explicitamente a compatibilidade Java.
- Não versionar caches `.gradle` nem artefatos de `build`.
- Usar `compileOnly` para bibliotecas fornecidas pelo runtime Sankhya, salvo
  necessidade diferente comprovada.
- Não incluir JAR proprietário no repositório do cliente sem autorização de
  licenciamento e segurança.
- Validar a existência do diretório de dependências antes da compilação.
- Importar o projeto no IntelliJ a partir do Gradle. Arquivos `.idea`, `*.iml`,
  `out` e configurações locais não são fonte do build e não devem ser versionados.

## Identificação e versão do artefato

Todo JAR de entrega deve seguir:

```text
ZAYA-<SIGLA_DO_PROJETO>-V<MAJOR>.<MINOR>.jar
```

- `SIGLA_DO_PROJETO` usa somente `A-Z` e `0-9` e deve ser estável.
- `V1.0` identifica a primeira entrega controlada.
- Ajuste, correção ou melhoria de comportamento existente incrementa o menor:
  `V1.0` para `V1.1`.
- Funcionalidade nova incrementa o maior e reinicia o menor: `V1.1` para `V2.0`.
- O Gradle é a única fonte da versão e do nome do JAR.
- `CHANGELOG.md`, manifesto e documentação de publicação devem registrar a mesma
  versão.
- Não publique `SNAPSHOT`, nomes genéricos ou JAR sem versão.

O manifesto deve declarar pelo menos `Implementation-Title`,
`Implementation-Version`, `Implementation-Vendor` e `Built-By`, sem usuário,
hostname, caminho local ou outro dado variável. O build deve ordenar entradas e
remover timestamps dos arquivos para melhorar a reprodutibilidade.

## Dependências Sankhya locais

Os projetos devem localizar a raiz das dependências nesta ordem:

1. propriedade Gradle `sankhyaDependenciesDir`;
2. variável de ambiente `SANKHYA_DEPENDENCIES_DIR`;
3. fallback corporativo `C:/dev/zaya/platform/modulos-sankhya/Dependencias`.

A pasta de versão deve ser configurável por `sankhyaVersionDir`. O projeto deve
registrar no README a versão validada, sem presumir que todos os clientes usam a
mesma distribuição.

## Validação mínima

```powershell
.\gradlew.bat clean build
```

Além do resultado do build, confirme:

- bytecode compatível com o Java de destino;
- recursos SQL presentes no JAR;
- ausência de dependências de runtime Sankhya empacotadas indevidamente;
- testes executados ou justificativa para `NO-SOURCE`;
- working tree sem artefatos gerados inesperados.
- nome do JAR compatível com `^ZAYA-[A-Z0-9]+-V[0-9]+\\.[0-9]+\\.jar$`.

## Recomendada

Utilizar repositório interno de artefatos quando houver autorização para
distribuição das bibliotecas. A definição do repositório corporativo oficial
está **Pendente de validação pela equipe**.
