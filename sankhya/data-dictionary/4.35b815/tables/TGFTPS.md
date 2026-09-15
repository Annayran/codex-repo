# TGFTPS — Tipos de Sazonalidades

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoSazonalidade | Tipos de Sazonalidades | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPSAZ | Cód. Tipo Sazonalidade | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| SazonalidadeProduto / [TGFSAZ](TGFSAZ.md) | TipoSazonalidade / [TGFTPS](TGFTPS.md) | CODTIPSAZ->CODTIPSAZ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
