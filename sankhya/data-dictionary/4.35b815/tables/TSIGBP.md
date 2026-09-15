# TSIGBP — Parâmetros de Gadgets p/ BIA

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GadgetBiaParametro | Parâmetros de Gadgets p/ BIA | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVE | Chave | S |  |  | N |
| CODGBI | Informação | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VLRDAT | Data/Hora | H |  |  | N |
| VLRDEC | Numérico | F |  |  | N |
| VLRTXT | Texto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GadgetBiaInformacao / [TSIGBI](TSIGBI.md) | GadgetBiaParametro / [TSIGBP](TSIGBP.md) | CODGBI->CODGBI | I |
| GadgetBiaParametro / [TSIGBP](TSIGBP.md) | GadgetBiaInformacao / [TSIGBI](TSIGBI.md) | CODGBI->CODGBI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
