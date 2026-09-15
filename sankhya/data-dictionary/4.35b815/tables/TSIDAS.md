# TSIDAS — Documento Assinado

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DocumentoAssinado | Documento Assinado | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVEARQUIVO | Chave Arquivo | S |  |  | N |
| CODUSU | Usuário Solicitante | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DETALHESTATUS | Detalhes Status | S |  |  | N |
| DHRESP | Dh. Resposta | H |  |  | N |
| DHSOLIC | Dh. Solicitação | H |  |  | N |
| HASHARQUIVO | Hash Arquivo | S |  |  | N |
| IDDESTINATARIO | ID Destinatário | S |  |  | N |
| IDSOLICITANTE | ID Solicitante | S |  |  | N |
| NOMEARQUIVO | Nome Arquivo | S |  |  | N |
| NOMEDESTINATARIO | Nome Destinatário | S |  |  | N |
| NOMESOLICITANTE | Nome Solicitante | S |  |  | N |
| NUDOC | Núm. Documento | I |  |  | N |
| STATUS | Status Assinatura Digital | S |  |  | N |
| TIPONOTIFIC | Tipo de Notificação | S |  |  | N |
| TRANSACTIONID | Transaction ID | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemOSDocAssinados / [TCSITE_DAS](TCSITE_DAS.md) | DocumentoAssinado / [TSIDAS](TSIDAS.md) | NUDOC->NUDOC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
