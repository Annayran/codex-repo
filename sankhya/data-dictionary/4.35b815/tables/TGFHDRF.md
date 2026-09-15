# TGFHDRF — DetalhesProdutoRessuprimento

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalhesProdutoRessuprimento | DetalhesProdutoRessuprimento | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMPDEST | Cód. Empresa Destino | I |  |  | N |
| CODEMPORIG | Cód. Empresa Origem | I |  |  | N |
| NUAGENDAMENTO | NUAGENDAMENTO | I |  |  | N |
| NUDETALHE | NUDETALHE | I |  |  | N |
| NUHISTORICO | NUHISTORICO | I |  |  | N |
| NUNOTA | Nro. Único do Pedido | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TIPOAGE | TIPOAGE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalhesProdutoRessuprimento / [TGFHDRF](TGFHDRF.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DetalhesProdutoRessuprimento / [TGFHDRF](TGFHDRF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPORIG->CODEMP | I |
| DetalhesProdutoRessuprimento / [TGFHDRF](TGFHDRF.md) | HistoricoRessuprimentoFiliais / [TGFHRF](TGFHRF.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| DetalhesProdutoRessuprimento / [TGFHDRF](TGFHDRF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODEMPDEST->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
