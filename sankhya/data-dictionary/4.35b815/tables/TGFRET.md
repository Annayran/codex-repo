# TGFRET — Tarefa Agendada para Processamento do Retorno

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AgendadorRetornoBancario | Agendador para Processamento de Retorno Bancário | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVA | Ativa | S |  |  | N |
| CODIGO | Código | I |  |  | N |
| CODIGOLAYOUT | Layout | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DIRARQ | Diretório p/ processamento | S |  |  | N |
| DIRARQPROC | Diretório p/ processados | S |  |  | N |
| JURMULTNEGOC | Considerar juros e multa negociados | S |  |  | N |
| OBTVALORPROPTIT | Obter o valor do título do próprio título | S |  |  | N |
| USABCOLOCTIT | Usar banco c/ Nosso Núm. p/ localizar o título | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendadorRetornoBancario / [TGFRET](TGFRET.md) | RetornoMovimentoBancario / [TSIRET](TSIRET.md) | CODIGOLAYOUT->CODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
