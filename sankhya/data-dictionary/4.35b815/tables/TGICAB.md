# TGICAB — TGICAB

- Módulo técnico: [TGI](../modules/TGI.md)
- Campos: 58
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoPropostaComercial | Proposta Comercial | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMOEDA | Moeda | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DTPROJETO | Dt.Projeto | H |  |  | N |
| DUPNOVAVERSAO | Duplicado Nova Versão | S |  |  | N |
| NOMEPROJ | Nome Projeto | S |  |  | N |
| NUPROJETO | Projeto | I |  |  | N |
| OBS | OBS | C |  |  | N |
| PERCCOMISSAO1 | PERCCOMISSAO1 | F |  |  | N |
| PERCCOMISSAO2 | PERCCOMISSAO2 | F |  |  | N |
| PERCCOMISSAO3 | PERCCOMISSAO3 | F |  |  | N |
| PERCCOMISSAO4 | PERCCOMISSAO4 | F |  |  | N |
| PERCCOMISSAO5 | PERCCOMISSAO5 | F |  |  | N |
| PERCDESCONTO | Percentual de Desconto | F |  |  | N |
| PERCDESCPROD | Percentual de Desconto do Produto | F |  |  | N |
| PERCDESCSERV | Percentual de Desconto do Serviço | F |  |  | N |
| PERCFRETE | Percentual de Frete | F |  |  | N |
| PERCIMPOSTOPROD | Imposto s/Venda do Produto | F |  |  | N |
| PERCIMPOSTOSERV | Imposto s/Venda do Serviço | F |  |  | N |
| PERCIMPOSTOSVEN | Imposto s/Venda R$ | F |  |  | N |
| PERCINTERNACAO | Percentual de Internação | F |  |  | N |
| PERCINTERNACAOPROD | Percentual Internação Produto | F |  |  | N |
| PERCINTERNACAOSERV | Percentual Internação Serviço | F |  |  | N |
| PERCLUCROLIQUIDO | Percentual Saldo R$ | F |  |  | N |
| PERCMARGEMPROD | Percentual Margem Produto | F |  |  | N |
| PERCMARGEMPROP | PERCMARGEMPROP | F |  |  | N |
| PERCMARGEMSERV | Percentual Margem Serviço | F |  |  | N |
| VERSAOPROJETO | Versão | S |  |  | N |
| VLRCUSEQUIPCIF | Custo Equip. CIF U$ | F |  |  | N |
| VLRCUSEQUIPFOB | Custo Equip. FOB U$ | F |  |  | N |
| VLRCUSEQUIPMARGEM | Custo Equip. c/Margem U$ | F |  |  | N |
| VLRDESCONTO | Desconto U$ | F |  |  | N |
| VLRGARANTIATECNICO | Valor 3 U$ | F |  |  | N |
| VLRGARHARDCIF | Garantia Hard. CIF U$ | F |  |  | N |
| VLRGARHARDFOB | Garantia Hard. FOB U$ | F |  |  | N |
| VLRGARHARDMARGEM | Garantia Hard. c/Margem U$ | F |  |  | N |
| VLRGARSOFTCIF | Garantia Software CIF U$ | F |  |  | N |
| VLRGARSOFTFOB | Garantia Software FOB U$ | F |  |  | N |
| VLRGARSOFTMARGEM | Garantia Soft. c/Margem U$ | F |  |  | N |
| VLRIMPOSTOSVEN | Valor Imposto s/Venda R$ | F |  |  | N |
| VLRINSTALACAO | Instalação U$ | F |  |  | N |
| VLRINTERNACAO | Internação U$ | F |  |  | N |
| VLRLUCROLIQUIDOREAL | Saldo R$ | F |  |  | N |
| VLRMARGEMPROP | VLRMARGEMPROP | F |  |  | N |
| VLRMOEDA | Valor Moeda | F |  |  | N |
| VLROUTROSCUSCIF | Outros Custos CIF U$ | F |  |  | N |
| VLROUTROSCUSMARGEM | Outros Custos c/Margem U$ | F |  |  | N |
| VLRTOTAL | Total Geral U$ | F |  |  | N |
| VLRTOTALARREDONDADO | Total Arredondado R$ | F |  |  | N |
| VLRTOTALCIF | Total CIF U$ | F |  |  | N |
| VLRTOTALCOMISSAO | Comissões | F |  |  | N |
| VLRTOTALCOMSPARE | Total c/Rateio R$ | F |  |  | N |
| VLRTOTALCUS | Total Custo R$ | F |  |  | N |
| VLRTOTALFOB | Total FOB U$ | F |  |  | N |
| VLRTOTALREAL | Total Geral R$ | F |  |  | N |
| VLRTREINAMENTO | Treinamento U$ | F |  |  | N |
| VLRVIAGEMINT | Valor 2 U$ | F |  |  | N |
| VLRVIAGEMNAC | Valor 1 U$ | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | ConfiguracaoImpressaoProposta / [TGICIP](TGICIP.md) | NUPROJETO->NUPROJETO; VERSAOPROJETO->VERSAOPROJETO | I |
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | FinanceiroPropostaComercial / [TGIFIN](TGIFIN.md) | NUPROJETO->NUPROJETO; VERSAOPROJETO->VERSAOPROJETO | I |
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | KitPropostaComercial / [TGIKIT](TGIKIT.md) | NUPROJETO->NUPROJETO; VERSAOPROJETO->VERSAOPROJETO | I |
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | RateioPropostaComercial / [TGIRAT](TGIRAT.md) | NUPROJETO->NUPROJETO; VERSAOPROJETO->VERSAOPROJETO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
