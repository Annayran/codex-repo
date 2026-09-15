# TIMMCK — TABLE TIMMCK

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimModelosCheckList | Modelos de CheckList | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| MCKDTALTER | Dt. Alteração | D |  |  | N |
| MCKDTINC | Dt. Inclusão | D |  |  | N |
| MCKNUMODELO | Nu. Modelo | I |  |  | N |
| MCKTIPOMODELO | Tipo de Contrato | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimItensModeloCheckList / [TIMIMK](TIMIMK.md) | TimModelosCheckList / [TIMMCK](TIMMCK.md) | IMKMODELOCHK->MCKNUMODELO | I |
| TimModelosCheckList / [TIMMCK](TIMMCK.md) | TimItensModeloCheckList / [TIMIMK](TIMIMK.md) | MCKNUMODELO->IMKMODELOCHK | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
