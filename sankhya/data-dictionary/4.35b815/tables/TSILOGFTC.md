# TSILOGFTC — Log de fatura do cartão

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LogFaturaCartao | Log de fatura do cartão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| DHFAT | Data/hora da fatura | H |  |  | N |
| ERROFAT | Mensagem da geração da fatura do cartão | C |  |  | N |
| NULOG | Nro único do log | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LogFaturaCartao / [TSILOGFTC](TSILOGFTC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
