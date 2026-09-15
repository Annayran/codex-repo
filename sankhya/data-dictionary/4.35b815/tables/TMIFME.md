# TMIFME — Faixas de Farol

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FaixaFarol | Faixa de Farol | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| EXPFAROL | Expressão do Farol | C |  |  | N |
| FAROL | Farol | I |  |  | N |
| NUMET | Nro. Meta | I |  |  | N |
| ORDEMAVAL | Ordem de Avaliação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FaixaFarol / [TMIFME](TMIFME.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | FaixaFarol / [TMIFME](TMIFME.md) | NUMET->NUMET | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
