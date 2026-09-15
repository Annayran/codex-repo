# TDDPCO — Atributos de campo

- Módulo técnico: [TDD](../modules/TDD.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AtributoCampo | Atributos de Campo | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NOME | Nome | S |  |  | N |
| NUCAMPO | Número do campo | I |  |  | N |
| VALOR | Valor | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Campo / [TDDCAM](TDDCAM.md) | AtributoCampo / [TDDPCO](TDDPCO.md) | NUCAMPO->NUCAMPO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
