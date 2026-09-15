# TIMIMK — TABLE TIMIMK

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimItensModeloCheckList | Itens de Modelo de CheckList | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IMKITEM | Nu.Item | I |  |  | N |
| IMKMODELOCHK | Nu. Modelo | I |  |  | N |
| IMKOBRIGATORIO | Obrigatório | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimItensModeloCheckList / [TIMIMK](TIMIMK.md) | TimItemCheckList / [TIMICK](TIMICK.md) | IMKITEM->ICKNUITEM | I |
| TimItensModeloCheckList / [TIMIMK](TIMIMK.md) | TimModelosCheckList / [TIMMCK](TIMMCK.md) | IMKMODELOCHK->MCKNUMODELO | I |
| TimModelosCheckList / [TIMMCK](TIMMCK.md) | TimItensModeloCheckList / [TIMIMK](TIMIMK.md) | MCKNUMODELO->IMKMODELOCHK | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
