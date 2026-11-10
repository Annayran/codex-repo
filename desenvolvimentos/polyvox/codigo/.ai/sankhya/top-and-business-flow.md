# TOP e Fluxo de Negocio Sankhya

## Principios

`TGFTOP`/TOP define comportamento do documento. Nunca assumir que documentos com mesmo layout possuem o mesmo efeito de negocio. TOP pode impactar estoque, financeiro, fiscal, confirmacao, livro, integracao, permissoes e rotinas customizadas.

## Checklist

- Qual `CODTIPOPER`/`CODTIPOPERBAIXA` esta envolvido?
- Qual `TIPMOV`?
- Atualiza estoque?
- Gera financeiro?
- Exige confirmacao?
- Tem integracao externa?
- Ha campos `AD_` na TOP?
- Ha regra customizada por TOP?
- Ha excecoes por empresa, parceiro ou produto?
- Em geracao de documento, qual TOP de origem e qual TOP de destino?

## No Repositorio Atual

Confirmado:

- A frente de compensacao considera `CODTIPOPER IN (1200, 1201)` para notas de devolucao.
- O processo envolve documento de devolucao, documento de origem, financeiro e aviso ao usuario.

A confirmar:

- `TIPMOV` das TOPs 1200 e 1201.
- Se as TOPs atualizam estoque, financeiro, fiscal ou exigem confirmacao em producao.
- Campos `AD_` nas TOPs.
- Excecoes por empresa, parceiro, produto, natureza, centro de resultado, local, controle, lote/serie ou integracao.
