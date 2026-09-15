# TGFDPP — Dependente IR (REINF)

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DependenteIR | Dependente IR (REINF) | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODDEPEND | Código Dependente | I |  |  | N |
| CODPARC | Código Parceiro | I |  |  | N |
| CPF | CPF Dependente | S |  |  | N |
| DEPENDIR | Dependente do IR | S |  |  | N |
| DESCRICAO | Descrição Outros | S |  |  | N |
| DTFIMVIG | Data fim vigência | D |  |  | N |
| DTINIVIG | Data inicio vigência | D |  |  | N |
| ESTUDANTE | Estudante até 24 Anos | S |  |  | N |
| NOME | Nome Dependente | S |  |  | N |
| RELACIONAMENTO | Relacionamento | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DepenFinancDeducao / [TGFDFD](TGFDFD.md) | DependenteIR / [TGFDPP](TGFDPP.md) | CODDEPEND->CODDEPEND; CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | DependenteIR / [TGFDPP](TGFDPP.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
