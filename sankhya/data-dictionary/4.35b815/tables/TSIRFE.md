# TSIRFE — Relatórios

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 15
- Instâncias DWF: 13
- Relações DWF: 26
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ModeloEtiqueta | Modelo de Etiqueta | S | N |
| ModeloPixCobranca | Modelo de Cobrança | N | N |
| Relatorio | Relatório | N | N |
| RelatorioCartaCorrecao | Relatório de carta de correção | N | N |
| RelatorioCartaCorrecaoCte | Relatório formatado do DANFE | N | N |
| RelatorioDanfeSimplificado | Modelo do DANFE simplificado | N | N |
| RelatorioDependente | Relatório Dependente | S | N |
| RelatorioEtiquetaPeso | Relatorio Etiqueta de Peso | S | N |
| RelatorioEtiquetaSeparacao | Relatorio Etiqueta de Separacao | S | N |
| RelatorioEtiquetaVolume | Relatorio Etiqueta de Volume | S | N |
| RelatorioExtratoPonto | Relatório | S | N |
| RelatorioMinutaDespacho | Minuta de Despacho | S | N |
| RelatorioModBoleto | RelatorioModBoleto | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CATEGORIA | Categoria | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONFIG | Configuração | C |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Data/Hora alteração | H |  |  | N |
| DSNAME | Fonte de Dados | S |  |  | N |
| EXPFILTROINSTANCIA | Mostrar na Tela/Instância quando | S |  |  | N |
| FORMATO | Formato de arquivo | S |  |  | N |
| IDTELA | IDTELA | S |  |  | N |
| NOMEIMPRESSORA | Impressora | S |  |  | N |
| NUINSTANCIA | Instância | I |  |  | N |
| NURFE | Número | I |  |  | N |
| NURFEDEPENDENTE | Nro relatório dependente | I |  |  | N |
| PREFIXOANEXO | Nome do anexo para e-mail | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendamentoRelatorioFormatado / [TSIARF](TSIARF.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| CentroResultadoPorNegociacao / [TCSCXT](TCSCXT.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| CentroResultadoPorNegociacao / [TCSCXT](TCSCXT.md) | RelatorioDependente / [TSIRFE](TSIRFE.md) | NURFEFR->NURFE | I |
| ConfiguracaoConferencia / [TGFCCO](TGFCCO.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFEETIQ->NURFE | I |
| ConfiguracaoConferencia / [TGFCCO](TGFCCO.md) | RelatorioEtiquetaPeso / [TSIRFE](TSIRFE.md) | NUETIQPESO->NURFE | I |
| ContaBancaria / [TSICTA](TSICTA.md) | ModeloPixCobranca / [TSIRFE](TSIRFE.md) | NURFEMODBOLETO->NURFE | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFEMODCHEQG->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEtiqueta / [TSIRFE](TSIRFE.md) | NURFEPRODUCAO->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RelatorioCartaCorrecao / [TSIRFE](TSIRFE.md) | NURFECARTACORR->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RelatorioCartaCorrecaoCte / [TSIRFE](TSIRFE.md) | NURFECARTACORRCTE->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RelatorioDanfeSimplificado / [TSIRFE](TSIRFE.md) | CODMODDANFESIMPLIFNFE->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RelatorioEtiquetaVolume / [TSIRFE](TSIRFE.md) | MODETQVOL->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RelatorioMinutaDespacho / [TSIRFE](TSIRFE.md) | CODRELMINUTAODP->NURFE | I |
| ItensPacote / [TSSITP](TSSITP.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| ModeloNotaFiscal / [TGFMON](TGFMON.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| ModeloNotaFiscalEstoque / [TGFMON](TGFMON.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| Produto / [TGFPRO](TGFPRO.md) | ModeloEtiqueta / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| Produto / [TGFPRO](TGFPRO.md) | RelatorioEtiquetaSeparacao / [TSIRFE](TSIRFE.md) | MODETIQSEPWMS->NURFE | I |
| Relatorio / [TSIRFE](TSIRFE.md) | ArquivoRelatorio / [TSIRFA](TSIRFA.md) | NURFE->NURFE | I |
| Relatorio / [TSIRFE](TSIRFE.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTANCIA->NUINSTANCIA | I |
| Relatorio / [TSIRFE](TSIRFE.md) | RelatorioDependente / [TSIRFE](TSIRFE.md) | NURFEDEPENDENTE->NURFE | I |
| Relatorio / [TSIRFE](TSIRFE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RelatorioFormatadoAtividade / [TWFRFA](TWFRFA.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| Verbas / [TGFGEV](TGFGEV.md) | Relatorio / [TSIRFE](TSIRFE.md) | CODMODVERB->NURFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
