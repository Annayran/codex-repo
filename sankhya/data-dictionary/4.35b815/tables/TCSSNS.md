# TCSSNS — Notificação Sonora

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
| NotificacaoSonora | Notificação Sonora | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQSOM | Arquivo de som | S |  |  | N |
| NUMITEM | Nro. Item | I |  |  | N |
| NUMOS | Nro. OS | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NotificacaoSonora / [TCSSNS](TCSSNS.md) | ItemOrdemServico / [TCSITE](TCSITE.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
