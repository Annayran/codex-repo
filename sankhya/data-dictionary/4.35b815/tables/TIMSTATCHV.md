# TIMSTATCHV — TABLE TIMSTATCHV

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimStatusChv | Status da Chave | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| STATCOD | Código | I |  |  | N |
| STATDESCRICAO | Descrição | S |  |  | N |
| STATDISP | Disponível | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimImovelChaves / [TIMCHV](TIMCHV.md) | TimStatusChv / [TIMSTATCHV](TIMSTATCHV.md) | STATCOD->STATCOD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
