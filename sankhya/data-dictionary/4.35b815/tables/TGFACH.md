# TGFACH — Acerto de Ordem de Carga Histórico

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcertoOrdemCargaHist | Acerto de Ordem de Carga Histórico | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODHIST | Código | I |  |  | N |
| DESCRHIST | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | AcertoOrdemCargaHist / [TGFACH](TGFACH.md) | CODHISTAC->CODHIST | I |
| Financeiro / [TGFFIN](TGFFIN.md) | AcertoOrdemCargaHist / [TGFACH](TGFACH.md) | CODHISTAC->CODHIST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
