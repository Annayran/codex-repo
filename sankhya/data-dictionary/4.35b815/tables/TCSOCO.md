# TCSOCO — Ocorrências

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Ocorrencia | Ocorrência | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODOCOR | Ocorrência | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| SITPROD | Situação do produto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OcorrenciaContrato / [TCSOCC](TCSOCC.md) | Ocorrencia / [TCSOCO](TCSOCO.md) | CODOCOR->CODOCOR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
