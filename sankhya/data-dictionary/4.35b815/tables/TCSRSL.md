# TCSRSL — Regra SLA

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 15
- Instâncias DWF: 2
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegraSLA | Regra SLA | S | N |
| RegraSLAPadrao | Regra SLA Padrão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BASECALCTEMPO | Base de Cálculo para Tempo | S |  |  | N |
| CODCARGAHOR | Carga Horária | I |  |  | N |
| CODGRUPOPROD | Grupo de Produtos | I |  |  | N |
| CODOCOROS | Motivo | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODSERV | Serviço | I |  |  | N |
| NUMREG | Num. Regra | I |  |  | N |
| NUSLA | Nro. Único SLA | I |  |  | N |
| ORDEM | Ordem de Classificação | I |  |  | N |
| PADRAO | Regra Padrão | S |  |  | N |
| PRIORIDADE | Prioridade | I |  |  | N |
| TEMPOTOTAL | TEMPOTOTAL | I |  |  | N |
| TIPOTEMPO | Tipo de Tempo | S |  |  | N |
| USAREGRAPADRAO | Usar em conjunto com a regra | S |  |  | N |
| VALORTEMPO | Tempo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoSLA / [TCSSLA](TCSSLA.md) | RegraSLA / [TCSRSL](TCSRSL.md) | NUSLA->NUSLA | I |
| CabecalhoSLA / [TCSSLA](TCSSLA.md) | RegraSLAPadrao / [TCSRSL](TCSRSL.md) | NUSLA->NUSLA | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | CabecalhoSLA / [TCSSLA](TCSSLA.md) | NUSLA->NUSLA | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | CorSLA / [TCSCSL](TCSCSL.md) | NUMREG->NUMREG; NUSLA->NUSLA | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | IdentificacaoCargaHoraria /  | CODCARGAHOR->CODCARGAHOR | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | NotificacaoSLA / [TCSSLN](TCSSLN.md) | NUMREG->NUMREG; NUSLA->NUSLA | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | OcorrenciaOS / [TCSOOS](TCSOOS.md) | CODOCOROS->CODOCOROS | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| RegraSLASubOs / [TCSRSI](TCSRSI.md) | RegraSLA / [TCSRSL](TCSRSL.md) | NUSLA->NUSLA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
