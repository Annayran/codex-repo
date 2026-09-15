# VGFCMC7 — VIEW VGFCMC7

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewPrefixoCheque | View prefixo cheque | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBCO | Código do banco | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| PREFIXO | Prefix | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewPrefixoCheque / [VGFCMC7](VGFCMC7.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
