# TSIGBV — Informações Vinculada à BIA

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GadgetBiaVinculo | Informações Vinculada à BIA | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGBC | Pergunta | I |  |  | N |
| CODGBI | Informação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GadgetBiaConhecimento / [TSIGBC](TSIGBC.md) | GadgetBiaVinculo / [TSIGBV](TSIGBV.md) | CODGBC->CODGBC | I |
| GadgetBiaVinculo / [TSIGBV](TSIGBV.md) | GadgetBiaConhecimento / [TSIGBC](TSIGBC.md) | CODGBC->CODGBC | I |
| GadgetBiaVinculo / [TSIGBV](TSIGBV.md) | GadgetBiaInformacao / [TSIGBI](TSIGBI.md) | CODGBI->CODGBI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
