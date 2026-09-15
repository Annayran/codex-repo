# TGFCONT — Containers

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContainerCTe | Container CT-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IDCONTAINERS | ID Container | S |  |  | N |
| NUNOTA | Nº Nota | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ContainerCTe / [TGFCONT](TGFCONT.md) | NUNOTA->NUNOTA | I |
| ContainerCTe / [TGFCONT](TGFCONT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
