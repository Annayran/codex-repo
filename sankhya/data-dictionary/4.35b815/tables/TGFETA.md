# TGFETA — Etapas de Produção

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 28
- Instâncias DWF: 2
- Relações DWF: 18
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EtapaProducao | Etapas de Produção | S | N |
| EtapaProducaoSelecaoMP | Etapa de Produção para Seleção de MP's | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AJUSTARMPS | Ajustar MPs da Produção com Local.Ent.Destino | S |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODEMPDEST | Empresa Destino | I |  |  | N |
| CODEMPORIG | Empresa Origem | I |  |  | N |
| CODETAPA | Cód. Etapa | I |  |  | N |
| CODETAPAMP | Etapa p/Seleção de MPs | I |  |  | N |
| CODLOCALENTDEST | Local Entrada Destino | I |  |  | N |
| CODLOCALENTORIG | Local Entrada Origem | I |  |  | N |
| CODLOCALSAIDEST | Local Saída Destino | I |  |  | N |
| CODLOCALSAIORIG | Local Saída Origem | I |  |  | N |
| GERARAMOSTRA | Gerar Amostra | S |  |  | N |
| LOCALORIGENT | Origem Parceiro Entrada | S |  |  | N |
| LOCALORIGSAI | Origem Parceiro Saída | S |  |  | N |
| NOMEETAPA | Nome Etapa | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PENDENTEENT | A nota fica pendente de confirmação | S |  |  | N |
| PENDENTESAI | A nota fica pendente de confirmação | S |  |  | N |
| SEQOTICA | Sequência Ótica | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TOPDEVMP | TOP para Devolução de Matéria Prima | I |  |  | N |
| TOPENTRADA | TOP de Entrada | I |  |  | N |
| TOPPRODUCAO | TOP de Produção | I |  |  | N |
| TOPSAIDA | TOP de Saída | I |  |  | N |
| USALOCENTDESTPA | Usar Local Entrada Destino da Produção | S |  |  | N |
| USALOCENTORIGPA | Usar Local Entrada Origem da Produção | S |  |  | N |
| USALOCSAIDESTPA | Usar Local Saída Destino da Produção | S |  |  | N |
| USALOCSAIORIGPA | Usar Local Saída Origem da Produção | S |  |  | N |
| VALIDAWMS | Verifica pendência no WMS na Saída da Etapa | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | EtapaProducao / [TGFETA](TGFETA.md) | CODETAPA->CODETAPA | I |
| EtapaProducao / [TGFETA](TGFETA.md) | AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | CODETAPA->CODETAPA | I |
| EtapaProducao / [TGFETA](TGFETA.md) | CentroResultadoDespesa / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| EtapaProducao / [TGFETA](TGFETA.md) | EmpresaDestinoEtapaProducao / [TSIEMP](TSIEMP.md) | CODEMPDEST->CODEMP | I |
| EtapaProducao / [TGFETA](TGFETA.md) | EmpresaOrigemEtapaProducao / [TSIEMP](TSIEMP.md) | CODEMPORIG->CODEMP | I |
| EtapaProducao / [TGFETA](TGFETA.md) | EtapaProducaoSelecaoMP / [TGFETA](TGFETA.md) | CODETAPAMP->CODETAPA | I |
| EtapaProducao / [TGFETA](TGFETA.md) | EtapaProduto / [TGFEPR](TGFEPR.md) | CODETAPA->CODETAPA | I |
| EtapaProducao / [TGFETA](TGFETA.md) | LocalFinanceiroEntDest / [TGFLOC](TGFLOC.md) | CODLOCALENTDEST->CODLOCAL | I |
| EtapaProducao / [TGFETA](TGFETA.md) | LocalFinanceiroEntOrig / [TGFLOC](TGFLOC.md) | CODLOCALENTORIG->CODLOCAL | I |
| EtapaProducao / [TGFETA](TGFETA.md) | LocalFinanceiroSaiDest / [TGFLOC](TGFLOC.md) | CODLOCALSAIDEST->CODLOCAL | I |
| EtapaProducao / [TGFETA](TGFETA.md) | LocalFinanceiroSaiOrig / [TGFLOC](TGFLOC.md) | CODLOCALSAIORIG->CODLOCAL | I |
| EtapaProducao / [TGFETA](TGFETA.md) | StatusPedido / [TGFSTA](TGFSTA.md) | CODETAPA->CODETAPA | I |
| EtapaProducao / [TGFETA](TGFETA.md) | TopEntrada / [TGFTOP](TGFTOP.md) | TOPENTRADA->CODTIPOPER | I |
| EtapaProducao / [TGFETA](TGFETA.md) | TopProducao / [TGFTOP](TGFTOP.md) | TOPPRODUCAO->CODTIPOPER | I |
| EtapaProducao / [TGFETA](TGFETA.md) | TopSaida / [TGFTOP](TGFTOP.md) | TOPSAIDA->CODTIPOPER | I |
| EtapaProduto / [TGFEPR](TGFEPR.md) | EtapaProducao / [TGFETA](TGFETA.md) | CODETAPA->CODETAPA | I |
| StatusPedido / [TGFSTA](TGFSTA.md) | EtapaProducao / [TGFETA](TGFETA.md) | CODETAPA->CODETAPA | I |
| Usuario / [TSIUSU](TSIUSU.md) | EtapaProducao / [TGFETA](TGFETA.md) | CODETAPA->CODETAPA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
