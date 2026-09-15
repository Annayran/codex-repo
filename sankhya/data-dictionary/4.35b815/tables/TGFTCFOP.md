# TGFTCFOP — Tributações por CFOP

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TributacoesPorCFOP | Tributações por CFOP | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCFO | CODCFO | I |  |  | N |
| CODTRIB | Tributação | I |  |  | N |
| TRIBUTADASCIAP | Utilizar nas Tributadas do CIAP | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | TributacoesPorCFOP / [TGFTCFOP](TGFTCFOP.md) | CODCFO->CODCFO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
