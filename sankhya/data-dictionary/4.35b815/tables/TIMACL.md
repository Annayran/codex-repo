# TIMACL — TABLE TIMACL

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 24
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimAcumulo | Acúmulo de Aluguéis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACLCODIGO | Código | I |  |  | N |
| ACLCONTRATO | Contrato | I |  |  | N |
| ACLDIFIRRF | Ajuste IRRF | F |  |  | N |
| ACLDTALTERACAO | Dt. Alteração | D |  |  | N |
| ACLDTBASE | Dt. Base | D |  |  | N |
| ACLDTEFETIVA | Dt. Efetivação | D |  |  | N |
| ACLDTINCLUSAO | Dt. Inclusão | D |  |  | N |
| ACLDTVENC | Dt. Venc | D |  |  | N |
| ACLESTAGIO | Estágio | S |  |  | N |
| ACLNUFIN | Nro. Único | I |  |  | N |
| ACLNURENEG | Nro. Reneg. SKW | I |  |  | N |
| ACLPAGADORDESC | Pagador do Desconto | S |  |  | N |
| ACLUSUALTEROU | Usuário Alterou | I |  |  | N |
| ACLUSUINCLUIU | Usuário Incluíu | I |  |  | N |
| ACLVLRCORRMONET | Vlr. Corr. Monetária | F |  |  | N |
| ACLVLRDESC | Vlr. Desconto | F |  |  | N |
| ACLVLRFECHADO | Vlr. Fechado | F |  |  | N |
| ACLVLRGRAVADO | Vlr. Efetivo | F |  |  | N |
| ACLVLRHONORARIO | Vlr. Honorários | F |  |  | N |
| ACLVLRIRRF | Vlr. IRRF | F |  |  | N |
| ACLVLRJURO | Vlr. Juro | F |  |  | N |
| ACLVLRMULTA | Vlr. Multa | F |  |  | N |
| ACLVLRPRINCIPAL | Vlr. Principal | F |  |  | N |
| ACLVLRTOTAL | Vlr. Total Líquido | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAcumulo / [TIMACL](TIMACL.md) | TimAcumuloOrigem / [TIMAAC](TIMAAC.md) | ACLCODIGO->AACACUMULO | I |
| TimAcumulo / [TIMACL](TIMACL.md) | TimParcelamentoAcumulo / [TIMREL](TIMREL.md) | ACLCODIGO->RELACUMULO | I |
| TimAcumuloOrigem / [TIMAAC](TIMAAC.md) | TimAcumulo / [TIMACL](TIMACL.md) | AACACUMULO->ACLCODIGO | I |
| TimParcelamentoAcumulo / [TIMREL](TIMREL.md) | TimAcumulo / [TIMACL](TIMACL.md) | RELACUMULO->ACLCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
