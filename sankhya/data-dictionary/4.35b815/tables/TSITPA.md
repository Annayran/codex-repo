# TSITPA — Tipo de arquivo

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoArquivo | Tipo de arquivo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRIPTION | Tipo de arquivo | S |  |  | N |
| DOCTASTE | Código | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TrabalhoImpressao / [TSIJPR](TSIJPR.md) | TipoArquivo / [TSITPA](TSITPA.md) | MIMETYPE->DOCTASTE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
