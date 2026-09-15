# TIMFPC — Forma Pagamento Condomínio

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimFormaPagCond | Forma Pagamento Condomínio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FPCBASECALCAP | Base Cálc. Aluguel Percentual | S |  |  | N |
| FPCCONTRATOLOC | Contrato | I |  |  | N |
| FPCPERCPA | % Aluguel Percentual | F |  |  | N |
| FPCPERCPE | % Ponto Equilíbrio | F |  |  | N |
| FPCREFERENCIA | Referência | D |  |  | N |
| FPCTIPO | Tipo C.C. | S |  |  | N |
| FPCVLRFIXO | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimLocacao / [TIMLOC](TIMLOC.md) | TimFormaPagCond / [TIMFPC](TIMFPC.md) | LOCCODIGO->FPCCONTRATOLOC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
