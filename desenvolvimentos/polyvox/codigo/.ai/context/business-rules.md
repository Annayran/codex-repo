# Regras de Negocio

## Entidades Principais

- Nota de devolucao: nota com `CODTIPOPER IN (1200, 1201)`.
- Nota de origem: nota original vinculada pela relacao `TGFVAR.NUNOTAORIG`.
- Relacao devolucao/origem: `TGFVAR.NUNOTA = NUNOTA` da devolucao e `TGFVAR.NUNOTAORIG = NUNOTA` da origem.
- Aviso financeiro: pop-up registrado em `TSIAVI`.
- Visualizacao de aviso: leitura registrada em `TSIRAV`.
- Log de processo: tabela `AD_LOGCOMPDEV`.
- Compensacao financeira: identificada por `TGFFIN.NUCOMPENS IS NOT NULL`.

## Processos

- Identificar notas de devolucao das TOPs `1200` e `1201`.
- Relacionar cada devolucao com sua nota de origem via `TGFVAR`.
- Para teste, gerar aviso para `CODUSU IN (20, 0, 37)`.
- Registrar cada aviso em `AD_LOGCOMPDEV`.
- Criar pop-up em `TSIAVI` com orientacao para compensar o financeiro da devolucao na origem.
- Atualizar visualizacao quando existir registro correspondente em `TSIRAV`.
- Atualizar status para `COMPENSADO` quando houver `TGFFIN.NUCOMPENS` para a nota de devolucao.
- Exibir relatorio com status calculado e dados de nota, parceiro, usuario, aviso e compensacao.

## Validacoes

- Nao avisar novamente a mesma combinacao de devolucao, origem e usuario quando o log estiver em `AVISADO`, `VISUALIZADO` ou `FECHADO`.
- Nao gerar aviso para devolucao ja compensada.
- Considerar apenas devolucoes de TOP `1200` ou `1201`.
- Considerar `TSIRAV` como evidencia de visualizacao.
- Considerar `TGFFIN.NUNOTA = NUNOTADEV` com `NUCOMPENS IS NOT NULL` como evidencia de compensacao.

## Calculos

- KPIs do JSP contam registros filtrados por status: total, avisados, visualizados e compensados.
- Status exibido no relatorio prioriza compensacao real, depois visualizacao, depois status gravado em `AD_LOGCOMPDEV`.
- Nao ha calculos financeiros confirmados neste pacote.

## Restricoes

- Usuarios de destino estao fixos para teste: `20`, `0` e `37`.
- A periodicidade das rotinas nao esta definida no repositorio.
- `ACTION_NOTIF_COMP_DEV` e `ACTION_ATUALIZA_LOG_COMP_DEV` dependem de execucao manual, action ou job externo.
- O JSP depende de sessao Sankhya valida via cookie `JSESSIONID`.

## Integracoes que Impactam Regras de Negocio

- `TGFCAB`: dados de cabecalho das notas.
- `TGFVAR`: vinculo entre devolucao e origem.
- `TGFFIN`: compensacao financeira.
- `TSIAVI`: criacao do aviso/pop-up.
- `TSIRAV`: visualizacao do aviso.
- `TGFNUM`: numeracao de aviso.
- `TGFPAR`: dados de parceiro no relatorio.
- `TSIUSU`: nomes dos usuarios no relatorio.

## Frente Telecontrol / Series

- A documentacao em `docs/telecontrol/post-serie-analise-atual.md` identifica fluxo de envio de series para API Telecontrol a partir de bytecode Java externo.
- Campos observados para envio de serie: `SERIE`, `REFERENCIA`, `DATAFABRICACAO`, `DATAVENDA` e `REVENDA`.
- A consulta observada nessa frente e `SELECT * FROM AD_TCLSER WHERE ROWNUM < 501`.
- A relacao dessa frente com a demanda de compensacao financeira deste repositorio permanece A confirmar.

## Regras a Confirmar

- Se a compensacao da nota de origem (`FINORIG.NUCOMPENS`) deve afetar status em algum cenario.
- Periodicidade ideal da notificacao.
- Periodicidade ideal da sincronizacao de visualizacao/compensacao.
- Usuarios finais de producao que receberao o aviso.
- Se o status `FECHADO` ou `CANCELADO` tera rotina propria de manutencao.
- Se o `seriesenvio.jar` faz parte de outra frente do mesmo projeto ou de uma integracao independente.

## Atualizacao 2026-06-26 - Telecontrol e Pagamento de OS

### Entidades principais confirmadas

- `AD_TCLCFG`: configuracao de API Telecontrol, endpoints e parametros.
- `AD_TCLLGT`: log tecnico das chamadas HTTP.
- `AD_TCLSER`: view de origem de series.
- `AD_TCLLOGSER`: fila/controle de series processadas.
- `AD_TCLPRO`, `AD_TCLPCA`, `AD_TCLPED`, `AD_TCLLTB`, `AD_TCLFAM`, `AD_TCLLIN`: tabelas customizadas Telecontrol documentadas.
- `AD_OSTLC`: base do dashboard de OS Telecontrol.
- `AD_OSPECA`: pecas de OS usadas para identificar OS com peca Telecontrol.
- `AD_LOGCOMPDEV`: log da frente de compensacao financeira de devolucoes.

### Processos confirmados

- Envio de series sincroniza `AD_TCLSER` para `AD_TCLLOGSER` e envia registros pendentes para `EP_SERIE`.
- Series usam `TGFPRO.REFERENCIA` via `CODPROD` como referencia preferencial do payload, com fallback para referencia do log.
- Dashboard OS classifica OS com peca quando existe peca relacionada em `AD_OSPECA` com referencia encontrada em `AD_TCLPCA`.
- Pagamento de OS usa marcadores `AD_OSTLC.OSPAGA`, `AD_OSTLC.NUFINGER` e `AD_OSTLC.NUNOTAGER`.
- Pagamento de OS selecionadas deve agrupar por `CNPJ + tipo de geracao`: OS com peca geram nota consolidada, OS sem peca geram financeiro direto consolidado.
- OS com peca e OS sem peca nao devem ser mescladas no mesmo documento/financeiro.
- O valor do financeiro direto de OS sem peca usa `TGFPAR.AD_TAXAMANUTENCAO`: individualmente como valor da OS, ou multiplicado pela quantidade quando agrupado por CNPJ.
- O financeiro direto de OS sem peca deve ser criado com `TGFFIN.ORIGEM = 'F'`, para preservar o comportamento esperado da tela financeira no lancamento do numero da nota.
- Compensacao financeira de devolucao usa TOPs `1200` e `1201`, vinculo por `TGFVAR` e compensacao por `TGFFIN.NUCOMPENS`.

### Inferido por padrao Sankhya

- OS com peca que gera nota de compra precisa validar TOP, financeiro, estoque/fiscal quando aplicavel, confirmacao e possivel `TGFVAR`.
- OS sem peca que gera financeiro direto/clonado precisa validar origem do financeiro, transacao, baixa/compensacao futura e estorno.

### Regras a confirmar

- TOP/modelo oficial para nota de compra de OS com peca.
- Parametros `TLC_GERA_NOTA_COMPRA_OS` e `AD_NUFINCLONETC`.
- IDs reais das actions do dashboard OS no ambiente.
- Se desfazer pagamento deve cancelar/estornar documento/financeiro gerado ou apenas limpar marcadores em `AD_OSTLC`.
