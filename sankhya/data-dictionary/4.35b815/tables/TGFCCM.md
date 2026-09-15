# TGFCCM — Comissão Multipla

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ComissaoMultipla | Comissão Múltipla | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODVEND | Vendedor | I |  |  | N |
| NUNOTA | Número Único Nota | I |  |  | N |
| OBS | Observação | S |  |  | N |
| PERCCOM | Percentual de Comissão | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ComissaoMultipla / [TGFCCM](TGFCCM.md) | NUNOTA->NUNOTA | I |
| ComissaoMultipla / [TGFCCM](TGFCCM.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
