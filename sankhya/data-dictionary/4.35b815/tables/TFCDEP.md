# TFCDEP — Dependentes - currículo

- Módulo técnico: [TFC](../modules/TFC.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Dependentes | Dependentes | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DTNASC | Data de Nascimento | D |  |  | N |
| GRAUPARENTESCO | Grau | I |  |  | N |
| NUCURRICULO | NUCURRICULO | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| SEXO | Sexo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Curriculo / [TFCBCO](TFCBCO.md) | Dependentes / [TFCDEP](TFCDEP.md) | NUCURRICULO->NUCURRICULO | L |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
