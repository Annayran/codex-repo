# Padrões observados nos projetos

Snapshot sanitizado de 2026-09-02. Este documento registra padrões técnicos,
não nomes, regras ou código de clientes. O inventário dinâmico da skill prevalece
sobre estes números.

## Fatos observados

- 18 raízes de projeto com Gradle foram identificadas em `clients` e `platform`.
- 17 usam `build.gradle`; 2 usam `build.gradle.kts`; 1 mantém as duas linguagens
  de build simultaneamente.
- 17 das 18 raízes possuem Gradle Wrapper.
- 16 das 18 possuem `settings.gradle` ou `settings.gradle.kts` na própria raiz.
- 5 apresentam diretórios com nomes associados a MVC, sem implementação
  uniforme do padrão.
- Somente 4 das 18 raízes estavam contidas em um repositório Git no snapshot.
- Nomes de JAR, grupos Java, versões e pacotes-base variam entre projetos.

## Padrões úteis

- Java 8 é recorrente e deve ser preservado até confirmação diferente.
- Gradle com dependências Sankhya locais é o modelo dominante nos módulos
  legados.
- Ações, eventos e integrações se beneficiam de adaptadores pequenos e regras em
  serviços/model.
- Builds existentes frequentemente configuram o nome do JAR, mas nem sempre
  incluem uma versão publicada.

## Riscos recorrentes

- `SNAPSHOT` ou versão ausente no artefato de entrega.
- coexistência injustificada de Groovy e Kotlin DSL.
- pacotes-base divergentes e FQCNs vinculados a cadastros externos.
- projetos sem repositório remoto, sem `settings.gradle` ou sem wrapper.
- artefatos `build`/`out` presentes junto ao código-fonte.

## Uso

Ao abrir um projeto, execute a descoberta da skill e trate este snapshot apenas
como lista de verificações. Nunca conclua compatibilidade ou regra funcional por
semelhança com outro projeto.
