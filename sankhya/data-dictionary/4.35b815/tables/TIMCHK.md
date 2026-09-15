# TIMCHK — TABLE TIMCHK

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimCheckList | CheckList de Contratos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHKCONCLUIDO | Concluído | S |  |  | S |
| CHKCONTRATOADM | Contrato de Administração | I |  |  | N |
| CHKCONTRATOCLA | Contrato de Venda | I |  |  | N |
| CHKCONTRATOLOC | Contrato de Locação | I |  |  | N |
| CHKCONTRATOLTV | Contrato de Venda de Lote | I |  |  | N |
| CHKDTINC | Dt. Inclusão | H |  |  | N |
| CHKNUCHECK | Nu. CheckList | I |  |  | N |
| CHKRESCISAOLOC | Rescisão de Locação | I |  |  | N |
| CHKTIPOCONTRATO | Tipo de contrato | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimCheckList / [TIMCHK](TIMCHK.md) | TimContratoAdm / [TIMADM](TIMADM.md) | CHKCONTRATOADM->ADMNUCONTRATO | I |
| TimCheckList / [TIMCHK](TIMCHK.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | CHKCONTRATOCLA->CLACODIGO | I |
| TimCheckList / [TIMCHK](TIMCHK.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | CHKCONTRATOLTV->LTVCODIGO | I |
| TimCheckList / [TIMCHK](TIMCHK.md) | TimItensCheckList / [TIMCHI](TIMCHI.md) | CHKNUCHECK->CHICHKNUCHECK | I |
| TimCheckList / [TIMCHK](TIMCHK.md) | TimLocacao / [TIMLOC](TIMLOC.md) | CHKCONTRATOLOC->LOCCODIGO | I |
| TimCheckList / [TIMCHK](TIMCHK.md) | TimRescisaoLoc / [TIMREC](TIMREC.md) | CHKRESCISAOLOC->RECCODIGO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimCheckList / [TIMCHK](TIMCHK.md) | ADMNUCHECKLIST->CHKNUCHECK | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimCheckList / [TIMCHK](TIMCHK.md) | CLANUCHECKLIST->CHKNUCHECK | I |
| TimItensCheckList / [TIMCHI](TIMCHI.md) | TimCheckList / [TIMCHK](TIMCHK.md) | CHICHKNUCHECK->CHKNUCHECK | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimCheckList / [TIMCHK](TIMCHK.md) | LOCNUCHECKLIST->CHKNUCHECK | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
