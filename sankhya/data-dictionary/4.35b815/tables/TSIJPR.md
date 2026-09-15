# TSIJPR — Trabalho de impressão

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 18
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TrabalhoImpressao | Trabalho de impressão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| COPIES | Cópias | I |  |  | N |
| DESCRIPTION | Descrição | S |  |  | N |
| DHINC | Dt. Inclusão | H |  |  | N |
| DHULTENV | Dt. Último envio | H |  |  | N |
| FILENAME | Arquivo | S |  |  | N |
| IDDOC | Número Documento | S |  |  | N |
| IDGRUPOJOB | Transação | S |  |  | N |
| JOBID | Código no Servidor de impressão | S |  |  | N |
| JOBSTATUS | Status | S |  |  | N |
| MIMETYPE | Tipo de arquivo | S |  |  | N |
| MSGERRO | Mensagem de erro | S |  |  | N |
| NUJPR | Código | I |  |  | N |
| NUMTENTATIVAS | Número de tentativas | I |  |  | N |
| ORIGINALPRINTER | Nome da impressora | S |  |  | N |
| PRINTERURI | Endereço da impressora | S |  |  | N |
| PRINTSERVERURI | Endereço do servidor de impressão | S |  |  | N |
| TIPODOC | Tipo de documento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TrabalhoImpressao / [TSIJPR](TSIJPR.md) | StatusTrabalhoImpressao / [TSIJPS](TSIJPS.md) | JOBSTATUS->JOBSTATUS | I |
| TrabalhoImpressao / [TSIJPR](TSIJPR.md) | TipoArquivo / [TSITPA](TSITPA.md) | MIMETYPE->DOCTASTE | I |
| TrabalhoImpressao / [TSIJPR](TSIJPR.md) | TipoDocumento / [TSITPD](TSITPD.md) | TIPODOC->DOCTYPE | I |
| TrabalhoImpressao / [TSIJPR](TSIJPR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
