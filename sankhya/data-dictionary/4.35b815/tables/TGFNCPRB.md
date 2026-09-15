# TGFNCPRB — Notas Ajustes p/ CPRB

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotasAjustesCPRB | Notas Ajustes p/ CPRB | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAJUSTE | Código do ajuste | I |  |  | N |
| CODCPRB | Código de atividade | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODOBRA | Cód. Obra | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| DHMOV | Dt. do movimento | D |  |  | N |
| DTDOC | Dt. do documento | D |  |  | N |
| DTREF | Período | D |  |  | N |
| NUNOTA | Nro. Único da Nota | I |  |  | N |
| SEQUENCIA | Sequência do Item | I |  |  | N |
| TIPOAJUSTE | Tipo do Ajuste | I |  |  | N |
| VLRAJUSTE | Valor do ajuste | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalhesAjustesCPRB / [TGFDACPRB](TGFDACPRB.md) | NotasAjustesCPRB / [TGFNCPRB](TGFNCPRB.md) | CODAJUSTE->CODAJUSTE; CODCPRB->CODCPRB; CODEMP->CODEMP; CODOBRA->CODOBRA; DTREF->DTREF; TIPOAJUSTE->TIPOAJUSTE | I |
| NotasAjustesCPRB / [TGFNCPRB](TGFNCPRB.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
