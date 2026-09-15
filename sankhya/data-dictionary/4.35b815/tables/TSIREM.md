# TSIREM — Formatador de Remessa

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 24
- Instâncias DWF: 1
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormatadorRemessa | Formatador de Remessa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Detalhe | S |  |  | N |
| ARQPORLAYOUTDETALHE | Gerar um arquivo p/cada detalhe do layout | S |  |  | N |
| ARQPORLINHA | Gerar um arquivo p/cada linha | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODIGO | Código | I |  |  | N |
| CODPAI | Cód.Formatador pai | I |  |  | N |
| COMGROUPBY | Com Group By | S |  |  | N |
| COMHAVING | Com Having | S |  |  | N |
| COMORDERBY | Com Order By | S |  |  | N |
| COMSELECT | Com Select | S |  |  | N |
| CONF | Configuração | S |  |  | N |
| CONSULTASQLLOTE | Consulta SQL | S |  |  | N |
| FICHA | Primeira coluna somente p/ ordenação | S |  |  | N |
| FILTRO | Filtro | S |  |  | N |
| GRAU | Grau | I |  |  | N |
| INICARQREM | Iniciais do arquivo p/ nome automático | S |  |  | N |
| MODULO | Módulo | S |  |  | N |
| NOMEARQ | Nome do arquivo | S |  |  | N |
| ORDENAR | Ordenar | S |  |  | N |
| SEQINFO | Sequência Informada | I |  |  | N |
| TAMREGISTRO | Tamanho do Registro | I |  |  | N |
| TITULO | Título | S |  |  | N |
| UTILIZASEQALT | Utiliza Sequência Alternativa | S |  |  | N |
| UTILIZASEQINFO | Utiliza Sequência Informada | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendadorRemessaBancaria / [TGFREM](TGFREM.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODIGOLAYOUT->CODIGO | I |
| FormatadorPorTipoTitulo / [TGFFTT](TGFFTT.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODFORMREM->CODIGO; MODULO->MODULO | I |
| FormatadorRemessa / [TSIREM](TSIREM.md) | FormatadorPorTipoTitulo / [TGFFTT](TGFFTT.md) | CODIGO->CODFORMREM; MODULO->MODULO | I |
| FormatadorRemessa / [TSIREM](TSIREM.md) | FormatadorRemessaPorConta / [TGFFCT](TGFFCT.md) | CODIGO->CODFORMREM; MODULO->MODULO | I |
| FormatadorRemessa / [TSIREM](TSIREM.md) | FormatadorRemessaPorEmpresa / [TGFFEM](TGFFEM.md) | CODIGO->CODFORMREM; MODULO->MODULO | I |
| FormatadorRemessa / [TSIREM](TSIREM.md) | ItemRemessa / [TSIIRE](TSIIRE.md) | CODIGO->CODIGO; MODULO->MODULO | I |
| FormatadorRemessaPorConta / [TGFFCT](TGFFCT.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODFORMREM->CODIGO; MODULO->MODULO | I |
| FormatadorRemessaPorEmpresa / [TGFFEM](TGFFEM.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODFORMREM->CODIGO; MODULO->MODULO | I |
| ItensPacote / [TSSITP](TSSITP.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODIGO->CODIGO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODREMEDI->CODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
