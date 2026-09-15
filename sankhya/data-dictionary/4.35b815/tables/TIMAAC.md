# TIMAAC — TABLE TIMAAC

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimAcumuloOrigem | Parcelas Origem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AACACUMULO | Acumulo | I |  |  | N |
| AACDTVENC | Dt. Venc | D |  |  | N |
| AACNUFIN | Nro. Único | I |  |  | N |
| AACREPASSEGERADO | Repasse Gerado | S |  |  | N |
| AACVLRCORRMONET | Vlr. Corr. Monetária | F |  |  | N |
| AACVLRDESC | Vlr. Desc | F |  |  | N |
| AACVLRIRRF | Vlr. IRRF | F |  |  | N |
| AACVLRJURO | Vlr. Juro | F |  |  | N |
| AACVLRMULTA | Vlr. Multa | F |  |  | N |
| AACVLRPRINCIPAL | Vlr. Principal | F |  |  | N |
| AACVLRTOTAL | Vlr. Total Líquido | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAcumulo / [TIMACL](TIMACL.md) | TimAcumuloOrigem / [TIMAAC](TIMAAC.md) | ACLCODIGO->AACACUMULO | I |
| TimAcumuloOrigem / [TIMAAC](TIMAAC.md) | TimAcumulo / [TIMACL](TIMACL.md) | AACACUMULO->ACLCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
