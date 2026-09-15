# TCSSPN — Simulação preço Negociação

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SimulacaoPrecoNegociacao | Simulação preço Negociação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Dt. Ateração | H |  |  | N |
| NUMOS | Núm. da OS | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| SimulacaoPrecoNegociacao / [TCSSPN](TCSSPN.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| SimulacaoPrecoNegociacao / [TCSSPN](TCSSPN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
