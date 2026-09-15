# TSIGBC — Perguntas Aprendidas pela BIA

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GadgetBiaConhecimento | Perguntas Aprendidas pela BIA | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGBC | Código | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| NUDSB | Dashboard | I |  |  | N |
| PERGUNTA | Pergunta | S |  |  | N |
| QTDUSO | Quantidade de uso da pergunta | I |  |  | N |
| RESOURCEID | ID da Tela | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GadgetBiaConhecimento / [TSIGBC](TSIGBC.md) | Dashboard / [TSIDSB](TSIDSB.md) | NUDSB->NUDSB | I |
| GadgetBiaConhecimento / [TSIGBC](TSIGBC.md) | GadgetBiaVinculo / [TSIGBV](TSIGBV.md) | CODGBC->CODGBC | I |
| GadgetBiaConhecimento / [TSIGBC](TSIGBC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| GadgetBiaVinculo / [TSIGBV](TSIGBV.md) | GadgetBiaConhecimento / [TSIGBC](TSIGBC.md) | CODGBC->CODGBC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
