# TGFORD — Ordens de Carga

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 54
- Instâncias DWF: 1
- Relações DWF: 22
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OrdemCarga | Ordem de Carga | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODDOCA | Doca p/ separação | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODEMPPAI | Empresa Pai | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPARCDEST | Parceiro Destino da Rota | I |  |  | N |
| CODPARCMOTORISTA | Parceiro Motorista | I |  |  | N |
| CODPARCORIG | Parceiro Origem da Rota | I |  |  | N |
| CODPARCTRANSP | Parceiro Transportadora | I |  |  | N |
| CODREG | Região | I |  |  | N |
| CODROTA | Rota | I |  |  | N |
| CODROTEIRIZADOR | Código Roteirizador | S |  |  | N |
| CODTIPOPERTRANSB | Tipo Operação | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSURETORNO | Cód. Usuário Retorno | I |  |  | N |
| CODVEICULO | Veículo | I |  |  | N |
| DHFINALPESAGEM | Data e hora final Pesagem | H |  |  | N |
| DHINICIALPESAGEM | Data e hora inicial Pesagem | H |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| DTALTERROTCAT | Data Alteração Erro Rotas | H |  |  | N |
| DTINIC | Data início | D |  |  | N |
| DTPREVSAIDA | Data prevista para saída | D |  |  | N |
| DTRETORNO | Data de Retorno | H |  |  | N |
| ENTSAI | Tipo de OC | S |  |  | N |
| ENVIOWMS | Enviado p/ WMS | S |  |  | N |
| FRETECALC | Frete Cálculo | S |  |  | N |
| HORASAIDA | Hora da saída | H |  |  | N |
| IDORDEMCARGA | Cód. Barras | S |  |  | N |
| JUSTIFICATIVA | Justificativa de escolha-Veículo/Motorista | S |  |  | N |
| KMFIN | Km Final | F |  |  | N |
| KMINIC | Km Inicial | F |  |  | N |
| M3MAX | Metros Cúbicos Máximo | F |  |  | N |
| NROFROTA | Nro. da Frota | I |  |  | N |
| NUCAIXA | Número do Caixa | I |  |  | N |
| NUFINACERTO | Número Financeiro Acerto | I |  |  | N |
| NUVIAG | Número da Viagem | I |  |  | N |
| OBSMOTORISTA | Observações | S |  |  | N |
| ORDEMCARGA | Ordem de carga | I |  |  | N |
| ORDEMCARGAPAI | Ordem Carga Pai | I |  |  | N |
| PESOMAX | Peso máximo | F |  |  | N |
| PRIORIDADE | Prioridade | I |  |  | N |
| QTDENTREGA | Qtd. Entrega | I |  |  | N |
| ROTEIRO | Roteiro | S |  |  | N |
| SEQCARGA | Sequência Carga | I |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| STATUSAVAL | Avaliação de Veículo/Motorista | S |  |  | N |
| TEMTRANSBORDO | Transbordo | S |  |  | N |
| TIPCALCFRETE | Tipo de Cálculo de Frete | I |  |  | N |
| TIPCARGA | Tipo de Carga | S |  |  | N |
| TIPDIST | Tipo de Distância p/ Valor Manual/Tabela | S |  |  | N |
| TIPEMBALAGEM | Tipo de Embalagem | S |  |  | N |
| TOTALCARGA | Total da carga | F |  |  | S |
| VINCROT | Gerar vinculação automática da rota para ordem de carga | S |  |  | N |
| VLRDIFACERTO | Valor diferença acerto | F |  |  | N |
| VLRFRETE | Valor do Frete | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcertoCarga / [TGFACR](TGFACR.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMP->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMP->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | OrdemCarga / [TGFORD](TGFORD.md) | ORDEMCARGA->ORDEMCARGA | I |
| ControleCaixa / [TGFCAI](TGFCAI.md) | OrdemCarga / [TGFORD](TGFORD.md) | NUCAIXA->NUCAIXA | I |
| ControleVeiculo / [TGFCVE](TGFCVE.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMP->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMP->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| OrdemCarga / [TGFORD](TGFORD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | ORDEMCARGA->ORDEMCARGA | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Doca /  | CODDOCA->CODDOCA | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| OrdemCarga / [TGFORD](TGFORD.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Motorista / [TGFPAR](TGFPAR.md) | CODPARCMOTORISTA->CODPARC | I |
| OrdemCarga / [TGFORD](TGFORD.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| OrdemCarga / [TGFORD](TGFORD.md) | ParceiroOrigem / [TGFPAR](TGFPAR.md) | CODPARCORIG->CODPARC | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| OrdemCarga / [TGFORD](TGFORD.md) | RomaneioCarga / [TGFROM](TGFROM.md) | CODEMP->CODEMPOC; ORDEMCARGA->ORDEMCARGA | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Rota / [TGFROT](TGFROT.md) | CODROTA->CODROTA | I |
| OrdemCarga / [TGFORD](TGFORD.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPERTRANSB->CODTIPOPER | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| RomaneioCarga / [TGFROM](TGFROM.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMPOC->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMP->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMP->CODEMP; ORDEMCARGA->ORDEMCARGA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
