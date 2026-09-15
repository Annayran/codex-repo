# TGFDID — Armazena disponibilidade diária de cada máquina

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 2
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ArmazenaDisponibilidadeDiaMaq | Armazena disponibilidade diária de cada máquina | S | N |
| DisponibilidadeDiaria | Disponibilidade Diária | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Codigo do produto | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DISPMAX | Disp. diária max. | F |  |  | N |
| DISPONIBILIDADE | Disp. diária | F |  |  | N |
| DTMOV | Data do movimento | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ArmazenaDisponibilidadeDiaMaq / [TGFDID](TGFDID.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ArmazenaDisponibilidadeDiaMaq / [TGFDID](TGFDID.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ArmazenaDisponibilidadeDiaMaq / [TGFDID](TGFDID.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DisponibilidadeDiaria / [TGFDID](TGFDID.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| DisponibilidadeDiaria / [TGFDID](TGFDID.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DisponibilidadeDiaria / [TGFDID](TGFDID.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | DisponibilidadeDiaria / [TGFDID](TGFDID.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | DisponibilidadeDiaria / [TGFDID](TGFDID.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
