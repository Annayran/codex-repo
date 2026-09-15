# TGFVCF — Volumes da Conferência

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VolumeConferencia | Volumes da Conferência | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUCONF | Nro. Conferência | I |  |  | N |
| ORDEM | Nro. Volume | I |  |  | N |
| SEQVOL | Seq. Volumes | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoConferencia / [TGFCON2](TGFCON2.md) | VolumeConferencia / [TGFVCF](TGFVCF.md) | NUCONF->NUCONF | I |
| VolumeConferencia / [TGFVCF](TGFVCF.md) | ItemVolumeConferencia / [TGFIVC](TGFIVC.md) | NUCONF->NUCONF; SEQVOL->SEQVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
