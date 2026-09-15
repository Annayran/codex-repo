# TSIFTM — Formatador de Master

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormatadorMaster | Formatador de Master | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBCO | Código do banco | I |  |  | N |
| CODIGO | Código | I |  |  | N |
| GRAU | Grau | I |  |  | N |
| MODULO | Módulo | S |  |  | N |
| PROGRAMA | Programa | S |  |  | N |
| TAMREGISTRO | Tamanho do Registro | I |  |  | N |
| TITULO | Título | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormatadorDetalhe / [TSIFTD](TSIFTD.md) | FormatadorMaster / [TSIFTM](TSIFTM.md) | CODIGO->CODIGO; MODULO->MODULO | I |
| FormatadorMaster / [TSIFTM](TSIFTM.md) | FormatadorDetalhe / [TSIFTD](TSIFTD.md) | CODIGO->CODIGO; MODULO->MODULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
