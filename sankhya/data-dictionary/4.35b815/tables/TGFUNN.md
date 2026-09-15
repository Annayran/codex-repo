# TGFUNN — Unidade de negócio

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 2
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadeNegocio | Un. de negócio | S | N |
| UnidadeNegocioCompradora | Un. de negócio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUNN | Cód. Unidade | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CentroResultado / [TSICUS](TSICUS.md) | UnidadeNegocio / [TGFUNN](TGFUNN.md) | CODUNN->CODUNN | I |
| GrupoUsuario / [TSIGRU](TSIGRU.md) | UnidadeNegocio / [TGFUNN](TGFUNN.md) | CODUNN->CODUNN | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | UnidadeNegocio / [TGFUNN](TGFUNN.md) | CODUNNVENDEDORA->CODUNN | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | UnidadeNegocioCompradora / [TGFUNN](TGFUNN.md) | CODUNNCOMPRADORA->CODUNN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
