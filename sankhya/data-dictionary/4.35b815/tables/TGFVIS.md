# TGFVIS — Controle de Entregas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleEntrega | Controle de Entregas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODREG | Região | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| SEQVISITA | Sequência de visita | I |  |  | N |
| TIPO | Tipo de Intervalo | S |  |  | N |
| VALOR | Frequência do Dia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleEntrega / [TGFVIS](TGFVIS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ControleEntrega / [TGFVIS](TGFVIS.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
