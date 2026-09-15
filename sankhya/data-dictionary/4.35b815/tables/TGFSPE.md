# TGFSPE — Série por empresa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SerieEmpresa | Série por empresa | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SERIE | Série | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| SerieEmpresa / [TGFSPE](TGFSPE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | SerieEmpresa / [TGFSPE](TGFSPE.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
