# Prompt: Revisar Alteracoes

Leia antes da revisao:

- `/.ai/context/checkpoint.md`
- `/.ai/context/architecture.md`
- `/.ai/context/stack.md`
- `/.ai/context/patterns.md`
- `/.ai/context/business-rules.md`
- `/.ai/context/known-issues.md`
- todos os arquivos em `/.ai/sankhya`

Tarefa:

Revise as alteracoes recentes do repositorio em postura de code review.

Priorize achados sobre:

- regressao funcional;
- risco em `TGFCAB`, `TGFITE`, `TGFVAR`, `TGFFIN`, `TGFEST`, `TGFTOP` e tabelas `AD_`;
- transacao, rollback e alteracao parcial;
- TOP e fluxo de negocio;
- documento origem/destino;
- estoque, financeiro e fiscal;
- confirmacao/liberacao;
- integracoes externas;
- aliases SQL consumidos por JSP, Jasper, dashboard ou tela;
- concorrencia, cardinalidade e duplicidade;
- ausencia de testes ou validacao manual critica.

Formato:

1. Liste achados primeiro, por severidade, com arquivo/linha quando possivel.
2. Liste perguntas ou premissas.
3. Resuma as alteracoes apenas depois dos achados.
4. Se nao houver achados, diga claramente e registre riscos residuais.
