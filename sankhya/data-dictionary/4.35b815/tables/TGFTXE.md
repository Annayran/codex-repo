# TGFTXE — Títulos por evento cobrança

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TituloPorEventoRegua | Títulos por evento cobrança | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODREGUA | Código da Régua | I |  |  | N |
| MENSAGEM | Mensagem | S |  |  | N |
| NUFIN | Nro. Financeiro | I |  |  | N |
| SEQEXEC | Sequência de Execução | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExecReguaCobranca / [TGFEXR](TGFEXR.md) | TituloPorEventoRegua / [TGFTXE](TGFTXE.md) | CODREGUA->CODREGUA; SEQEXEC->SEQEXEC | I |
| TituloPorEventoRegua / [TGFTXE](TGFTXE.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| TituloPorEventoRegua / [TGFTXE](TGFTXE.md) | TituloPorEventoReguaStatus / [TGFTXECONTROLST](TGFTXECONTROLST.md) | CODREGUA->CODREGUA; NUFIN->NUFIN; SEQEXEC->SEQEXEC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
