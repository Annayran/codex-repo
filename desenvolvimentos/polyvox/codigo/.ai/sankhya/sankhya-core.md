# Guia Base Sankhya

## Visao Geral

Customizacoes Sankhya nao devem ser tratadas como CRUD Java/SQL comum. O ERP possui fluxos nativos para documentos, financeiro, estoque, fiscal, liberacoes, integracoes e auditoria. Antes de alterar qualquer regra, confirmar o processo de negocio e o ponto correto de extensao.

## Entidades Centrais

Confirmado como relevante para este repositorio:

- `TGFCAB`: cabecalho de notas/documentos.
- `TGFITE`: itens de notas/documentos, quando a demanda envolver itens.
- `TGFVAR`: vinculo entre documento de origem e destino.
- `TGFFIN`: financeiro, incluindo `NUCOMPENS` na frente de compensacao.
- `TSIAVI`: avisos/pop-ups.
- `TSIRAV`: visualizacao de avisos.
- `TGFNUM`: numeracao usada nos avisos atuais.
- `AD_LOGCOMPDEV`: log customizado da frente de compensacao financeira.

Inferido por padrao Sankhya e deve ser validado por demanda:

- `TGFTOP`: comportamento da TOP.
- `TGFPAR`: parceiro.
- `TGFPRO`: produto.
- `TGFEST`: estoque.
- `TSILIB`: liberacoes.
- Tabelas `AD_`: regras customizadas do cliente.

## Regras Obrigatorias

- Nao inserir ou alterar nota/documento ignorando o fluxo Sankhya.
- Nao criar nota apenas inserindo `TGFCAB`/`TGFITE` sem validar servico, action ou padrao existente.
- Sempre analisar documento de origem e destino antes de gerar, cancelar, faturar, devolver ou transferir documento.
- Se envolver nota, pedido, transferencia, devolucao, remessa ou faturamento, verificar necessidade de `TGFVAR`.
- Se envolver estoque ou financeiro, validar efeitos colaterais antes de gravar.
- Se envolver confirmacao ou liberacao, nao simular confirmacao apenas alterando status/campo.
- Quando algo depender de rotina especifica do cliente/projeto, registrar como "A confirmar".

## Cuidados por Processo

- Documento: validar `NUNOTA`, `NUMNOTA`, `CODTIPOPER`, `TIPMOV`, origem/destino e status.
- Estoque: avaliar local, controle, lote/serie, saldo, reserva e atualizacao de custos quando aplicavel.
- Financeiro: avaliar `TGFFIN`, baixa, compensacao, renegociacao, vencimentos e duplicidade.
- Fiscal: avaliar impostos, status NFe, livros e autorizacao antes de qualquer alteracao.
- TOP: validar empresa, parceiro, natureza, centro de resultado, local, financeiro, estoque e integracoes.
- Integracoes: conferir se dashboards, Jasper, Flow, jobs ou APIs consomem os mesmos dados.

## A Confirmar

- Versao do Sankhya e parametrizacoes do ambiente.
- Servicos internos disponiveis para geracao/confirmacao de documentos.
- Campos customizados `AD_` usados em TOPs, telas, flows, dashboards e integracoes.
