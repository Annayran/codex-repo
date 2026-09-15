# TGFTPV — Tipos de Negociação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 56
- Instâncias DWF: 6
- Relações DWF: 37
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipNegTabPreco | Tipo Negociação vinc. Tabela de Preço | N | N |
| TipoNegociacao | Tipo de Negociação | S | N |
| TipoNegociacao3 | TipoNegociacao3 | S | N |
| TipoNegociacaoArm | Tipo Negociação Armazenagem | S | N |
| TipoNegociacaoExp | Tipo Negociação Armazenagem | S | N |
| TipoNegociacaoFilha | Tipo de Negociação de Saída | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APRESTRANSP | Apresenta transportadora | S |  |  | N |
| ARREDPRIMEIRAPARC | Ajusta arredondamento na 1ª parcela | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| BAIXA | Baixa | S |  |  | N |
| BASEPRAZO | Base do prazo | I |  |  | N |
| CODCTACTB_1 | Conta contábil 1 | I |  |  | N |
| CODCTACTB_2 | Conta contábil 2 | I |  |  | N |
| CODFORMDESCMAX | Fórmula desc. máximo | I |  |  | N |
| CODFORMDESCMAXITENS | Fórmula desc. máximo ítens | I |  |  | N |
| CODOBSPADRAO | Observação Padrão | I |  |  | N |
| CODTAB | Tabela de preço | I |  |  | N |
| CODTCF | Tabela financeira | I |  |  | N |
| CODTIPVENDA | Tipo de Negociação | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| COMISSAO | Comissão | F |  |  | N |
| COMPRAMAX | Valor máximo p/ compra | F |  |  | N |
| DESCMAX | % Desconto máximo | F |  |  | N |
| DESCPROM | Desconto Promocional | S |  |  | N |
| DESCRTIPVENDA | Descrição | S |  |  | N |
| DHALTER | Data e hora alteração | H |  |  | N |
| EDITASIMULACAO | Permite edição simulação pgto.? | S |  |  | N |
| EMITEBOLETA | Imprimir Pix/Boleto/Duplicata? | S |  |  | N |
| EMITEDUPL | Emite duplicata | S |  |  | N |
| EXVENDAMAIS | Exclusivo Venda Mais | S |  |  | N |
| FASTUSA | Utiliza no Fast Service? | S |  |  | N |
| FIXAVENC | Fixa vencimento | S |  |  | N |
| FLEX | Usar Créd. Flex | S |  |  | N |
| FORMARECBTOSOCIN | Forma Recebto. | I |  |  | N |
| GRUPOAUTOR | Grupo de autorização | S |  |  | N |
| INTEGRAECONECT | Integrar com Econect | S |  |  | N |
| LUCROMIN | % Lucro mínimo | F |  |  | N |
| MARGEMMIN | % MC Mínima | F |  |  | N |
| MODELOPGTO | Disponível para simulação? | S |  |  | N |
| NROPARCELAS | Número de parcelas | I |  |  | N |
| NUNOTA | Cabeçalho modelo | I |  |  | N |
| PERCMINENTRADA | % Mín. entrada | F |  |  | N |
| PODECONSUMIDOR | Pode ser usado por consumidor | S |  |  | N |
| POSSUISIMSALVA | Possui Simulação Salva | S |  |  | S |
| PRAZOMAX | Prazo máximo | I |  |  | N |
| PRAZOMAXPRIPARC | Prazo máx. 1ª parcela | I |  |  | N |
| PRAZOMEDMAX | Prazo médio máximo | I |  |  | N |
| PRAZOMIN | Prazo mínimo | I |  |  | N |
| SOMAPRAZOCLIENTE | Soma prazo de cliente | S |  |  | N |
| SUBTIPOVENDA | Subtipo | S |  |  | N |
| TAXAJURO | Taxa em % | F |  |  | N |
| TAXAJURSIM | Taxa de juros em simulações | F |  |  | N |
| TIMQTDPARC | Qtd. Parcelas Lançamento | I |  |  | N |
| TIPJURO | Apresentação da taxa | S |  |  | N |
| TIPOJURSIM | Tipo de juros em simulações | S |  |  | N |
| TIPTAXA | Tipo da taxa | S |  |  | N |
| TRUNCPARCELA | Truncar valores no parcelamento | S |  |  | N |
| TXPARCADM | Taxa mensal de Administradora | F |  |  | N |
| VALPRAZOCLIENTE | Valida prazo de cliente | S |  |  | N |
| VENCPREFIXPED | Vencimento pré-fixado no pedido | S |  |  | N |
| VENDAMAX | Valor máximo p/ venda | F |  |  | N |
| VENDAMIN | Valor mínimo p/ venda | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | SUGTIPNEGENTR->CODTIPVENDA | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | TipoNegociacaoFilha / [TGFTPV](TGFTPV.md) | SUGTIPNEGSAID->CODTIPVENDA | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |
| Contrato / [TCSCON](TCSCON.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoNegociacaoArm / [TGFTPV](TGFTPV.md) | CODTIPVENDAAR->CODTIPVENDA | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoNegociacaoExp / [TGFTPV](TGFTPV.md) | CODTIPVENDAEX->CODTIPVENDA | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ItemRegras / [TGFITR](TGFITR.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODINSTPRINC->CODTIPVENDA | I |
| ItemRegras / [TGFITR](TGFITR.md) | TipoNegociacao3 / [TGFTPV](TGFTPV.md) | CODINSTSECFIN->CODTIPVENDA | I |
| ItemRegras / [TGFITR](TGFITR.md) | TipoNegociacaoFilha / [TGFTPV](TGFTPV.md) | CODINSTSECINI->CODTIPVENDA | I |
| Materiais / [TCAMAT](TCAMAT.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| RestricaoPreco / [TFXRTP](TFXRTP.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTPV->CODTIPVENDA; DHALTERTPV->DHALTER | I |
| TaxasJurosDescontos / [TGFTAXG](TGFTAXG.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | BasesVencimento / [TGFBPV](TGFBPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | ControleFinanceira / [TGFTCF](TGFTCF.md) | CODTCF->CODTCF | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | FormulaCalcDescMax / [TGFFDM](TGFFDM.md) | CODFORMDESCMAX->CODFORM | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | FormulaCalcDescMaxItens / [TGFFDM](TGFFDM.md) | CODFORMDESCMAXITENS->CODFORM | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | ObservacaoNotasFiscais / [TGFOBS](TGFOBS.md) | CODOBSPADRAO->CODOBSPADRAO | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | ParcelaPagamento / [TGFPPG](TGFPPG.md) | CODTIPVENDA->CODTIPVENDA | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | PlanoContaConta /  | CODCTACTB_1->CODCTACTB | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | PLanoContaConta2 /  | CODCTACTB_2->CODCTACTB | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | TaxasEspeciais / [TGFTAX](TGFTAX.md) | CODTIPVENDA->CODTIPVENDA | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | TaxasJurosDescontos / [TGFTAXG](TGFTAXG.md) | CODTIPVENDA->CODTIPVENDA | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | VendedorVenda / [TGFNPV](TGFNPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| VendaPendente / [TGFVCP](TGFVCP.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |
| VendedoresTabPreco / [TGFNPV](TGFNPV.md) | TipNegTabPreco / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
