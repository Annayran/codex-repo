# TCSFAP — Projeto Serviço (FAP)

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 22
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProjetoServico | Projeto Serviço | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODCOORD | Coordenador | I |  |  | N |
| CODMETOD | Modelo | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| COR | Cor das etapas (no gráfico) | S |  |  | N |
| IMPLANTADOR | Implantador | S |  |  | N |
| LIMITAPROD | Limitar Produtos | S |  |  | N |
| NUFAP | Número do Projeto | I |  |  | N |
| NUNOTA | Número da Nota | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| QTDHORAS | Quantidade de Horas | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Agendamento / [TCSAGE](TCSAGE.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| HistoricoAlteracaoFAP / [TCSFHA](TCSFHA.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| InsumoEtapa / [TCSFPE](TCSFPE.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| LinhaBaseProjeto / [TCSLBP](TCSLBP.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| ProdutoProjeto / [TCSFPR](TCSFPR.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | EquipeProjeto / [TCSFEQ](TCSFEQ.md) | NUFAP->NUFAP | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | HistoricoAlteracaoFAP / [TCSFHA](TCSFHA.md) | NUFAP->NUFAP | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | HistoricoStatusProjeto / [TCSFHS](TCSFHS.md) | NUFAP->NUFAP | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | ModeloProjeto / [TCSMET](TCSMET.md) | CODMETOD->CODMETOD | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | PedidoProjeto / [TCSFPD](TCSFPD.md) | NUFAP->NUFAP | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | ProdutoProjeto / [TCSFPR](TCSFPR.md) | NUFAP->NUFAP | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | RequisitoFap / [TCSREQ](TCSREQ.md) | NUFAP->NUFAP | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODCOORD->CODUSU | I |
| RequisitoFap / [TCSREQ](TCSREQ.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
