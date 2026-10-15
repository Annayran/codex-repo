# Padrões do Projeto

## Organização de Código

- Pacote principal observado: `br.com.zaya.apontamentoProducao`.
- A rotina de agendamento fica em `scheduleActions`.
- SQL usado pela action fica no mesmo pacote da classe Java, permitindo carregamento por `NativeSql.loadSql(ApontamentoProducaoSchedule.class, "queApo.sql")`.
- Arquivos antigos ou legados ficam em `old/`.

## Nomenclatura

- Classes Java em PascalCase: `ApontamentoProducaoSchedule`.
- Pacotes usam minúsculas/camelCase conforme legado: `apontamentoProducao`, `scheduleActions`.
- Métodos existentes misturam camelCase e PascalCase (`ConfirmarApontamento`, `AtualizarSerie`); manter consistência local ao alterar código legado, mas preferir camelCase em novos métodos quando possível.
- SQL externo com nome curto e descritivo: `queApo.sql`.

## Separação de Responsabilidades

- A `ScheduledAction` concentra orquestração, consulta, regra de negócio e persistência. Isso reflete o estado atual do projeto.
- Para evoluções futuras, preferir separar:
  - carregamento/consulta de apontamentos;
  - confirmação de apontamentos;
  - atualização de séries;
  - tratamento de erros e logs.
- Não mover código sem testes ou validação em ambiente Sankhya, pois as APIs dependem de contexto de execução.

## Acesso a Dados

- O projeto usa `NativeSql` para consultas/updates diretos.
- O projeto usa Jape (`JapeFactory.dao`) para acesso a entidades Sankhya como `CabecalhoApontamento`, `ApontamentoPA` e `SerieProduto`.
- SQL carregado de arquivo deve permanecer junto da classe consumidora quando usado por `loadSql`.
- Evitar concatenação de SQL com valores dinâmicos em novas implementações; preferir parâmetros quando a API permitir.

## Logs

- O código atual usa `System.out.println` e `printStackTrace`.
- Dependências de SLF4J/Logback estão configuradas no Gradle, mas o uso no código ainda não foi identificado.
- Para novas alterações, preferir logger estruturado se o ambiente Sankhya permitir e se isso for compatível com o deploy.

## Tratamento de Erros

- O código captura exceções no fluxo principal e atualiza `msgErro`.
- Em falhas de confirmação/atualização de série, há update em `TPRAPO.OBSERVACAO` indicando erro no apontamento.
- Recursos JDBC/SQL são fechados em blocos `finally` ou helpers como `JdbcWrapper.closeSession`, `NativeSql.releaseResources` e `JdbcUtils.closeResultSet`.
- Atenção: há chamadas a `rsApontamentos.close()` em `try/catch/finally`; revisar risco de `NullPointerException` ou fechamento duplicado antes de refatorar.

## Boas Práticas Obrigatórias

- Não alterar código-fonte sem validar dependências locais Sankhya e impacto no ambiente de execução.
- Manter SQLs versionados junto ao pacote que os carrega.
- Marcar como “A confirmar” qualquer regra não demonstrada pelo código.
- Preservar compatibilidade Java 8 enquanto o ambiente de execução não for confirmado.
- Antes de buildar, corrigir o Gradle wrapper ou usar Gradle global instalado.

## Anti-padrões a Evitar

- Criar regras novas a partir de suposição sobre o Sankhya.
- Misturar `build.gradle` e `build.gradle.kts` com configurações divergentes.
- Depender de paths locais antigos como `C:/sk-java/workspace/APIs`.
- Adicionar bibliotecas sem confirmar se elas podem ser empacotadas ou se já existem no servidor Sankhya.
- Expandir responsabilidades da `ScheduledAction` sem separar riscos e sem plano de teste.