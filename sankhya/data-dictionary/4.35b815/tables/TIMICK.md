# TIMICK — TABLE TIMICK

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimItemCheckList | Itens para CheckList | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ICKDESCRITEM | Descrição do Item | S |  |  | N |
| ICKNUITEM | Nu. Item | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimItensCheckList / [TIMCHI](TIMCHI.md) | TimItemCheckList / [TIMICK](TIMICK.md) | CHINUITEM->ICKNUITEM | I |
| TimItensModeloCheckList / [TIMIMK](TIMIMK.md) | TimItemCheckList / [TIMICK](TIMICK.md) | IMKITEM->ICKNUITEM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
