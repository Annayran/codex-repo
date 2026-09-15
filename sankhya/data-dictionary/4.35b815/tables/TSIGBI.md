# TSIGBI — Informações de Gadgets p/ BIA

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GadgetBiaInformacao | Informações de Gadgets p/ BIA | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPOAGRUP | Campo para Agrupamento | S |  |  | N |
| CAMPOVALOR | Campo para Valor | S |  |  | N |
| CODGBI | Código | I |  |  | N |
| DESCRGBI | Descrição | S |  |  | N |
| IDCMP | ID do Componente | S |  |  | N |
| IDINFOBIA | ID da Informação | S |  |  | N |
| IDLVL | ID do Nível | S |  |  | N |
| NUGDG | Gadget | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GadgetBiaInformacao / [TSIGBI](TSIGBI.md) | Gadget / [TSIGDG](TSIGDG.md) | NUGDG->NUGDG | I |
| GadgetBiaInformacao / [TSIGBI](TSIGBI.md) | GadgetBiaParametro / [TSIGBP](TSIGBP.md) | CODGBI->CODGBI | I |
| GadgetBiaParametro / [TSIGBP](TSIGBP.md) | GadgetBiaInformacao / [TSIGBI](TSIGBI.md) | CODGBI->CODGBI | I |
| GadgetBiaVinculo / [TSIGBV](TSIGBV.md) | GadgetBiaInformacao / [TSIGBI](TSIGBI.md) | CODGBI->CODGBI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
