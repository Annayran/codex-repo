# TCSEQP — Equipe

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Equipe | Equipe | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVA | Ativa | S |  |  | N |
| CODCOORD | Coordenador | I |  |  | N |
| CODEMPFUN | Empresa | I |  |  | N |
| NOME | Nome Da Equipe | S |  |  | N |
| NOMECOORD | Nome Coordenador | S |  |  | S |
| NUEQUIPE | Cód. Equipe | I |  |  | N |
| TIPOCOORD | Tipo Coordenador | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Equipe / [TCSEQP](TCSEQP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPFUN->CODEMP | I |
| Equipe / [TCSEQP](TCSEQP.md) | MembroEquipe / [TCSMEQ](TCSMEQ.md) | NUEQUIPE->NUEQUIPE | I |
| EquipePorProjeto / [TAPEQJ](TAPEQJ.md) | Equipe / [TCSEQP](TCSEQP.md) | NUEQUIPE->NUEQUIPE | I |
| MembroEquipe / [TCSMEQ](TCSMEQ.md) | Equipe / [TCSEQP](TCSEQP.md) | NUEQUIPE->NUEQUIPE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
