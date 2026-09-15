# TGFVEN — Vendedores Representantes

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 37
- Instâncias DWF: 11
- Relações DWF: 63
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Assessor | Assessor | S | N |
| ExecutanteItem | Executante do item da nota | N | N |
| Gerente | Gerente | S | N |
| Vendedor | Vendedor | S | S |
| Vendedor2 | Vendedor | N | N |
| VendedorCentroResultado | Vendedor do centro de resultado | N | N |
| VendedorExecutante | Executante | S | N |
| VendedorItem | Vendedor do item da nota | S | N |
| VendedorLancamento | Vendedor no lançamento de comissão | N | N |
| VendedorRepTabPreco | Vendedor vinc. Tabela de Preço | N | N |
| VendParceiro | Vendedor Parceiro | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACRESCMAX | Acréscimo máximo | F |  |  | N |
| APELIDO | Apelido | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| ATUACOMPRADOR | Atua também como comprador | S |  |  | N |
| CODCARGAHOR | Carga Horária | I |  |  | N |
| CODCENCUSPAD | Centro Resultado Padrão | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODFORM | Fórmula Comissão | I |  |  | N |
| CODFUNC | Funcionário | I |  |  | N |
| CODGER | Gerente | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODREG | Região | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEND | Código | I |  |  | N |
| COMCM | Recebe comissão em CM | S |  |  | N |
| COMGER | Comissão gerência | F |  |  | N |
| COMVENDA | Comissão venda | F |  |  | N |
| DESCMAX | Desconto máximo | F |  |  | N |
| DIACOM | Dia inicial p/período de comissão | I |  |  | N |
| DTALTER | Data de alteração | H |  |  | N |
| EMAIL | Email | S |  |  | N |
| GRUPODESCVEND | Grupo Desc. Vendedor | S |  |  | N |
| GRUPORETENCAO | Grupo de retenção | S |  |  | N |
| PARTICMETA | Participação na meta | F |  |  | N |
| PERCCUSVAR | % Custo Variável | F |  |  | N |
| PROVACRESC | Provisão acréscimo | F |  |  | N |
| PROVACRESCCAC | Provisão do carrinho | F |  |  | N |
| RECHREXTRA | Recebe Hora Dobrada | S |  |  | N |
| SALDODISP | Saldo Disponível | F |  |  | N |
| SALDODISPCAC | Saldo Diponivel do Carrinho | F |  |  | N |
| SENHA | Senha | I |  |  | N |
| TIPCALC | Pagamento de Comissão por data de | S |  |  | N |
| TIPFECHCOM | Tipo de fechamento de comissão | S |  |  | N |
| TIPOCERTIF | Tipo p/ certificação | S |  |  | S |
| TIPVALOR | Usar valor p/ comissão de OS | S |  |  | N |
| TIPVEND | Tipo | S |  |  | N |
| VLRHORA | Valor hora p/ comissão de O.S. | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AdiantamentoSaldoFlex / [TGFASA](TGFASA.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| AgendaFixa / [TCSAGF](TCSAGF.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Gerente / [TGFVEN](TGFVEN.md) | CODGER->CODVEND | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| CabecalhoAgenda / [TGFCAS](TGFCAS.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODEXEC->CODVEND | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | L |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Vendedor2 / [TGFVEN](TGFVEN.md) | CODVENDTEC->CODVEND | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Vendedor2 / [TGFVEN](TGFVEN.md) | CODVENDTEC->CODVEND | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| CheckoutItem / [TGFCKI](TGFCKI.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ComissaoMultipla / [TGFCCM](TGFCCM.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ComissaoNegociacao / [TCSCNV](TCSCNV.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| Desconto / [TGFDES](TGFDES.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ExtratoVendedor / [TGFEXT](TGFEXT.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Gerente / [TGFVEN](TGFVEN.md) | CODGER->CODVEND | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| IndiceEfetividade / [TCSIE](TCSIE.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ItemNota / [TGFITE](TGFITE.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ItemNota / [TGFITE](TGFITE.md) | Vendedor2 / [TGFVEN](TGFVEN.md) | CODEXEC->CODVEND | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| MetaAtual / [TGMMET](TGMMET.md) | Gerente / [TGFVEN](TGFVEN.md) | CODGER->CODVEND | I |
| MetaAtual / [TGMMET](TGMMET.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Assessor / [TGFVEN](TGFVEN.md) | CODASSESSOR->CODVEND | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| Regiao / [TSIREG](TSIREG.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Gerente / [TGFVEN](TGFVEN.md) | CODGER->CODVEND | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| Usuario / [TSIUSU](TSIUSU.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ValorComissaoVendedor / [TGFCOM](TGFCOM.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| Vendedor / [TGFVEN](TGFVEN.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUSPAD->CODCENCUS | I |
| Vendedor / [TGFVEN](TGFVEN.md) | CertificacaoRegra / [TGFCER](TGFCER.md) | CODVEND->CHAVE; TIPOCERTIF->TIPO | I |
| Vendedor / [TGFVEN](TGFVEN.md) | ComissaoVendedorGrupo / [TGFCVG](TGFCVG.md) | CODVEND->CODVEND | I |
| Vendedor / [TGFVEN](TGFVEN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Vendedor / [TGFVEN](TGFVEN.md) | FormulaComissaoPremiacao / [TGFFCV](TGFFCV.md) | CODVEND->CODVEND | I |
| Vendedor / [TGFVEN](TGFVEN.md) | FormulasComissao / [TGFFOC](TGFFOC.md) | CODFORM->CODFORM | I |
| Vendedor / [TGFVEN](TGFVEN.md) | Funcionario /  | CODEMP->CODEMP; CODFUNC->CODFUNC | I |
| Vendedor / [TGFVEN](TGFVEN.md) | Gerente / [TGFVEN](TGFVEN.md) | CODGER->CODVEND | I |
| Vendedor / [TGFVEN](TGFVEN.md) | IdentificacaoCargaHoraria /  | CODCARGAHOR->CODCARGAHOR | I |
| Vendedor / [TGFVEN](TGFVEN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Vendedor / [TGFVEN](TGFVEN.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Vendedor / [TGFVEN](TGFVEN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODPARC->CODPARC | I |
| Vendedor / [TGFVEN](TGFVEN.md) | VendedorGrupoProduto / [TGFRGV](TGFRGV.md) | CODVEND->CODVEND | I |
| Vendedor / [TGFVEN](TGFVEN.md) | VendedorParceiro / [TGFRPV](TGFRPV.md) | CODVEND->CODVEND | I |
| VendedoresTabPreco / [TGFNPV](TGFNPV.md) | VendedorRepTabPreco / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| VendedorProdutoContrato / [TCSVPC](TCSVPC.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| VendedorVenda / [TGFNPV](TGFNPV.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | Assessor / [TGFVEN](TGFVEN.md) | CODASSESSOR->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | ExecutanteItem / [TGFVEN](TGFVEN.md) | CODEXEC->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | VendedorCentroResultado / [TGFVEN](TGFVEN.md) | CODVENDCR->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | VendedorItem / [TGFVEN](TGFVEN.md) | CODVENDITE->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | VendedorLancamento / [TGFVEN](TGFVEN.md) | CODVENDLAN->CODVEND | L |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | VendParceiro / [TGFVEN](TGFVEN.md) | CODVENDPARC->CODVEND | L |
| ViewComissaoFechamento / [VGFCOM_FECH](VGFCOM_FECH.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
