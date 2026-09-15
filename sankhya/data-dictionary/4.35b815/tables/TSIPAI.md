# TSIPAI — Países

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 2
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NacionalidadeParceiro | Nacionalidade do Parceiro | S | N |
| Pais | Países | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABREVIATURA | Abreviatura | S |  |  | N |
| CODPAIS | Código do país | I |  |  | N |
| CODPAISFIS | País Domicílio Fiscal | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| TIMNACIONALIDAD | Nacionalidade | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | NacionalidadeParceiro / [TSIPAI](TSIPAI.md) | NACIONALIDADE->CODPAIS | I |
| Contato / [TGFCTT](TGFCTT.md) | Pais / [TSIPAI](TSIPAI.md) | TIMNACIONALIDAD->CODPAIS | I |
| Feriado / [TSIFER](TSIFER.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| MetaAtual / [TGMMET](TGMMET.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Pais / [TSIPAI](TSIPAI.md) | TIMNACIONALIDAD->CODPAIS | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| UnidadeFederativa / [TSIUFS](TSIUFS.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
