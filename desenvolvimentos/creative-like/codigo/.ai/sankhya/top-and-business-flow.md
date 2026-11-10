# TOP e Fluxo de Negocio Sankhya

## Principios

`TGFTOP`/TOP define comportamento do documento. Documentos visualmente parecidos podem ter efeitos diferentes conforme TOP, empresa, parceiro, produto, natureza, centro de resultado, local e tipo de movimento.

TOP pode impactar:

- estoque;
- financeiro;
- fiscal;
- confirmacao;
- livro;
- integracoes;
- permissoes;
- custos.

## Checklist

- Qual `CODTIPOPER`/`CODTIPOPERBAIXA` esta envolvido?
- Qual `TIPMOV`?
- Atualiza estoque?
- Gera financeiro?
- Exige confirmacao?
- Tem integracao externa?
- Ha campos `AD_` na TOP?
- Ha regra customizada por TOP?
- Ha excecoes por empresa/parceiro/produto?

## Confirmado neste projeto

- Pedido de Venda relacionado deve ser identificado por `TGFCAB.TIPMOV = 'P'`.
- `CODTIPOPER` nao deve limitar a busca atual; e informativo.

## A confirmar

- Se alguma regra futura deve variar por TOP.
- Se a Nota Mov. Interna precisa filtro adicional por TOP ou `TIPMOV`.

