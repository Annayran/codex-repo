# TGFHPRF — DetalhesPedidoRessuprimento

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalhesPedidoRessuprimento | Detalhes Pedido Ressuprimento | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMPDEST | Cód. Empresa Destino | I |  |  | N |
| CODEMPORIG | Cód. Empresa Origem | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| MOTIVOERRO | TIPOAGE | S |  |  | N |
| NUAGENDAMENTO | Nro. Agendamento | I |  |  | N |
| NUDETALHE | NUDETALHE | I |  |  | N |
| NUHISTORICO | NUHISTORICO | I |  |  | N |
| NUNOTA | Nro. Único do Pedido | I |  |  | N |
| QTDDISPONIVEL | Qtd. Disponível | F |  |  | N |
| QTDNECESSARIA | Qtd. Necessária | F |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TIPOAGE | TIPOAGE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalhesPedidoRessuprimento / [TGFHPRF](TGFHPRF.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DetalhesPedidoRessuprimento / [TGFHPRF](TGFHPRF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPORIG->CODEMP | I |
| DetalhesPedidoRessuprimento / [TGFHPRF](TGFHPRF.md) | HistoricoRessuprimentoFiliais / [TGFHRF](TGFHRF.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| DetalhesPedidoRessuprimento / [TGFHPRF](TGFHPRF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODEMPDEST->CODPARC | I |
| DetalhesPedidoRessuprimento / [TGFHPRF](TGFHPRF.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
