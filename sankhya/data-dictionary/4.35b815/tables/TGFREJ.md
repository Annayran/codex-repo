# TGFREJ — Rejeição

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Rejeicao | Rejeição | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | Codigo | I |  |  | N |
| CODOCORRENCIA | Ocorrência | S |  |  | N |
| CODREJ | Rejeição | S |  |  | N |
| DESCRREJ | Descrição da Rejeição | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Rejeicao / [TGFREJ](TGFREJ.md) | CODIGO->CODIGO; CODOCORRENCIA->CODOCORRENCIA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
