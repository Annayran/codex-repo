# Padroes do Projeto

## Organizacao de Codigo

Pacote base identificado:

```text
br.com.zaya.like.Actions
```

Organizacao atual:

```text
Actions/
  MomentoNegociacaoAction.java
  services/
    MomentoNegociacaoService.java
  dao/
    MomentoNegociacaoDAO.java
  dto/
    MomentoNegociacaoDTO.java
    ItemMomentoNegociacaoDTO.java
```

## Nomenclatura

- Actions terminam com `Action`.
- Servicos terminam com `Service`.
- DAOs terminam com `DAO`.
- Objetos de transporte terminam com `DTO`.
- Campos relacionados a negociacao usam `nuNegociacao`/`nunegociacao`.
- Nomes de tabelas seguem padrao Sankhya/customizado (`AD_*`, `TGF*`).

## Separacao de Responsabilidades

- `Action`: entrada pelo Sankhya, leitura do contexto e retorno ao usuario.
- `Service`: orquestracao e montagem de resposta HTML.
- `DAO`: consultas SQL e conversao de `ResultSet` para DTOs.
- `DTO`: transporte simples de dados sem logica de negocio.
- JSP/HTML5: simulacao visual e interacao de tela, sem gravacao.
- JS de botao: abre componente HTML5 e passa parametros.

## Acesso a Dados

Padrao atual:

- usar `NativeSql`;
- montar SQL por `appendSql`;
- usar named parameter (`:NUNEGOCIACAO`);
- preencher parametro com `setNamedParameter`;
- fechar `ResultSet` e liberar `NativeSql` no `finally`.

Boas praticas para manter:

- nao concatenar valores de usuario diretamente no SQL;
- usar named parameters;
- manter consultas relacionadas ao banco dentro de DAO;
- mapear `ResultSet` para DTO antes de retornar para servico.

## Logs

Nao ha padrao de log aplicado no codigo atual.

Bibliotecas SLF4J/Logback estao declaradas no `build.gradle`, mas nao ha uso identificado nas classes lidas.

A confirmar:

- padrao oficial de log para acoes Sankhya neste cliente;
- se erros devem ir apenas para `contexto.mostraErro` ou tambem para log tecnico.

## Tratamento de Erros

Padrao atual:

- `Action` envolve execucao em `try/catch`;
- mensagens ao usuario usam `contexto.mostraErro`;
- mensagem de erro e escapada com `escapeHtml`;
- DAO lanca excecao quando `NUNEGOCIACAO` nao e encontrado em `AD_TGLCAB`;
- recursos SQL sao fechados no `finally`.

## Boas Praticas Obrigatorias

- Validar se existe linha selecionada antes de acessar campos.
- Validar se `NUNEGOCIACAO` esta preenchido.
- Escapar texto antes de renderizar HTML.
- Manter modo de simulacao sem gravacao ate a regra ser homologada.
- Gerar zip HTML5 com `modalMomentoNegociacao.jsp` direto na raiz.
- Preencher `nuGdg` no JavaScript antes de testar no Sankhya.
- Registrar informacoes nao confirmadas como "A confirmar".

## Anti-padroes a Evitar

- Implementar gravacao/cancelamento real dentro do JSP.
- Misturar SQL diretamente na `Action`.
- Concatenar parametros de usuario no SQL.
- Reaproveitar componente HTML5 produtivo para testes sem homologacao.
- Tratar regras de negocio apenas no front-end quando houver impacto real no ERP.
- Alterar codigo-fonte sem antes confirmar regra funcional e tabelas envolvidas.
