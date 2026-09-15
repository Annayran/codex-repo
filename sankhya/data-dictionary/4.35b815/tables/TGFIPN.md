# TGFIPN — IPI por NCM

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| IpiNCM | IPI por NCM | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIPI | Código do IPI | I |  |  | N |
| NCM | NCM | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaIPI / [TGFIPI](TGFIPI.md) | IpiNCM / [TGFIPN](TGFIPN.md) | CODIPI->CODIPI | I |
| HistoricoAliquotaIPI / [TGFHIPI](TGFHIPI.md) | IpiNCM / [TGFIPN](TGFIPN.md) | CODIPI->CODIPI | I |
| IpiNCM / [TGFIPN](TGFIPN.md) | AliquotaIPI / [TGFIPI](TGFIPI.md) | CODIPI->CODIPI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
