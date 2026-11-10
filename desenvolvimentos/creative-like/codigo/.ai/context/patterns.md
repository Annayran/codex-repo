# Padroes do Projeto

## Organizacao de Codigo

Pacote base:

```text
br.com.zaya.like.Actions
```

Padrao atual:

```text
Actions/
  *Action.java
  services/
    *Service.java
  dao/
    *DAO.java
  dto/
    *DTO.java
```

## Nomenclatura

- Actions terminam com `Action`.
- Services terminam com `Service`.
- DAOs terminam com `DAO`.
- DTOs terminam com `DTO`.
- Parametros Sankhya usam nomes em caixa alta, por exemplo `P_NUNOTA_DOCUMENTO`, `P_TIPO_DOCUMENTO`, `P_MOTIVO`, `P_SEQUENCIA`.
- Campos Java usam camelCase (`nuNegociacao`, `nuNota`, `sequencia`).

## Separacao de Responsabilidades

- `Action`: entrada Sankhya, validacao de contexto, parametros e chamada do service.
- `Service`: regra de negocio, orquestracao e mensagens de resultado.
- `DAO`: SQL, update/criacao de entidades Sankhya e fechamento de recursos.
- `DTO`: transporte de dados.
- JSP/HTML5: experiencia operacional, selecao de documento/item, motivo e chamada da Action.
- JS do botao: abre o componente HTML5 e passa parametros.

## Acesso a Dados

Padroes confirmados:

- `NativeSql` com `appendSql`.
- `setNamedParameter` para parametros.
- `ResultSet` fechado no `finally`.
- `NativeSql.releaseResources(sql)` no `finally`.
- `JapeFactory.dao(...).prepareToUpdateByPK(...)` para update de `TGFITE`.
- `DynamicVO` + `createEntity("AcompanhamentoNota", ...)` para criar `TGFACT`.

## Logs

- O JSP possui painel de log visual.
- Actions retornam mensagens via `contexto.setMensagemRetorno` ou `contexto.mostraErro`.
- Padrao de log tecnico persistente: A confirmar.

## Tratamento de Erros

- Erros de usuario/validacao devem retornar mensagem clara.
- Erros criticos devem interromper fluxo para rollback.
- Nao engolir excecoes.
- Escapar HTML em mensagens que exibem texto dinamico.

## Boas Praticas Obrigatorias

- Validar linha selecionada e `NUNEGOCIACAO`.
- Nao inventar tabela/campo.
- Usar `BigDecimal` para campos numericos Sankhya.
- Nao comparar `BigDecimal` com `==`.
- Nao usar `confirmarSimNao` em Action chamada via JSP.
- Nao abrir transacao concorrente na Action chamada por `ActionButtonsSP.executeJava`.
- Regenerar ZIP sempre que o JSP mudar.
- Recompilar JAR sempre que Java mudar.
- Atualizar `.ai/context` ao final de mudanca relevante.

## Anti-padroes a Evitar

- SQL inseguro com concatenacao de parametro.
- Regra transacional complexa dentro do JSP.
- Upload de JAR compilado em Java 11.
- Alterar `TGFITE.PENDENTE` para `N` no cancelamento atual.
- Depender de fonte externa para icones do iframe HTML5; usar SVG inline.
- Refatorar estrutura sem necessidade.

## A Confirmar

- Se pastas duplicadas na raiz ainda sao usadas.
- Padrao oficial de log tecnico.
- Backend real para botoes utilitarios.

