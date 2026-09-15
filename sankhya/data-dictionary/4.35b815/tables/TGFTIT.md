# TGFTIT — Tipos de Título

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 70
- Instâncias DWF: 5
- Relações DWF: 33
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EspecieTitulo | Espécie do Tipo de Título | S | N |
| TipoTitulo | Tipo de Título | S | S |
| TipoTituloArm | Tipos de Título Armazenagem | S | N |
| TipoTituloExp | Tipo de Título | S | S |
| TipoTituloIpi | Tipo de Título | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AJUSTAVP | Ajusta Valor Presente | S |  |  | N |
| ALTERASIMULTPV | Permite alteração no tipo de negociação | S |  |  | N |
| ARREDPRIMEIRAPARC | Ajusta arredondamento na 1ª parcela | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| BAIXACERTO | Pode Baixar? | S |  |  | N |
| CARENCIA | Float(Carência) | I |  |  | N |
| CARTAODESC | Taxa Administradora | F |  |  | N |
| CARTAOTAXA | % Taxa Administradora | F |  |  | N |
| CODBANDEIRAECONECT | Bandeira Cartão | S |  |  | N |
| CODCTACTB | Conta Contábil 1 | I |  |  | N |
| CODCTACTB2 | Conta Contábil 2 | I |  |  | N |
| CODCTACTB3 | Conta Contábil 3 | I |  |  | N |
| CODDETRECGNRE | Código Detalhamento Receita (GNRE) | I |  |  | N |
| CODFINALIZADORA | Cód. Finalizadora | I |  |  | N |
| CODGRUPOTIPTIT | Grupo | I |  |  | N |
| CODMOEDA | Moeda | I |  |  | N |
| CODPARCTEF | Parc.Administradora | I |  |  | N |
| CODPRODGNRE | Código do Produto (GNRE) | I |  |  | N |
| CODRECGNRE | Código da Receita (GNRE) | I |  |  | N |
| CODTIPTIT | Tipo de Título | I |  |  | N |
| CONFERENCIA | Conferência | S |  |  | N |
| CONSDIASUTEIS | Considerar dias úteis | S |  |  | N |
| CONVENIOECONECT | Convênio | S |  |  | N |
| CREDENCIADORACFE | Instituição Credenciadora do Cartão | S |  |  | N |
| DESCRTIPTIT | Descrição | S |  |  | N |
| DESCRTPAGNFCE | Descrição do Tipo de Pagto NFC-e/NF-e/CF-e (Outros) | S |  |  | N |
| DIAVENC | Vencimento | I |  |  | N |
| ESPDOC | Espécie | S |  |  | N |
| EXIBBAIX | Mensagem p/exibir na tela de baixa | S |  |  | N |
| EXIGBAIXAACERTO | Valor Baixado deve ser igual ao Retornado? | S |  |  | N |
| EXPGRS | Exportação para GRS | I |  |  | N |
| EXPTES | Exportação para TES | S |  |  | N |
| FASTBAIXA | Baixa automática? | S |  |  | N |
| FASTUSA | Utiliza no Fast Service? | S |  |  | N |
| FISCAL | Forma Pagamento TEF | S |  |  | N |
| GRUPOFINALIZADORA | Grupo Finalizadora | I |  |  | N |
| GRUPOLIMCRED | Grupo p/Limite Crédito | S |  |  | N |
| IMAGEM | Imagem | B |  |  | N |
| IMPBOLRENEG | Imprimir Pix/Boleto/Duplicata p/os títulos renegociados? | S |  |  | N |
| IMPCOMPROVANTE | Imprime comprovante? | S |  |  | N |
| INDRECEFDCONT | Indicador de Receita (registro F525 - EFD Cont.) | S |  |  | N |
| INDTIT | Indicador do Tipo de Título | I |  |  | N |
| INFCMC7 | Informar CMC7? | S |  |  | N |
| INFCOMPLEFDCONT | Informações complem. (registro F525 - EFD Cont.) | S |  |  | N |
| INTEGRAECONECT | Integração EConect | S |  |  | N |
| NROPARCELAS | Nro. Parcelas | I |  |  | N |
| NROPARCELASMAX | Qtd de parcelas Máxima | I |  |  | N |
| NSUOPCIONALPOS | Informar NSU opcional | S |  |  | N |
| OPERACAOCTF | Operação CTF | S |  |  | N |
| PERCCUSVAR | % Custo variável | F |  |  | N |
| PERCJUROS | % Juros | F |  |  | N |
| PERCMULTA | % Multa | F |  |  | N |
| PRAZO | Prazo | I |  |  | N |
| PROIBIMPBOL | Proibir impressão boleto? | S |  |  | N |
| QTDPARCELCTF | Qtd. parcelas | I |  |  | N |
| RECEBANTAPROV | Receber antes de aprovar a nota | S |  |  | N |
| SUBTIPOVENDA | Subtipo | S |  |  | N |
| TIMUSADOLOCACAO | Utilizado na Locação | S |  |  | N |
| TIPDOCRURAL | Tipo Documento Rural | S |  |  | N |
| TIPVENC | Tipo vencimento | S |  |  | N |
| TPAGNFCE | Tipo de pgto para NFC-e / NF-e / CF-e | S |  |  | N |
| TRANSFBAIX | Pode transferir depois de baixado? | S |  |  | N |
| TRANSFDIF | Transferir a diferença entre o valor Retornado e o Baixado? | S |  |  | N |
| TRANSFPEND | Pode transferir título pendente? | S |  |  | N |
| TRUNCPARCELA | Truncar valores no parcelamento | S |  |  | N |
| ULTILIZAPDVWEB | Tip. título cartão principal PDV Web | S |  |  | N |
| UTILIZAPOS | Utiliza POS | S |  |  | N |
| VALIDAQTDMAXTITVENCIDOS | Validar Qtd. Máx. Títulos Vencidos | S |  |  | N |
| VLRCUSVAR | Valor Custo Variável | F |  |  | N |
| VLRPARCMINCART | Valor de parcela minima para cartões | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcertoCarga / [TGFACR](TGFACR.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| AgendadorRemessaBancaria / [TGFREM](TGFREM.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| Contrato / [TCSCON](TCSCON.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | TIPOTITULO->CODTIPTIT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | TIPOTITULO->CODTIPTIT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoTituloArm / [TGFTIT](TGFTIT.md) | TIPOTITULOAR->CODTIPTIT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoTituloExp / [TGFTIT](TGFTIT.md) | TIPOTITULOEX->CODTIPTIT | I |
| CreditoPorParceiro / [TGFLCP](TGFLCP.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | GRUPOLIMCRED->GRUPOLIMCRED | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | EFDCODTIPTIT->CODTIPTIT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoTituloIpi / [TGFTIT](TGFTIT.md) | CODTIPTITIPI->CODTIPTIT | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| FormatadorPorTipoTitulo / [TGFFTT](TGFFTT.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| FormulaParcelasIndependentes / [TGFFPI](TGFFPI.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTITPAD->CODTIPTIT | I |
| RestricaoTipoTitulo / [TGFRTT](TGFRTT.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTITDES->CODTIPTIT | I |
| TimBeneficiario / [TIMIRB](TIMIRB.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | IRBCODTIPTIT->CODTIPTIT | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | IMPCODTIPTIT->CODTIPTIT | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | TXCCODTIPTIT->CODTIPTIT | I |
| TipoTitulo / [TGFTIT](TGFTIT.md) | GruposTipoTitulo / [TGFGTT](TGFGTT.md) | CODGRUPOTIPTIT->CODGRUPOTIPTIT | I |
| TipoTitulo / [TGFTIT](TGFTIT.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| TipoTitulo / [TGFTIT](TGFTIT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCTEF->CODPARC | I |
| TipoTitulo / [TGFTIT](TGFTIT.md) | PlanoConta /  | CODCTACTB->CODCTACTB | I |
| TipoTitulo / [TGFTIT](TGFTIT.md) | PlanoConta2 /  | CODCTACTB2->CODCTACTB | I |
| TipoTitulo / [TGFTIT](TGFTIT.md) | PlanoContaConta3 /  | CODCTACTB3->CODCTACTB | I |
| TipoTitulo / [TGFTIT](TGFTIT.md) | RestricaoTipoTitulo / [TGFRTT](TGFRTT.md) | CODTIPTIT->CODTIPTITORI | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTITORIG->CODTIPTIT | I |
| UnidadeFederativa / [TSIUFS](TSIUFS.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | TIPTITGNREFCPST->CODTIPTIT | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
