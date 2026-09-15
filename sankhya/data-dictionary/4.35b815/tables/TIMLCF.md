# TIMLCF — Fiadores das locações

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLocacaoFiador | Garantia - Fiadores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LCFCANCELAMENTO | Dt. Exoneração | D |  |  | N |
| LCFESTAGIO | Estágio | I |  |  | N |
| LCFFIADOR | Fiador | I |  |  | N |
| LCFLOCACAO | Locação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoFiador / [TIMLCF](TIMLCF.md) | LOCCODIGO->LCFLOCACAO | I |
| TimLocacaoFiador / [TIMLCF](TIMLCF.md) | Parceiro / [TGFPAR](TGFPAR.md) | LCFFIADOR->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
