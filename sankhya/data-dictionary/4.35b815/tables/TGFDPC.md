# TGFDPC — Dimensão de peças

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DimensaoPecas | Dimensão das peças | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANGULO | Ângulo de dobra | F |  |  | N |
| CODFACE | Face | I |  |  | N |
| CODPECA | Código | I |  |  | N |
| LARGURA | Largura da face | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegistroPecas / [TGFRPC](TGFRPC.md) | DimensaoPecas / [TGFDPC](TGFDPC.md) | CODPECA->CODPECA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
