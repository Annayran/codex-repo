# TAPPAA — Paralisações por Apontamento

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParalisacaoPorApontamento | Paralisações por Apontamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPAR | Parceiro | I |  |  | N |
| HRFECHAMENTO | Hora Fechamento | I |  |  | N |
| HRFINAL | Hora Final | I |  |  | N |
| HRINICIAL | Hora Inicial | I |  |  | N |
| NUAPONTA | Nro Único Apontamento | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | ParalisacaoPorApontamento / [TAPPAA](TAPPAA.md) | NUAPONTA->NUAPONTA | I |
| ParalisacaoPorApontamento / [TAPPAA](TAPPAA.md) | MovimentoApontamento / [TAPMAP](TAPMAP.md) | NUAPONTA->NUAPONTA | I |
| ParalisacaoPorApontamento / [TAPPAA](TAPPAA.md) | TipoParalisacao / [TAPTPA](TAPTPA.md) | CODPAR->CODPAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
