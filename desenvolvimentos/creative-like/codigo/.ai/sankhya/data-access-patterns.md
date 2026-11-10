# Padroes de Acesso a Dados

## Padroes encontrados

- `NativeSql` para consultas.
- `EntityFacadeFactory.getDWFFacade()` para obter `JdbcWrapper`.
- `sql.appendSql(...)` para montar consultas.
- `sql.setNamedParameter(...)` para binds.
- `ResultSet` mapeado para DTOs.
- `JapeFactory.dao(...).prepareToUpdateByPK(...)` para update de entidade.
- `DynamicVO` e `EntityFacadeFactory.getDWFFacade().createEntity(...)` para criar `AcompanhamentoNota`.

## Cuidados obrigatorios

- Usar bind parameters sempre que possivel.
- Evitar concatenacao insegura de SQL.
- Tratar `BigDecimal` com cuidado; nao comparar com `==`.
- Tratar `null`.
- Preservar aliases SQL consumidos por JSP, dashboard, Jasper ou integracao.
- Cuidado com `LEFT JOIN` que vira `INNER JOIN` por filtro no `WHERE`.
- Evitar queries em loop quando uma consulta agregada resolve.
- Cuidado com atualizacao direta em tabelas sensiveis.

## Confirmado neste projeto

- O JSP e o DAO consultam `TGFITE` e enriquecem itens com `TGFACT.AD_ITEMCANCELADO`.
- O cancelamento grava `TGFITE.PENDENTE = 'S'` e cria `AcompanhamentoNota`.

## A confirmar

- Padrao de log tecnico.
- Se ha helpers internos do cliente para acesso a dados que devem substituir parte do DAO atual.

