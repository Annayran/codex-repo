# TAPMAP — Movimento Apontamento

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MovimentoApontamento | MovimentoApontamento | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROJ | Projeto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONDCLIMATICAMANHA | Condição Clima Manhã | S |  |  | N |
| CONDCLIMATICATARDE | Condição Clima Tarde | S |  |  | N |
| CONTEUDORDO | Conteúdo RDO | B |  |  | N |
| DHMOV | Data Mov. | H |  |  | N |
| HRFINAL | Hora Final | I |  |  | N |
| HRINICIAL | Hora inicial | I |  |  | N |
| INTERVALO | Intervalo | I |  |  | N |
| NUAPONTA | Nro Único Apontamento | I |  |  | N |
| NUMRDO | Número RDO | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemApontamento / [TAPIAP](TAPIAP.md) | MovimentoApontamento / [TAPMAP](TAPMAP.md) | NUAPONTA->NUAPONTA | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | ControleVeiculo / [TGFCVE](TGFCVE.md) | NUAPONTA->NUAPONTA | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUAPONTA->NUAPONTA | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | ItemApontamento / [TAPIAP](TAPIAP.md) | NUAPONTA->NUAPONTA | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | ParalisacaoPorApontamento / [TAPPAA](TAPPAA.md) | NUAPONTA->NUAPONTA | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ParalisacaoPorApontamento / [TAPPAA](TAPPAA.md) | MovimentoApontamento / [TAPMAP](TAPMAP.md) | NUAPONTA->NUAPONTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
