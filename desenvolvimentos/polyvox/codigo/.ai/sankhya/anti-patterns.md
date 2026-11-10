# Anti-padroes Sankhya

Evitar obrigatoriamente:

- Inserir nota/documento diretamente em `TGFCAB`/`TGFITE` sem validar servico/padrao existente.
- Ignorar `TGFVAR` em processo com origem/destino.
- Atualizar status de nota diretamente para simular confirmacao.
- Fazer commit/rollback manual sem entender transacao Sankhya.
- Engolir excecao e continuar fluxo.
- Deixar `NativeSql`, `JdbcWrapper` ou `ResultSet` aberto.
- Usar `Integer`/`Long` de forma insegura onde o projeto usa `BigDecimal`.
- Comparar `BigDecimal` com `==`.
- Concatenar SQL com parametros do usuario.
- Alterar alias SQL consumido por dashboard, Jasper ou tela.
- Criar regra fora do padrao de package do projeto.
- Refatorar codigo legado sem necessidade.
- Criar dependencia nova para resolver problema simples.
- Alterar query sem avaliar cardinalidade dos joins.
- Duplicar item sem validar quantidade pendente, faturada ou entregue.
- Atualizar estoque/financeiro manualmente sem regra clara.
- Assumir que toda TOP se comporta igual.
- Alterar campos `AD_` sem verificar uso em telas, flows, dashboards ou integracoes.

## Anti-padroes Observados como Risco no Repositorio

- SQL embutido na JSP ficar divergente da query versionada.
- Numeracao manual com `MAX(...) + 1` sem avaliar concorrencia.
- Reaplicar DDL em ambiente onde a tabela ja existe sem checagem.
- Usar artefatos `.jar` externos como fonte de verdade sem confirmar o que esta publicado no Sankhya.
