# TWFASLA — Alerta SLA

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AlertaSLA | Alerta SLA | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Active | S |  |  | N |
| CODPRN | Cód. Processo | I |  |  | N |
| CODSMTP | Conta SMTP | I |  |  | N |
| EXPRDESTINATARIOS | Expressão de destinatarios | C |  |  | N |
| NOTIFICAPADRAO | Notificar padrão | S |  |  | N |
| REGRA | Regra | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| TEMPOLIMITE | Tempo limite | I |  |  | N |
| TIPONOTIFICACAO | Tipo de notificação | S |  |  | N |
| VERSAO | Versão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlertaSLA / [TWFASLA](TWFASLA.md) | ServidorSMTP / [TSISMTP](TSISMTP.md) | CODSMTP->CODSMTP | I |
| AlertaSLA / [TWFASLA](TWFASLA.md) | SLAProcessoNegocio / [TWFRSLA](TWFRSLA.md) | CODPRN->CODPRN; REGRA->REGRA; VERSAO->VERSAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
