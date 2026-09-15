# TMIQUEL — TABLE TMIQUEL

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| QuebraMetaLigacoes | Ligacoes da quebra | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODQUEBRA | Código do item da quebra | I |  |  | N |
| NUMETBASE | Número do item da meta | I |  |  | N |
| NUMETQUEBRA | Número do item da quebra | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | QuebraMetaLigacoes / [TMIQUEL](TMIQUEL.md) | NUMET->NUMETBASE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
