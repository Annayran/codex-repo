# Sankhya Core

## Visao Geral

Customizacoes Sankhya nao devem ser tratadas como CRUD Java/SQL comum. Documentos, itens, estoque, financeiro, fiscal, confirmacao, liberacao e integracoes dependem de fluxos nativos do ERP e de configuracoes de TOP.

## Entidades Centrais

- `TGFCAB`: cabecalho de documentos.
- `TGFITE`: itens de documentos.
- `TGFVAR`: vinculo entre documento origem e destino.
- `TGFTOP`: configuracao da TOP.
- `TGFPAR`: parceiros.
- `TGFPRO`: produtos.
- `TGFEST`: estoque.
- `TGFFIN`: financeiro.
- `TSILIB`: liberacoes.
- `AD_*`: tabelas customizadas do cliente/projeto.

## Regras Fortes

- Nao inserir/alterar nota ou documento ignorando fluxo de negocio.
- Antes de gerar documento, analisar origem/destino.
- Se envolver nota, pedido, transferencia, devolucao, remessa ou faturamento, verificar necessidade de `TGFVAR`.
- Se envolver estoque ou financeiro, validar efeitos colaterais.
- Se envolver confirmacao/liberacao, nao simular confirmacao apenas mudando status/campo.
- Validar empresa, parceiro, produto, natureza, centro de resultado, local, controle, lote/serie quando aplicavel.

## Confirmado neste projeto

- O fluxo atual consulta Pedido(s) de Venda por `AD_VGLDOC` + `TGFCAB.TIPMOV = 'P'`.
- O cancelamento atual atua em `TGFITE`, grava `TGFACT`/`AcompanhamentoNota` e usa `AD_ITEMCANCELADO = 'S'` como trava de recancelamento.
- A regra atual mantem `TGFITE.PENDENTE = 'S'`.

## Inferido por padrao Sankhya

- Alteracoes em documentos podem impactar estoque, financeiro, fiscal e rastreabilidade.
- TOP pode alterar comportamento do documento mesmo quando a tela parece igual.

## A confirmar

- Versao exata do Sankhya.
- Rotinas nativas disponiveis para geracao/confirmacao/cancelamento de documentos no ambiente do cliente.
- Se existem flows, integracoes ou dashboards externos consumindo os campos alterados.

