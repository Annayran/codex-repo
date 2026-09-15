# TGFPPM — Matéria Prima dos Produtos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MateriaPrimaProduto | Matéria Prima dos Produtos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCAL | Local do Produto | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle do Produto | S |  |  | N |
| ENTRADA | Quantidade de Entradas | F |  |  | N |
| ESTOQUE | Quantidade em Estoque | F |  |  | N |
| MAQUINA | Máquina | S |  |  | N |
| NUPLAN | Número do Planejamento | I |  |  | N |
| NUPPL | Número PPL | I |  |  | N |
| PROBLEMA | Usado para Controle do Planejamento | S |  |  | N |
| QTD | Quantidade | F |  |  | N |
| SEQUENCIA | Sequência da Linha | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MateriaPrimaProduto / [TGFPPM](TGFPPM.md) | PlanejamentoProducao / [TGFPPL](TGFPPL.md) | NUPLAN->NUPLAN | I |
| MateriaPrimaProduto / [TGFPPM](TGFPPM.md) | ProdutoMateriaPrima /  | CODPROD->CODPROD | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | MateriaPrimaProduto / [TGFPPM](TGFPPM.md) | NUPLAN->NUPLAN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
