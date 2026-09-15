# TGFMCSI — Múltiplos Componentes de Serviço do Item

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MultiplosComponentesServicoI | Multiplos Componentes de Servico do Item | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCOMP | Cód. Componente | I |  |  | N |
| DESCRCOMP | Descrição | S |  |  | N |
| NUNOTA | Nro único | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPCOMP | Tipo de Componente | I |  |  | N |
| VLRCOMP | Vlr. Complemento | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MultiplosComponentesServicoI / [TGFMCSI](TGFMCSI.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
