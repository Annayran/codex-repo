# TIMLCL — Locatários

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
| TimLocatario | Inquilinos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LCLBOLETO | Responsável pelo boleto | S |  |  | N |
| LCLLOCACAO | Locação | I |  |  | N |
| LCLLOCATARIO | Inquilino | I |  |  | N |
| LCLPERCENTUAL | LCLPERCENTUAL | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocatario / [TIMLCL](TIMLCL.md) | LOCCODIGO->LCLLOCACAO | I |
| TimLocatario / [TIMLCL](TIMLCL.md) | Parceiro / [TGFPAR](TGFPAR.md) | LCLLOCATARIO->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
