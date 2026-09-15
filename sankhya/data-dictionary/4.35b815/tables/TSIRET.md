# TSIRET — Retorno Movimento Bancário com Hierarquia

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RetornoMovimentoBancario | Retorno Movimento Bancário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODIGO | Cód.Layout | I |  |  | N |
| CODPAI | Cód.Pai | I |  |  | N |
| CONCEXTBANC | Conciliação Extrato Bancário | S |  |  | N |
| CONFIGTAXAADMIN | Taxa Administrativa | S |  |  | N |
| GRAU | Grau | I |  |  | N |
| SP_CHAMADA | Stored Chamada | S |  |  | N |
| TITULO | Título do Layout | S |  |  | N |
| USASQLNUFIN | Usa SQL Para Número Único | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendadorRetornoBancario / [TGFRET](TGFRET.md) | RetornoMovimentoBancario / [TSIRET](TSIRET.md) | CODIGOLAYOUT->CODIGO | I |
| ItensPacote / [TSSITP](TSSITP.md) | RetornoMovimentoBancario / [TSIRET](TSIRET.md) | CODRET->CODIGO | I |
| RetornoMovimentoBancario / [TSIRET](TSIRET.md) | DetalheRetornoMovimentoBco / [TSIIRT](TSIIRT.md) | CODIGO->CODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
