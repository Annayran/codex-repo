# TCAALU — Alunos

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 9
- Instâncias DWF: 2
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Aluno2 | teste mais uma vez | S | S |
| Alunos | Aluno | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANOINGRESSO | Ano de Ingresso | I |  |  | N |
| CODMAE | Cód.Mãe | I |  |  | N |
| CODPAI | Cód.Pai | I |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODRESP | Cód.Responsável | I |  |  | N |
| CODTPRESP | Cód.Tipo responsável | I |  |  | N |
| DTDESATIVO | DTDESATIVO | S |  |  | N |
| FOTO | Foto | B |  |  | N |
| SEXO | Sexo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Materiais / [TCAMAT](TCAMAT.md) | Alunos / [TCAALU](TCAALU.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
