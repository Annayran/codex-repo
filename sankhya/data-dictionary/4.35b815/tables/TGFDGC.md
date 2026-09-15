# TGFDGC — Desconto Grande Carga

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescontoGrandeCarga | Desconto Grande Carga | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BASEDESC | Base desconto | F |  |  | N |
| BASEDESCPROGEXT | Base desc. prog. extra | F |  |  | N |
| CONFIRMADO | Confirmado | S |  |  | N |
| NUNOTA | Nota | I |  |  | N |
| PERCDESC | Percentual desconto | F |  |  | N |
| PERCDESCPROGEXT | Perc. desc. prog. extra | F |  |  | N |
| PRIPARFIXA | Primeira parcela fixa | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| VLRDESC | Valor desconto | F |  |  | N |
| VLRDESCPROGEXT | Vlr. desc. prog. extra | F |  |  | N |
| VLRDESCPROM | Vlr. desc. promocional | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DescontoGrandeCarga / [TGFDGC](TGFDGC.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
