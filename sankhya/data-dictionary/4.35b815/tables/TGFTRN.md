# TGFTRN — TopRegraNegocio

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TopRegraNegocio | TopRegraNegocio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| NURNG | Regra | I |  |  | N |
| SEQEXEC | Seq. de execução | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegraNegocio / [TGFRNG](TGFRNG.md) | TopRegraNegocio / [TGFTRN](TGFTRN.md) | NURNG->NURNG | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | TopRegraNegocio / [TGFTRN](TGFTRN.md) | CODTIPOPER->CODTIPOPER | I |
| TopRegraNegocio / [TGFTRN](TGFTRN.md) | RegraNegocio / [TGFRNG](TGFRNG.md) | NURNG->NURNG | I |
| TopRegraNegocio / [TGFTRN](TGFTRN.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
