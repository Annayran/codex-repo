# TGFMOT — Motoristas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MotoristaCTe | Motorista CT-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro Motorista | I |  |  | N |
| NUNOTA | Nro. Único da Nota | I |  |  | N |
| SEQMOT | Sequência do Motorista | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | MotoristaCTe / [TGFMOT](TGFMOT.md) | NUNOTA->NUNOTA | I |
| MotoristaCTe / [TGFMOT](TGFMOT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| MotoristaCTe / [TGFMOT](TGFMOT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
