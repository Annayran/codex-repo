# TIMRET — Reajuste

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimReajuste | Reajuste Parcelas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RETCODIGO | Nro. Reajuste | I |  |  | N |
| RETDETALHES | Detalhes | S |  |  | N |
| RETDTCADASTRO | Dh. Cadastro | H |  |  | N |
| RETDTREAJUSTE | Dt. Reajuste | D |  |  | N |
| RETDTUSUALTEROU | Usuário Alteração | I |  |  | N |
| RETDTUSUINCLIU | Usuário Inclusão | I |  |  | N |
| RETESTAGIO | Estágio | S |  |  | N |
| RETSTART | Dh. Início | H |  |  | N |
| RETSTOP | Dh. Termíno | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimReajuste / [TIMRET](TIMRET.md) | TimReajusteContrato / [TIMRCV](TIMRCV.md) | RETCODIGO->RCVREAJUSTE | I |
| TimReajuste / [TIMRET](TIMRET.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | RETDTUSUALTEROU->CODUSU | I |
| TimReajuste / [TIMRET](TIMRET.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | RETDTUSUINCLIU->CODUSU | I |
| TimReajusteContrato / [TIMRCV](TIMRCV.md) | TimReajuste / [TIMRET](TIMRET.md) | RCVREAJUSTE->RETCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
