# TGATDC — Desconto para Características

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 4
- Instâncias DWF: 2
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrecoUmidade | Tabela Preços Umidade | S | N |
| PrecoUmidadeArm | Tabela Preços Umidade | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLC | Característica Analisável | I |  |  | N |
| CODTDC | Código | I |  |  | N |
| NOMETDC | Descrição | S |  |  | N |
| STATUS | Ativo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | PrecoUmidade / [TGATDC](TGATDC.md) | TABPRECUMI->CODTDC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | PrecoUmidadeArm / [TGATDC](TGATDC.md) | TABPRECUMIAR->CODTDC | I |
| PrecosUmidadeItens / [TGARDC](TGARDC.md) | PrecoUmidade / [TGATDC](TGATDC.md) | CODTDC->CODTDC | I |
| PrecoUmidade / [TGATDC](TGATDC.md) | PrecosUmidadeItens / [TGARDC](TGARDC.md) | CODTDC->CODTDC | I |
| PrecoUmidadeArm / [TGATDC](TGATDC.md) | CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CODCLC->CODCLC | I |
| PrecoUmidadeArm / [TGATDC](TGATDC.md) | PrecosUmidadeItens / [TGARDC](TGARDC.md) | CODTDC->CODTDC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
