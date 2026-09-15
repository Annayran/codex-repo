# TGFSTMVH — Histórico

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BaseStMediaVendaHist | BaseStMediaVendaHist | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AUTOMATICO | Automático | S |  |  | N |
| CODEMP | Cód. Empresa | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONFIG | Config. Tela de cálculo | S |  |  | N |
| DHPROCESS | Data e hora do Processo | H |  |  | N |
| NROUNICO | Nro. Único | I |  |  | N |
| REFERENCIA | Referência | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BaseStMediaVendaHist / [TGFSTMVH](TGFSTMVH.md) | BaseStMediaVenda / [TGFSTMV](TGFSTMV.md) | NROUNICO->NROUNICO | I |
| BaseStMediaVendaHist / [TGFSTMVH](TGFSTMVH.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
