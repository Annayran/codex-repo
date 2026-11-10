# Padroes de Acesso a Dados

## Padroes Confirmados no Repositorio

- SQL Oracle e PL/SQL em `sql/financeiro-devolucao-compensacao`.
- JSP com SQL montado em JavaScript e executado via `DbExplorerSP.executeQuery`.
- Uso de tabelas nativas Sankhya: `TGFCAB`, `TGFVAR`, `TGFFIN`, `TSIAVI`, `TSIRAV`, `TGFNUM`, `TGFPAR`, `TSIUSU`.
- Uso de tabela customizada `AD_LOGCOMPDEV`.

## Padroes A Confirmar

- Uso de `JapeWrapper`, `NativeSql`, `JdbcWrapper`, `EntityFacade` ou JDBC puro em codigo-fonte Java deste workspace. Nao ha fonte Java versionado aqui.
- Versao do Oracle e recursos disponiveis.
- Politica local para binds no `DbExplorerSP.executeQuery`.

## Regras Obrigatorias

- Usar bind parameters sempre que possivel.
- Evitar concatenacao insegura de SQL com entrada de usuario.
- Preservar aliases consumidos por JSP, dashboard, Jasper ou integracao.
- Cuidado com `LEFT JOIN` que vira `INNER JOIN` por filtro no `WHERE`.
- Cuidado com cardinalidade e duplicidade em joins com `TGFVAR`, `TGFFIN` e tabelas de log.
- Evitar query dentro de loop quando um `JOIN` ou processamento em lote resolver.
- Evitar atualizacao direta em tabelas sensiveis sem regra clara.
- Tratar `NULL` antes de usar `DynamicVO`, `BigDecimal`, `String` ou datas.
- Nao comparar `BigDecimal` com `==`; usar `compareTo` quando houver Java.

## Cuidados Especificos

- O JSP atual possui SQL embutido; se a query SQL versionada mudar, sincronizar a tela.
- A query de relatorio usa aliases consumidos pelo mapeamento posicional em `mapearLinha`; alterar ordem/colunas exige ajustar a JSP.
- `TGFFIN.NUCOMPENS` esta documentado como criterio confirmado de compensacao da devolucao.
