# TGFCFP — TransportadoraCalculoFrete

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TransportadoraCalculoFrete | Transportadoras | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| NUCFR | Cód. Rota | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TabelaCalculoFrete / [TGFCFR](TGFCFR.md) | TransportadoraCalculoFrete / [TGFCFP](TGFCFP.md) | NUCFR->NUCFR | I |
| TransportadoraCalculoFrete / [TGFCFP](TGFCFP.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
