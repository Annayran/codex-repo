# TGFCGTVE — Complementos da GTV-e

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
| ComplementosGTVE | Complementos da GTV-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IDCGTVE | ID Complemento GTV-e | I |  |  | N |
| IDGTVE | ID Informacoes GTV-e | I |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| TIPOCOMP | Componentes do Valor da GTV-e | I |  |  | N |
| VCOMP | Valor do Numerário | F |  |  | N |
| XCOMP | Complemento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ComplementosGTVE / [TGFCGTVE](TGFCGTVE.md) | NUNOTA->NUNOTA | I |
| ComplementosGTVE / [TGFCGTVE](TGFCGTVE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| InformacoesGTVE / [TGFIGTVE](TGFIGTVE.md) | ComplementosGTVE / [TGFCGTVE](TGFCGTVE.md) | IDGTVE->IDGTVE; NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
