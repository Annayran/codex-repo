# TGFREM — Tarefa Agendada para Geração de Remessa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 27
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AgendadorRemessaBancaria | Agendador para Geração de Remessa Bancária | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGRUPARTIT | Agrupar pagamentos | S |  |  | N |
| ATIVA | Ativa | S |  |  | N |
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| CODIGO | Código | I |  |  | N |
| CODIGOLAYOUT | Layout | I |  |  | N |
| CODTIPTIT | Alterar Tipo de Título para | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Dh. alteração | H |  |  | N |
| DHULTGERARQ | Dh. última geração | H |  |  | N |
| DIASPERFIM | Data atual (+) | I |  |  | N |
| DIASPERINI | Data atual (-) | I |  |  | N |
| DIRDESTINO | Diretório destino | S |  |  | N |
| FILTROTIT | Filtros de títulos | C |  |  | N |
| GERANOSSONUM | Gerar nosso número | S |  |  | N |
| GERLINHADIG | Gerar linha digitável | S |  |  | N |
| HRFINALREM | Hora final p/ envio de remessa | T |  |  | N |
| HRINICIALREM | Hora inicial p/ envio de remessa | T |  |  | N |
| INTERVALGERARQ | Intervalo de geração (min) | I |  |  | N |
| NOMEARQ | Nome arquivo | S |  |  | N |
| PERMPROVISAO | Permite provisão | S |  |  | N |
| TIPOPERIODO | Tipo de período | S |  |  | N |
| TIPORECDESP | Receita/Despesa | S |  |  | N |
| TITNOSSONUMPRE | Títulos com Nosso Número preenchido | S |  |  | N |
| TITNROREMVAZIO | Títulos com Nro. Remessa vazio | S |  |  | N |
| USABCOCONTA | Alterar banco dos títulos para banco da conta | S |  |  | N |
| USANOMELAY | Usar nome arquivo do layout | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendadorRemessaBancaria / [TGFREM](TGFREM.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| AgendadorRemessaBancaria / [TGFREM](TGFREM.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODIGOLAYOUT->CODIGO | I |
| AgendadorRemessaBancaria / [TGFREM](TGFREM.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| AgendadorRemessaBancaria / [TGFREM](TGFREM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
