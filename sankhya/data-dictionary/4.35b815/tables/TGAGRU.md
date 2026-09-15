# TGAGRU — Grupo Produção do Produto

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoProducaoProduto | Grupo de produção do produto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODEST | Estrutura de Produção | I |  |  | N |
| CODGPROD | Código | I |  |  | N |
| CODGPRODPAI | Cód. Grupo Pai | I |  |  | N |
| DESCRGPROD | Descrição | S |  |  | N |
| GRAU | Grau | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoProducaoProduto / [TGAGRU](TGAGRU.md) | EstruturaProducao / [TGFESP](TGFESP.md) | CODEST->CODEST | I |
| GrupoProducaoProduto / [TGAGRU](TGAGRU.md) | Produto / [TGFPRO](TGFPRO.md) | CODGPROD->CODGPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | GrupoProducaoProduto / [TGAGRU](TGAGRU.md) | CODGPROD->CODGPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
