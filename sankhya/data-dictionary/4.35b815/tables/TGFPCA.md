# TGFPCA — PlanejamentoCompra

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PlanejamentoCompra | Planejamento de Compras | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CONFIG | CONFIG | C |  |  | N |
| DESCRPCA | Descrição | S |  |  | N |
| DTPROCES | Dt. Processamento | D |  |  | N |
| NUMODELO | Modelo de Planejamento | I |  |  | N |
| NUPCA | Núm. Planejamento | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PlanejamentoCompra / [TGFPCA](TGFPCA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PlanejamentoCompra / [TGFPCA](TGFPCA.md) | ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | NUPCA->NUPCA | I |
| PlanejamentoCompra / [TGFPCA](TGFPCA.md) | ModeloPlanejamentoCompra / [TGFMPC](TGFMPC.md) | NUMODELO->NUMODELO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
