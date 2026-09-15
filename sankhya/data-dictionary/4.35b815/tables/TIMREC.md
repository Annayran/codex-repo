# TIMREC — TABLE TIMREC

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRescisaoLoc | Rescisão de Locação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RECCODIGO | Código | I |  |  | N |
| RECCONTRATOLOC | Contrato de Locação | I |  |  | N |
| RECDTPAGAMENTO | Dt. Pagamento | D |  |  | N |
| RECDTRESCISAO | Dt. Rescisão | D |  |  | N |
| RECGEROURESCISAO | Efetivou Rescisão | S |  |  | N |
| RECLOCESTAGIO | Estágio do Contrato | S |  |  | S |
| RECNUCHECKLIST | Check List da Rescisão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimRescisaoLoc / [TIMREC](TIMREC.md) | TIMRESCISAOLOC->RECCODIGO | I |
| TimCheckList / [TIMCHK](TIMCHK.md) | TimRescisaoLoc / [TIMREC](TIMREC.md) | CHKRESCISAOLOC->RECCODIGO | I |
| TimRescisaoLoc / [TIMREC](TIMREC.md) | TimLocacao / [TIMLOC](TIMLOC.md) | RECCONTRATOLOC->LOCCODIGO | I |
| TimRescisaoLoc / [TIMREC](TIMREC.md) | TimRescisaoLocDetalhamentos / [TIMRCM](TIMRCM.md) | RECCODIGO->RCMRESCISAO | I |
| TimRescisaoLocDetalhamentos / [TIMRCM](TIMRCM.md) | TimRescisaoLoc / [TIMREC](TIMREC.md) | RCMRESCISAO->RECCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
