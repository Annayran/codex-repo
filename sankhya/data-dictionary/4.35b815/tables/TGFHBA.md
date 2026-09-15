# TGFHBA — TABELA DE CONTROLE DOS BOLETOS DA API NA TGFRAF

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleTituloFinanceiro | Fazer o controle dos titulos financeiros gerados na TGFRAF | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHALTER | DATA DA ALTERACAO | H |  |  | N |
| DHENVIOAPI | DATA DO ENVIO PARA A API | H |  |  | N |
| DHINC | DATA DA CRIACAO | H |  |  | N |
| EDI | EDI | S |  |  | N |
| ID | ID | I |  |  | N |
| IDAPIBANCO | idapibanco | I |  |  | N |
| MSGERRO | MENSAGEM DE ERRO | S |  |  | N |
| NOSSONUM | NOSSO NUMERO DO BOLETO | S |  |  | N |
| NUFIN | NUMERO UNICO | I |  |  | N |
| STATUS | STATUS | S |  |  | N |
| STATUSBANCO | STATUS DO BOLETO NO BANCO | S |  |  | N |
| STATUSENVIO | STATUS DE ENVIO PARA A API | S |  |  | N |
| TIPOENVIO | TIPO DE REGISTRO | S |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
