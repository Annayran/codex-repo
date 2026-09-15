# TGFUNM — Unidades de Medida

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadeMedida | Unidade de Medida | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUNOTA | Nro. Único da Nota | I |  |  | N |
| QTDMED | Quantidade na medida | F |  |  | N |
| SEQMED | Sequência da Medida | I |  |  | N |
| TIPMED | Tipo da Medida | S |  |  | N |
| UNMED | Unidade de Medida | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | UnidadeMedida / [TGFUNM](TGFUNM.md) | NUNOTA->NUNOTA | I |
| UnidadeMedida / [TGFUNM](TGFUNM.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
