# TIMREL — TABLE TIMREL

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimParcelamentoAcumulo | Novas Parcelas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RELACUMULO | Acúmulo | I |  |  | N |
| RELCODIGO | Código | I |  |  | N |
| RELDHGERREPASSE | Dh. Ger. Repasse | H |  |  | N |
| RELDHGERREPPARCIAL | Dh. Ger. Rep. Parcial | H |  |  | N |
| RELDIFIRRF | Ajuste IRRF | F |  |  | N |
| RELDTREPASSE | Dt. Repasse | D |  |  | N |
| RELDTREPPARCIAL | Dt. Repasse Parcial | D |  |  | N |
| RELDTVENC | Dt. Vencimento | D |  |  | N |
| RELNUFIN | Nro. Único | I |  |  | N |
| RELVLRDESDOB | Vlr. Fechado | F |  |  | N |
| RELVLRGRAVADO | Vlr. Efetivo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAcumulo / [TIMACL](TIMACL.md) | TimParcelamentoAcumulo / [TIMREL](TIMREL.md) | ACLCODIGO->RELACUMULO | I |
| TimParcelamentoAcumulo / [TIMREL](TIMREL.md) | TimAcumulo / [TIMACL](TIMACL.md) | RELACUMULO->ACLCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
