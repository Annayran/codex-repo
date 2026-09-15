# TGFTDT — Tipo de Datas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoData | Tipo de Datas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPDT | Tipo Data | I |  |  | N |
| CORFONTEDTFIM | Cor Fonte Data Fim | S |  |  | N |
| CORFONTEDTINICIO | Cor Fonte Data Início | S |  |  | N |
| CORFUNDODTFIM | Cor Fundo Data Fim | S |  |  | N |
| CORFUNDODTINICIO | Cor Fundo Data Início | S |  |  | N |
| DESCRTIPDT | Descrição Tipo Data | S |  |  | N |
| OBS | Observação | S |  |  | N |
| PODEDTFIM | Pode Data Fim | S |  |  | N |
| PODEMEDICAO | Pode Medição | S |  |  | N |
| PODEOBS | Pode Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNotaData / [TGFCAD](TGFCAD.md) | TipoData / [TGFTDT](TGFTDT.md) | CODTIPDT->CODTIPDT | I |
| ItemNotaData / [TGFIDT](TGFIDT.md) | TipoData / [TGFTDT](TGFTDT.md) | CODTIPDT->CODTIPDT | I |
| TipoData / [TGFTDT](TGFTDT.md) | CabecalhoNotaData / [TGFCAD](TGFCAD.md) | CODTIPDT->CODTIPDT | I |
| TipoData / [TGFTDT](TGFTDT.md) | ItemNotaData / [TGFIDT](TGFIDT.md) | CODTIPDT->CODTIPDT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
