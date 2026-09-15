# TGFDACPRB — Detalhes dos Ajustes p/ CPRB

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalhesAjustesCPRB | Detalhes dos Ajustes p/ CPRB | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAJUSTE | Código do ajuste | I |  |  | N |
| CODCPRB | Código de atividade | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODOBRA | Código da obra | S |  |  | N |
| DESCRAJUSTE | Descrição do ajuste | S |  |  | N |
| DTREF | Período | D |  |  | N |
| TIPOAJUSTE | Tipo do ajuste | I |  |  | N |
| VLRAJUSTE | Valor do ajuste | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalhesAjustesCPRB / [TGFDACPRB](TGFDACPRB.md) | NotasAjustesCPRB / [TGFNCPRB](TGFNCPRB.md) | CODAJUSTE->CODAJUSTE; CODCPRB->CODCPRB; CODEMP->CODEMP; CODOBRA->CODOBRA; DTREF->DTREF; TIPOAJUSTE->TIPOAJUSTE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
