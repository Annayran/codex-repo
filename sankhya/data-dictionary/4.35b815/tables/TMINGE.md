# TMINGE — Níveis Gerenciais

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NivelGerencial | Nível Gerencial | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNGE | Cód. Nível Gerencial | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| NIVEL | Nível | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| UnidadeGerencial / [TMIUNG](TMIUNG.md) | NivelGerencial / [TMINGE](TMINGE.md) | CODNGE->CODNGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
