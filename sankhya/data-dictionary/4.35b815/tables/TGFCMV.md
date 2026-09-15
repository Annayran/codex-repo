# TGFCMV — Config. Consolidação de movimentos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 37
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConsolidacaoConfig | Config. Consolidação de movimentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGRUPACENCUS | Centro de Resultado | S |  |  | N |
| AGRUPALOCAL | Local Estoque | S |  |  | N |
| AGRUPANATUREZA | Natureza | S |  |  | N |
| AGRUPAPARC | Parceiro | S |  |  | N |
| AGRUPAPROJETO | Projeto | S |  |  | N |
| AGRUPASUBTIPVEN | Subtipo de Negociação | S |  |  | N |
| AGRUPATOP | Tipo Operação | S |  |  | N |
| AGRUPAVENDEDOR | Vendedor | S |  |  | N |
| AUTOMATICO | Consolidação inserida automaticamente | S |  |  | N |
| CODCONFIG | Código | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| COMISSAOPOR | Comissão por | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Dt. Alteração | D |  |  | N |
| DHPROXEXEC | Dt. próx. execução | H |  |  | N |
| DHULTEXEC | Dt. últ. execução | H |  |  | N |
| DTCONS | Data inicial | D |  |  | N |
| FORMGASTOFIX | Formação do gasto fixo | I |  |  | N |
| HORAAGENDA1 | Hora primeira atualização (diário) | S |  |  | N |
| HORAAGENDA2 | Hora segunda atualização (diário) | S |  |  | N |
| IMPRETNOTA | Considerar impostos retidos no valor da nota? | S |  |  | N |
| INTERVALOATUALIZACAO | Qtd. meses a retroagir | I |  |  | N |
| PERCCOMISSAO | Porcentagem | F |  |  | N |
| PERCUSTOFIX | % de Custo Fixo | F |  |  | N |
| QTDMESESCONSOLIDAR | Qtd. meses consolidar | I |  |  | N |
| RATGASTOFIX | Rateio do Gasto Fixo no período | F |  |  | N |
| TEMCOMISSAO | Tem comissão? | I |  |  | N |
| TEMPOPROCESSAMENTO | Tempo de processamento (minutos) | I |  |  | N |
| TIPCONFIG | Início da consolidação | I |  |  | N |
| TIPDTCOM | Na compra | I |  |  | N |
| TIPDTDEVCOM | Na devolução da compra | I |  |  | N |
| TIPDTDEVVEN | Na devolução da venda | I |  |  | N |
| TIPDTGASTOFIX | Data para Gastos Fixos | I |  |  | N |
| TIPDTVEN | Na venda | I |  |  | N |
| TIPFATPER | Faturamento Personalizado | I |  |  | N |
| TIPOCUSTO | Custo a ser considerado | I |  |  | N |
| USACONFNATRECDESP | Usar as configurações do cadastro de Naturezas de Receitas e Despesas? | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConsolidacaoAcessos / [TGFUCM](TGFUCM.md) | ConsolidacaoConfig / [TGFCMV](TGFCMV.md) | CODCONFIG->CODCONFIG | I |
| ConsolidacaoConfig / [TGFCMV](TGFCMV.md) | ConsolidacaoAcessos / [TGFUCM](TGFUCM.md) | CODCONFIG->CODCONFIG | I |
| ConsolidacaoConfig / [TGFCMV](TGFCMV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
