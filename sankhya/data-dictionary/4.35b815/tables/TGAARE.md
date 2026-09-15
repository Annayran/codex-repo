# TGAARE — Area de Plantio

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AreaPlantio | Área de Plantio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AREAPRODUTIVA | Área Produtiva | I |  |  | N |
| AREATOTAL | Área Total | I |  |  | N |
| CODAREA | Cód.Área | I |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODVOL | Cód.Volume | S |  |  | N |
| INCRA | INCRA | S |  |  | N |
| LIVRO | LIVRO | S |  |  | N |
| MATRICULA | MATRICULA | S |  |  | N |
| NOME | Nome | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| TIPAREA | Tipo Área | S |  |  | N |
| VALOR | VALOR | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Cultura / [TGACUL](TGACUL.md) | AreaPlantio / [TGAARE](TGAARE.md) | CODAREA->CODAREA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
