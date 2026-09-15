# TIMCHI — TABLE TIMCHI

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimItensCheckList | Itens do CheckList | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHICHKNUCHECK | Nu. CheckList | I |  |  | N |
| CHIDTMARCADO | Dt. de Marcação | H |  |  | N |
| CHIMARCADO | Check | S |  |  | N |
| CHINUITEM | Nu. Item | I |  |  | N |
| CHIOBIGATORIO | Obrigatório | S |  |  | N |
| CHIOBSERVACAO | Observação | S |  |  | N |
| CHIUSUARIO | Usuário de Alteração | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimCheckList / [TIMCHK](TIMCHK.md) | TimItensCheckList / [TIMCHI](TIMCHI.md) | CHKNUCHECK->CHICHKNUCHECK | I |
| TimItensCheckList / [TIMCHI](TIMCHI.md) | TimCheckList / [TIMCHK](TIMCHK.md) | CHICHKNUCHECK->CHKNUCHECK | I |
| TimItensCheckList / [TIMCHI](TIMCHI.md) | TimItemCheckList / [TIMICK](TIMICK.md) | CHINUITEM->ICKNUITEM | I |
| TimItensCheckList / [TIMCHI](TIMCHI.md) | Usuario / [TSIUSU](TSIUSU.md) | CHIUSUARIO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
