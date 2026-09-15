# TGMDFM — Detalhe previsão Meta

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetailMetaForecast | Detalhe previsão Meta | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENARIO | Codigo cenário | I |  |  | N |
| CODMETA | Cód. Meta | I |  |  | N |
| CODUSU | Usuário Solicitante | I |  |  | N |
| DTREF | Período | D |  |  | N |
| PREVISTO | Prevista | F |  |  | N |
| RECDESP | Receita/Despesa | S |  |  | N |
| VALUEDETAIL | Valor detalhe | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetailMetaForecast / [TGMDFM](TGMDFM.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| DetailMetaForecast / [TGMDFM](TGMDFM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
