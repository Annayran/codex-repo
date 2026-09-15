# VIMAPR — View Compradores Loteamento

- Módulo técnico: [VIM](../modules/VIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimViewCompradorLoteFiltro | Compradores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LTELOTEAMENTO | Loteamento | I |  |  | N |
| LVCCLIENTE | Cliente | I |  |  | N |
| LVCDESCRICAO | Descrição | S |  |  | N |
| LVCPROCURADOR | Procurador | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimViewCompradorLoteFiltro / [VIMAPR](VIMAPR.md) | Parceiro / [TGFPAR](TGFPAR.md) | LVCCLIENTE->CODPARC | I |
| TimViewCompradorLoteFiltro / [VIMAPR](VIMAPR.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | LVCPROCURADOR->CODPARC | I |
| TimViewCompradorLoteFiltro / [VIMAPR](VIMAPR.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | LTELOTEAMENTO->LOTCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
