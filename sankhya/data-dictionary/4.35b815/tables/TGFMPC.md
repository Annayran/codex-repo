# TGFMPC — Modelo Planejamento Compra

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ModeloPlanejamentoCompra | Modelo de Planejamento de Compra | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CONFIG | Config. | C |  |  | N |
| DESCRMODELO | Descr. Modelo | S |  |  | N |
| NUMODELO | Núm. Modelo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PlanejamentoCompra / [TGFPCA](TGFPCA.md) | ModeloPlanejamentoCompra / [TGFMPC](TGFMPC.md) | NUMODELO->NUMODELO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
