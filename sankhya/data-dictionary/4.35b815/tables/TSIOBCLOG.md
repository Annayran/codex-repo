# TSIOBCLOG — Log de ocorrências bancárias

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 33
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LogOcorrenciasBancarias | Log de Ocorrências Bancárias | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALTERADO | Alterado | S |  |  | N |
| BAIXADO | Baixado | S |  |  | N |
| CARTEIRA | Carteira | I |  |  | N |
| CODAGE | Agência | S |  |  | N |
| CODBCO | Banco | I |  |  | N |
| CODCTABCO | Cta. Bancária | S |  |  | N |
| CODMSGERRO | Status da Mensagem | I |  |  | N |
| CODOCORRENCIA | Ocorrência | S |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| CONCILIADO | Conciliado | S |  |  | N |
| DATOCORRENCIA | Dt. Ocorrência | H |  |  | N |
| DESCRICAO | Desc. Ocorrência | S |  |  | N |
| DTBAIXA | Dt. Baixa | H |  |  | N |
| DTCREDITO | Dt. Crédito | H |  |  | N |
| DTVENCIMENTO | Dt. Vencimento | H |  |  | N |
| INTERROMPIDO | Interrompido | S |  |  | N |
| NOMEARQ | Nome do Arquivo | S |  |  | N |
| NOSSONUM | Nosso Nro | S |  |  | N |
| NROLINHA | Nro Linha | I |  |  | N |
| NUFIN | Nro Único | I |  |  | N |
| NUOBCLOG | Nro Único Proc/Prev. | I |  |  | N |
| OBSERVACAO | Detalhe da Mensagem | S |  |  | N |
| REGISTRADONOSSONUM | Registra Nosso Num | S |  |  | N |
| REPORTADO | Reportado | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TRATAROCORRENCIA | Tratar Ocorrência | S |  |  | N |
| VLRBAIXA | Vlr. Baixa | F |  |  | N |
| VLRDESC | Vlr. Desc. | F |  |  | N |
| VLRJURO | Vlr. Juro | F |  |  | N |
| VLRMULTA | Vlr. Multa | F |  |  | N |
| VLRTITULO | Vlr. Título | F |  |  | N |
| VLRTXADM | Vlr. Adm. | F |  |  | N |
| VLRVENDOR | Vlr. Vendor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LogOcorrenciasBancarias / [TSIOBCLOG](TSIOBCLOG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
