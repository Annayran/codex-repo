# TCSNHC — TCSNHC

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NegociacaoHorasCruzadas | Negociação Horas Cruzadas | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| CODUNNCOMPRADORA | Un. Compradora | I |  |  | N |
| CODUNNVENDEDORA | Un. Vendedora | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DTALTER | Dt. Alteração | H |  |  | N |
| DTFIN | Dt. Final | D |  |  | N |
| DTINI | Dt. Inicial | D |  |  | N |
| MULTHORAEXTRA | Multip. Hora Extra | F |  |  | N |
| NUFAP | Nro. Projeto | I |  |  | N |
| NUNEGOCIACAO | Código | I |  |  | N |
| NUNOTA | Pedido Avulso | I |  |  | N |
| VLRHORA | Vlr. Hora | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemOrdemServico / [TCSITE](TCSITE.md) | NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | NUNEGOCIACAO->NUNEGOCIACAO | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | ExecutantesHorasCruzadas / [TCSEXN](TCSEXN.md) | NUNEGOCIACAO->NUNEGOCIACAO | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | PedidoOrigem / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | UnidadeNegocio / [TGFUNN](TGFUNN.md) | CODUNNVENDEDORA->CODUNN | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | UnidadeNegocioCompradora / [TGFUNN](TGFUNN.md) | CODUNNCOMPRADORA->CODUNN | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
