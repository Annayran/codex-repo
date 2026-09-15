# TGAGPL — Grupo para planejamento de plantio

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoPlanejamentoPlantio | Grupos para planejamento de plantio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALTPLANT | ALTPLANT | S |  |  | N |
| CODGRUPOPROD | CODGRUPOPROD | I |  |  | N |
| CODPROD | CODPROD | I |  |  | N |
| ORDEM | ORDEM | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoPlanejamentoPlantio / [TGAGPL](TGAGPL.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| GrupoPlanejamentoPlantio / [TGAGPL](TGAGPL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
