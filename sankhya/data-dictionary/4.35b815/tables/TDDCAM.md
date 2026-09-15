# TDDCAM — Descrição de Campos

- Módulo técnico: [TDD](../modules/TDD.md)
- Campos: 18
- Instâncias DWF: 2
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Campo | Descrição de Campos | S | N |
| CampoDestino | Campo de destino | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ADICIONAL | Adicional | S |  |  | N |
| APRESENTACAO | Campo Apresentação | S |  |  | N |
| CALCULADO | Campo calculado | S |  |  | N |
| DESCRCAMPO | Descrição do campo | S |  |  | N |
| EXPRESSAO | Expressão | S |  |  | N |
| MASCARA | Mascara | S |  |  | N |
| NOMECAMPO | Nome do campo | S |  |  | N |
| NOMETAB | Nome da Tabela Pai | S |  |  | N |
| NUCAMPO | Núm. do campo | I |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| PERMITEPADRAO | Permite padrão | S |  |  | N |
| PERMITEPESQUISA | Permite pesquisa | S |  |  | N |
| REQUERIDO | Requerido | S |  |  | S |
| SISTEMA | Campo de sistema | S |  |  | N |
| TAMANHO | Tamanho | I |  |  | N |
| TIPCAMPO | Tipo do Campo | S |  |  | N |
| TIPOAPRESENTACAO | Tipo de apresentação | S |  |  | N |
| VISIVELGRIDPESQUISA | Aparece no grid de pesquisa | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Campo / [TDDCAM](TDDCAM.md) | AtributoCampo / [TDDPCO](TDDPCO.md) | NUCAMPO->NUCAMPO | I |
| Campo / [TDDCAM](TDDCAM.md) | OpcaoCampo / [TDDOPC](TDDOPC.md) | NUCAMPO->NUCAMPO | I |
| CampoLigacao / [TDDLGC](TDDLGC.md) | Campo / [TDDCAM](TDDCAM.md) | NUCAMPOORIG->NUCAMPO | I |
| CampoLigacao / [TDDLGC](TDDLGC.md) | CampoDestino / [TDDCAM](TDDCAM.md) | NUCAMPODEST->NUCAMPO | I |
| Tabela / [TDDTAB](TDDTAB.md) | Campo / [TDDCAM](TDDCAM.md) | NOMETAB->NOMETAB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
