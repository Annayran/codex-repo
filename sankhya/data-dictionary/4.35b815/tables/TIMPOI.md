# TIMPOI — Relatorios Doc e Docx

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRelatorioPoi | Relatorios Doc e Docx | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQUIVO | Arquivo | B |  |  | N |
| CODPOI | Código | I |  |  | N |
| DESCRPOI | Descrição | S |  |  | N |
| JAVACLASS | Classe Java | S |  |  | N |
| NOMEARQUIVO | Arquivo Word | S |  |  | N |
| SINGLEQUERY | NativeSQL Query | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimRelatorioPoi / [TIMPOI](TIMPOI.md) | TimTelasPoi / [TIMPOT](TIMPOT.md) | CODPOI->CODPOI | I |
| TimTelasPoi / [TIMPOT](TIMPOT.md) | TimRelatorioPoi / [TIMPOI](TIMPOI.md) | CODPOI->CODPOI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
