# TCSSLA — Cadastro de SLA

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoSLA | Cabeçalho de SLA | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCARGAHOR | Carga Horária | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| NUSLA | Nro. Único SLA | I |  |  | N |
| TIPOFILTRO | Tipo de Filtragem de Regras | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoSLA / [TCSSLA](TCSSLA.md) | IdentificacaoCargaHoraria /  | CODCARGAHOR->CODCARGAHOR | I |
| CabecalhoSLA / [TCSSLA](TCSSLA.md) | RegraSLA / [TCSRSL](TCSRSL.md) | NUSLA->NUSLA | I |
| CabecalhoSLA / [TCSSLA](TCSSLA.md) | RegraSLAPadrao / [TCSRSL](TCSRSL.md) | NUSLA->NUSLA | I |
| Contrato / [TCSCON](TCSCON.md) | CabecalhoSLA / [TCSSLA](TCSSLA.md) | NUSLA->NUSLA | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | CabecalhoSLA / [TCSSLA](TCSSLA.md) | NUSLA->NUSLA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
