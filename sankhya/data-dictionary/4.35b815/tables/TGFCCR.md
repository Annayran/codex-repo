# TGFCCR — Cartão de Crédito

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CartaoCredito | Cartão de Crédito | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CGC_CPF | CPF | S |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| DTCADASTRO | Dt. Cadastro | H |  |  | N |
| DTEMBOSSAMENTO | Dt. Embossamento | H |  |  | N |
| DTVENCIMENTO | Dt. Vencimento | H |  |  | N |
| NOMENOCARTAO | Nome no cartão | S |  |  | N |
| NROCARTAO | Nro. do cartão | S |  |  | N |
| NROCARTAOMASC | Nro. do cartão | S |  |  | N |
| NUCCR | Nro. Único | I |  |  | N |
| NUCCRTITULAR | NUCCRTITULAR | I |  |  | N |
| NULEB | Lote Embossamento | I |  |  | N |
| STATUS | Status do cartão | S |  |  | N |
| TITULAR | Titular | S |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CartaoCredito / [TGFCCR](TGFCCR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CartaoCredito / [TGFCCR](TGFCCR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CartaoCredito / [TGFCCR](TGFCCR.md) | NUCCR->NUCCR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
