# VMILOG — ViewLogAtualizacaoMetas

- Módulo técnico: [VMI](../modules/VMI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewLogAtualizacaoMetas | View Log Atualização das Metas | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHULTEXEC | Dh. Ult. Exec. | H |  |  | N |
| NUMET | Nro. Meta | I |  |  | N |
| SITULTATUAL | Evento Ult. Exec. | S |  |  | N |
| TEMPOTOTAL | Tempo Total | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewLogAtualizacaoMetas / [VMILOG](VMILOG.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
