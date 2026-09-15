# TSICND — TABLE TSICND

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 24
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConsolidadorDados | Consolidador Dados Genérico | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODCONSOLIDACAO | Código | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONSULTA | Consulta | C |  |  | N |
| DATAINICIAL | Data Inicial | D |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| DHPROXEXEC | Dt. Próx. Execução | H |  |  | N |
| DHULTEXEC | Dt. Últ. Execução | H |  |  | N |
| EXPGATILHO | Expressão de Gatilho | S |  |  | N |
| FONTEDADOS | Fonte Dados | S |  |  | N |
| INVALIDO | INVALIDO | S |  |  | N |
| LOG_ERRO | Log Erro | S |  |  | S |
| OBSERVACAO | Observação | S |  |  | N |
| QTDMESESCONSOLIDAR | Qtd. Meses a Consolidar | I |  |  | N |
| QTDMESESRETROAGIR | Qtd. Meses a Retroagir | I |  |  | N |
| STATUS | Status | S |  |  | S |
| TABELA | Nome da Tabela no Banco de Dados | S |  |  | N |
| TEMPOPROCESSAMENTO | Tempo de Processamento (seg.) | I |  |  | N |
| TIPCONSOLIDACAO | Início da Consolidação | I |  |  | N |
| TIPGATILHO | Tipo de Gatilho | S |  |  | N |
| TIPINTERVALO | Tipo de Intervalo | S |  |  | N |
| TIPPROCESSAMENTO | Agrupamento | S |  |  | N |
| VLRINTERVALO | Valor | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConsolidadorDados / [TSICND](TSICND.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
