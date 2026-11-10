# Padroes do Projeto

## Organizacao de Codigo

- Agrupar artefatos por demanda funcional.
- Manter SQL em `sql/<nome-da-demanda>/`.
- Manter JSP em `jsp/<nome-da-demanda>/`.
- Manter documentos de analise em `docs/<tema>/`.
- Manter contexto de IA em `/.ai/context`.
- Separar scripts de criacao, alteracao, procedures e relatorios em arquivos distintos e numerados.

## Nomenclatura

- Usar nomes descritivos em portugues sem depender de abreviacoes obscuras.
- Prefixar objetos customizados de banco com `AD_` quando seguir padrao Sankhya.
- Procedures desta demanda usam prefixo `ACTION_`.
- Status operacionais em maiusculas: `PENDENTE`, `AVISADO`, `VISUALIZADO`, `FECHADO`, `COMPENSADO`, `CANCELADO`.
- Para novos scripts, manter prefixos numericos para indicar ordem de aplicacao.

## Separacao de Responsabilidades

- Scripts DDL criam ou alteram estrutura.
- Procedures executam regras operacionais e sincronizacao.
- SQL de relatorio deve consultar e consolidar, evitando alterar dados.
- JSP deve cuidar da experiencia visual, filtros e chamada ao servico, sem substituir procedures de negocio.
- Documentacao em `/.ai/context` deve registrar fatos confirmados e hipoteses como A confirmar.

## Acesso a Dados

- Usar SQL Oracle/PLSQL.
- Usar `TGFFIN.NUCOMPENS IS NOT NULL` como criterio confirmado de compensacao.
- Usar `TSIRAV` para leitura de visualizacao de aviso.
- Usar `TGFNUM` para numeracao de `TSIAVI`.
- Evitar duplicidade de avisos consultando `AD_LOGCOMPDEV` por devolucao, origem, usuario e status.
- Preferir scripts idempotentes ou acompanhados de checks quando houver risco de reaplicacao.

## Logs

- Log funcional principal: `AD_LOGCOMPDEV`.
- Campos relevantes: nota de devolucao, nota de origem, usuario avisado, aviso, visualizacao, compensacao, status e observacao.
- Artefato externo A confirmar: `seriesenvio.jar` grava logs em entidade/tabela `AD_TCLLGT`.

## Tratamento de Erros

- Nos scripts PL/SQL atuais nao ha blocos `EXCEPTION`; erros de compilacao/execucao sobem para o ambiente Oracle/Sankhya.
- O JSP exibe mensagem de erro quando a chamada `DbExplorerSP.executeQuery` falha.
- Analises feitas a partir de `.jar` externo devem declarar a origem e marcar conclusoes operacionais como A confirmar.
- Para evolucoes futuras, registrar erros operacionais em tabela apropriada ou observacao do log quando isso fizer sentido.

## Boas Praticas Obrigatorias

- Nao alterar codigo-fonte ou scripts de aplicacao sem explicar antes o plano quando a mudanca for significativa.
- Nao inventar regras de negocio sem evidencia no codigo, SQL ou documentacao.
- Marcar como A confirmar qualquer inferencia nao validada.
- Atualizar `checkpoint.md`, `known-issues.md` e `decisions.md` quando houver mudanca relevante.
- Validar em homologacao antes de aplicar em producao.
- Conferir se a tabela ja existe antes de aplicar DDL destrutivo ou duplicado.

## Anti-padroes a Evitar

- Reexecutar `001_create_log_table.sql` em ambiente onde `AD_LOGCOMPDEV` ja existe sem checagem.
- Duplicar avisos para a mesma nota, origem e usuario.
- Colocar regra critica apenas no JSP.
- Tratar visualizacao de pop-up como compensacao financeira.
- Usar o `.jar` externo como fonte de verdade deste repositorio sem confirmar sua relacao com a demanda.
- Criar novos artefatos fora do diretorio operacional combinado sem registrar o motivo.

## Atualizacao 2026-06-26 - Padroes Observados

### Confirmado por arquivos

- Documentacao tecnica fica em `docs/`.
- Artefatos compilados ficam em `artifacts/`.
- Dashboards/JSPs ficam em `jsp/<nome-da-frente>/`.
- Scripts SQL ficam em `sql/<nome-da-frente>/`.
- Contexto de IA fica em `.ai/context`, guias permanentes Sankhya em `.ai/sankhya`, prompts em `.ai/prompts` e scripts auxiliares em `.ai/scripts`.
- No JAR Telecontrol, o padrao desejado e pacote por feature em `br.zaya.telecontrol.<feature>`, com `actions`, `application`, `infrastructure`, `domain` quando aplicavel.

### Inferido por padrao Sankhya

- Alteracoes em Java Sankhya devem preservar FQCNs cadastrados enquanto nao houver mapeamento das actions/schedulers no ambiente.
- Bridges legadas devem ser tratadas como compatibilidade, nao como ponto principal de evolucao.
- SQL embutido em JSP deve ser mantido sincronizado com SQL versionado quando existir consulta equivalente.

### Anti-padroes adicionais

- Alterar IDs de actions no dashboard sem confirmar o cadastro no Sankhya.
- Usar JAR compilado como unica fonte para alterar regra de negocio sem validar fonte Java ou comportamento em homologacao.
- Limpar marcadores de OS (`OSPAGA`, `NUFINGER`, `NUNOTAGER`) sem definir se documento/financeiro gerado tambem deve ser estornado.
