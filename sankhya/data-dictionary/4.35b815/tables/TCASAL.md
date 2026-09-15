# TCASAL — Salas

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Sala | Salas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVA | Ativa | S |  |  | N |
| METRAGEM | Metragem | F |  |  | N |
| NOME | Nome | S |  |  | N |
| NUSALA | Número Sala | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrecificacaoCurso / [TCACPR](TCACPR.md) | Sala / [TCASAL](TCASAL.md) | NUSALA->NUSALA | I |
| Sala / [TCASAL](TCASAL.md) | PrecificacaoCurso / [TCACPR](TCACPR.md) | NUSALA->NUSALA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
