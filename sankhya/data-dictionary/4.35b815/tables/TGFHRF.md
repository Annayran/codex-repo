# TGFHRF — HistoricoRessuprimentoFiliais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoRessuprimentoFiliais | Historico Ressuprimento de Filiais | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHFIMEXEC | Dh. Fim Execução | H |  |  | N |
| DHINIEXEC | Dh. Ini. Execução | H |  |  | N |
| ERRO | Erro | S |  |  | N |
| NUAGENDAMENTO | Nro. Agendamento | I |  |  | N |
| NUHISTORICO | Nro Execução | I |  |  | N |
| QTDDOCSENTRADA | Qtd. Docs. Entrada | I |  |  | N |
| QTDDOCSSAIDA | Qtd. Docs. Saída | I |  |  | N |
| QTDPRODATEND | Qtd. Produtos Atendidos | I |  |  | N |
| QTDPRODNAOATEND | Qtd. Produtos Não Atendidos | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TIPOAGE | TIPOAGE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalhesPedidoRessuprimento / [TGFHPRF](TGFHPRF.md) | HistoricoRessuprimentoFiliais / [TGFHRF](TGFHRF.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| DetalhesProdutoRessuprimento / [TGFHDRF](TGFHDRF.md) | HistoricoRessuprimentoFiliais / [TGFHRF](TGFHRF.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| HistoricoRessuprimentoFiliais / [TGFHRF](TGFHRF.md) | AgendamentoTarefa / [TGFTAG](TGFTAG.md) | NUAGENDAMENTO->NUAGENDAMENTO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
