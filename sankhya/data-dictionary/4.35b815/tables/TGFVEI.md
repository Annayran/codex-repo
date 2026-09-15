# TGFVEI — Veículos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 66
- Instâncias DWF: 5
- Relações DWF: 36
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Veiculo | Veículos | S | N |
| VeiculoReboque1 | Veiculo Reboque | S | N |
| VeiculoReboque2 | Veiculo Reboque2 | S | N |
| VeiculoReboque3 | Veiculo Reboque3 | S | N |
| VeiculoTracao | Veículo de Tração | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AFERICAO | Aferição | S |  |  | N |
| ANOFABRIC | Ano Fabricação | I |  |  | N |
| ANOMOD | Ano Modelo | I |  |  | N |
| ANTT | RNTRC | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CAPPOTCIL | Cap/Pot/Cil | S |  |  | N |
| CATEGORIA | Categoria | S |  |  | N |
| CHASSIS | Chassis | S |  |  | N |
| CMKG | Cap. Máx. de Tração | S |  |  | N |
| CODBEM | Código do bem | S |  |  | N |
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODCID | Cidade Emplacamento | I |  |  | N |
| CODEMBARCACAO | Cód. Embarcação | S |  |  | N |
| CODEMPFOLHA | Empresa | I |  |  | N |
| CODFORMFRETE | Fórmula p/cálculo de Frete | I |  |  | N |
| CODFUNC | Funcionário | I |  |  | N |
| CODMARCAMOD | Cód.Marca/Modelo | I |  |  | N |
| CODMOTORISTA | Motorista | I |  |  | N |
| CODPARC | Parceiro ou Empresa | I |  |  | N |
| CODPARCPROPANTT | Parceiro ANTT | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODQUEST | Questionário | I |  |  | N |
| CODVEICULO | Código do Veículo | I |  |  | N |
| COMBUSTIVEL | Combustível | S |  |  | N |
| CONDVEI | Condição do Veículo | I |  |  | N |
| CONDVIN | Condição do VIN | S |  |  | N |
| COR | Cor | S |  |  | N |
| CORDENATRAN | Código da Cor (Tabela DENATRAN) | I |  |  | N |
| CORFAB | Cor Fabricante | S |  |  | N |
| DESCR_EMP_PARC | Descrição do campo Parceiro ou Empresa | S |  |  | S |
| DISTEIXOS | Distância entre Eixos | S |  |  | N |
| EMITEEXPED | Emite Expedição | S |  |  | N |
| EMPPARC | Empresa ou Parceiro | S |  |  | N |
| ESPECIETIPO | Espécie e Tipo | S |  |  | N |
| ESPVEI | Espécie de Veículo | I |  |  | N |
| M3MAX | Metros Cúbicos Máximo | F |  |  | N |
| MARCAMODELO | Marca Modelo | S |  |  | N |
| MARCAPLACA | Marca [Placa] | S |  |  | S |
| MAXLOTACAO | Capacidade Máxima de Lotação | I |  |  | N |
| NOMEEMBARCACAO | Nome Embarcação | S |  |  | N |
| NUMMOTOR | Número do Motor | S |  |  | N |
| PESOBRUTO | Peso Bruto | F |  |  | N |
| PESOLIQ | Peso Líquido | F |  |  | N |
| PESOMAX | Peso Máximo | F |  |  | N |
| PLACA | Placa | S |  |  | N |
| POTENCIA | Potência | S |  |  | N |
| PROPRIO | Veículo da empresa | S |  |  | N |
| RENAVAM | RENAVAM | S |  |  | N |
| RESTRICAO | Restrição | I |  |  | N |
| SERIAL | Serial | S |  |  | N |
| TARA | Tara do veículo | F |  |  | N |
| TIPFORMFRETE | Tipo de Fórmula de Frete | S |  |  | N |
| TIPINT | Tipo Pintura | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPOAFERICAO | Tipo Aferição | S |  |  | N |
| TIPOCAR | Tipo de Carroceria | S |  |  | N |
| TIPOCOMBUST | Tipo de Combustível (Tabela RENAVAM) | I |  |  | N |
| TIPOEMBARCACAO | Tipo Embarcação | I |  |  | N |
| TIPOPROP | Tipo de Proprietário | S |  |  | N |
| TIPOROD | Tipo de Rodado | S |  |  | N |
| TIPOVEICULO | Tipo Veículo | S |  |  | N |
| TIPVEI | Tipo de Veículo | I |  |  | N |
| VIATRANSP | Via de Transporte | S |  |  | N |
| VLRDEPRECMENSAL | Depreciação mensal do veículo | F |  |  | N |
| VLRSEGMENSAL | Valor do Seguro Mensal | F |  |  | N |
| VOLTAGEM | Voltagem | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | VeiculoReboque1 / [TGFVEI](TGFVEI.md) | REBOQUE1->CODVEICULO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | VeiculoReboque2 / [TGFVEI](TGFVEI.md) | REBOQUE2->CODVEICULO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | VeiculoReboque3 / [TGFVEI](TGFVEI.md) | REBOQUE3->CODVEICULO | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| ContratoVeiculo / [TCSCVE](TCSCVE.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| ControlePortaria / [TGARPO](TGARPO.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEIC->CODVEICULO | I |
| ControleVeiculo / [TGFCVE](TGFCVE.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| EmbarcacoesComboio / [TGFCOMB](TGFCOMB.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| InformacoesGTV / [TGFIGTV](TGFIGTV.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEIC->CODVEICULO | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| PesagemAvulsa / [TGAPEA](TGAPEA.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEI->CODVEICULO | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEIC->CODVEICULO | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| Veiculo / [TGFVEI](TGFVEI.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPFOLHA->CODEMP | I |
| Veiculo / [TGFVEI](TGFVEI.md) | EventoVeiculoCadastro / [TGFEVEVEI](TGFEVEVEI.md) | CODVEICULO->CODVEICULO | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORMFRETE->CODFORM; TIPFORMFRETE->TIPFORM | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Funcionario /  | CODEMPFOLHA->CODEMP; CODFUNC->CODFUNC | I |
| Veiculo / [TGFVEI](TGFVEI.md) | HistoricoVeiculo / [TGFTFR](TGFTFR.md) | CODVEICULO->CODVEICULO | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTORISTA->CODPARC | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCPROPANTT->CODPARC | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Veiculo / [TGFVEI](TGFVEI.md) | QuestionarioVeiculo / [TPQQU1](TPQQU1.md) | CODQUEST->CODQUEST | I |
| VeiculoPorProjeto / [TAPVPJ](TAPVPJ.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEIPRIN->CODVEICULO | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | VeiculoReboque1 / [TGFVEI](TGFVEI.md) | CODVEIREB1->CODVEICULO | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | VeiculoReboque2 / [TGFVEI](TGFVEI.md) | CODVEIREB2->CODVEICULO | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | VeiculoReboque3 / [TGFVEI](TGFVEI.md) | CODVEIREB3->CODVEICULO | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
