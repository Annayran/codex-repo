# TDDLIG — Ligação de tabelas

- Módulo técnico: [TDD](../modules/TDD.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Ligacao | Ligação de Tabelas | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ADICIONAL | Adicional | S |  |  | N |
| ALTERAR | Alterar | S |  |  | N |
| CONDICAO | Condição | S |  |  | N |
| EXCLUIR | Excluir | S |  |  | N |
| EXPRESSAO | Expressão | S |  |  | N |
| INSERIR | Inserir | S |  |  | N |
| NOMELIGACAO | Nome da ligação | S |  |  | N |
| NUINSTDEST | Numero da instância de destino | I |  |  | N |
| NUINSTORIG | Numero da instância de origem | I |  |  | N |
| OBRIGATORIA | Obrigatória | S |  |  | N |
| TIPLIGACAO | Tipo de Ligação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormularioFormatado / [TDDINS](TDDINS.md) | Ligacao / [TDDLIG](TDDLIG.md) | NUINSTANCIA->NUINSTORIG | I |
| Instancia / [TDDINS](TDDINS.md) | Ligacao / [TDDLIG](TDDLIG.md) | NUINSTANCIA->NUINSTORIG | I |
| Ligacao / [TDDLIG](TDDLIG.md) | CampoLigacao / [TDDLGC](TDDLGC.md) | NUINSTDEST->NUINSTDEST; NUINSTORIG->NUINSTORIG | I |
| Ligacao / [TDDLIG](TDDLIG.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTDEST->NUINSTANCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
