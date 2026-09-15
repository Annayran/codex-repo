# TCSSEM — Relacionamento serviço motivo

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RelacionamentoServicoMotivo | Motivo SubOS | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODOCOROS | Ocorrência | I |  |  | N |
| CODPROD | Produto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OcorrenciaOS / [TCSOOS](TCSOOS.md) | RelacionamentoServicoMotivo / [TCSSEM](TCSSEM.md) | CODOCOROS->CODOCOROS | I |
| RelacionamentoServicoMotivo / [TCSSEM](TCSSEM.md) | OcorrenciaOS / [TCSOOS](TCSOOS.md) | CODOCOROS->CODOCOROS | I |
| RelacionamentoServicoMotivo / [TCSSEM](TCSSEM.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | RelacionamentoServicoMotivo / [TCSSEM](TCSSEM.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
