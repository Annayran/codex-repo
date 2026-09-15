# TGFSTMV — Configuração de base ST pela média de venda

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BaseStMediaVenda | BaseStMediaVenda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| EXECAUT | Executa processamento automático? | S |  |  | N |
| LISPRODEXC | Lista de produtos a serem desconsiderados | S |  |  | N |
| NROUNICO | Nro. Único | I |  |  | N |
| SUBSTCOMPRA | Revenda com subst. tributária (Cálculo de Subst. na Compra) | S |  |  | N |
| SUBSTCOMPVEND | Subst. na compra e na venda (Cálculo na Compra e na Venda) | S |  |  | N |
| SUBSTVENDA | Venda com subst. tributária (Cálculo de Subst. na Venda) | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BaseStMediaVenda / [TGFSTMV](TGFSTMV.md) | BaseStMediaVendaEmp / [TGFSTMVE](TGFSTMVE.md) | NROUNICO->NROUNICO | I |
| BaseStMediaVenda / [TGFSTMV](TGFSTMV.md) | BaseStMediaVendaPar / [TGFSTMVP](TGFSTMVP.md) | NROUNICO->NROUNICO | I |
| BaseStMediaVenda / [TGFSTMV](TGFSTMV.md) | BaseStMediaVendaTop / [TGFSTMVT](TGFSTMVT.md) | NROUNICO->NROUNICO | I |
| BaseStMediaVenda / [TGFSTMV](TGFSTMV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| BaseStMediaVendaHist / [TGFSTMVH](TGFSTMVH.md) | BaseStMediaVenda / [TGFSTMV](TGFSTMV.md) | NROUNICO->NROUNICO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
