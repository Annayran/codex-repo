# TGARDC — Regras de Desconto por Valor Obtido

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 5
- Instâncias DWF: 2
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrecosUmidadeItens | Itens da Tabela | S | N |
| RegraDesconto | Regras de Desconto por Valor Obtido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTDC | Código da Tabela de Desconto e Característica | I |  |  | N |
| DESCONTAR | Descontar | F |  |  | N |
| PERCENTUAL | Percentual (%) | F |  |  | N |
| PRECO | Preço por SC(R$) | F |  |  | N |
| VLROBTIDO | Umidade até | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrecosUmidadeItens / [TGARDC](TGARDC.md) | PrecoUmidade / [TGATDC](TGATDC.md) | CODTDC->CODTDC | I |
| PrecoUmidade / [TGATDC](TGATDC.md) | PrecosUmidadeItens / [TGARDC](TGARDC.md) | CODTDC->CODTDC | I |
| PrecoUmidadeArm / [TGATDC](TGATDC.md) | PrecosUmidadeItens / [TGARDC](TGARDC.md) | CODTDC->CODTDC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
