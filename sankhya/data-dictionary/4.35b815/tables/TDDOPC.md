# TDDOPC — Opção de campos

- Módulo técnico: [TDD](../modules/TDD.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OpcaoCampo | OpcaoCampo | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUCAMPO | NUCAMPO | I |  |  | N |
| OPCAO | OPCAO | S |  |  | N |
| ORDEM | ORDEM | I |  |  | N |
| PADRAO | PADRAO | S |  |  | N |
| VALOR | VALOR | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Campo / [TDDCAM](TDDCAM.md) | OpcaoCampo / [TDDOPC](TDDOPC.md) | NUCAMPO->NUCAMPO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
