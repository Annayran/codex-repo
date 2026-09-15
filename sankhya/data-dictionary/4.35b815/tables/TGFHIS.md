# TGFHIS — TABLE TGFHIS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoReajusteContrato | Histórico de Reajuste de Contratos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHFIMEXEC | Dh. Fim Execução | H |  |  | N |
| DHINIEXEC | Dh. Ini. Execução | H |  |  | N |
| NUAGENDAMENTO | NUAGENDAMENTO | I |  |  | N |
| NUHISTORICO | Nro. Único Agend. | I |  |  | N |
| QTDCONTRATOS | Qtd. Contratos | I |  |  | N |
| QTDNAOREAJUSTADOS | Qtd. Não Reajustados | I |  |  | N |
| QTDPRODUTOS | Qtd. Produtos | I |  |  | N |
| QTDREAJUSTADOS | Qtd. Reajustados | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TIPOAGE | TIPOAGE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheReajusteContrato / [TGFDPR](TGFDPR.md) | HistoricoReajusteContrato / [TGFHIS](TGFHIS.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| HistoricoReajusteContrato / [TGFHIS](TGFHIS.md) | DetalheReajusteContrato / [TGFDPR](TGFDPR.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
