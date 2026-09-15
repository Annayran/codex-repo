# TWFAPVT — Prazo de Vencimento da Tarefa

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrazoVencimentoAlerta | Prazo de Vencimento da Tarefa - Alertas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativa | S |  |  | N |
| CODPRN | Cód. Processo | I |  |  | N |
| CODSMTP | Cód. Conta | I |  |  | N |
| DECORRIDO | Decorrido | F |  |  | N |
| EXPRDESTINATARIOS | Expressão de destinatarios | C |  |  | N |
| IDELEMENTO | Código do Elemento | S |  |  | N |
| NOTIFICADONO | Notificar dono | S |  |  | N |
| NOTIFICAGESTOR | Notificar gestor | S |  |  | N |
| NUELE | Núm. Elemento | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| TIPONOTIFICACAO | Tipo de notificação | S |  |  | N |
| VERSAO | Versão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrazoVencimentoAlerta / [TWFAPVT](TWFAPVT.md) | PrazoVencimentoTarefa / [TWFPVT](TWFPVT.md) | NUELE->NUELE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
