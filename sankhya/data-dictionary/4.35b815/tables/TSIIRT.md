# TSIIRT — SI Detalhes do Retorno Movimento Bancário com Hierarquia

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalheRetornoMovimentoBco | Detalhe do Retorno Bancário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | Código | I |  |  | N |
| IDLINHA | Identificador | S |  |  | N |
| IDVALOR | Conteúdo p/Identificação | S |  |  | N |
| NOME | Nome do Campo | S |  |  | N |
| POSFIM | Fim | I |  |  | N |
| POSINI | Início | I |  |  | N |
| QTDDEC | Decimais | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheRetornoMovimentoBco / [TSIIRT](TSIIRT.md) | OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | CODIGO->CODIGO; SEQUENCIA->SEQUENCIA | I |
| RetornoMovimentoBancario / [TSIRET](TSIRET.md) | DetalheRetornoMovimentoBco / [TSIIRT](TSIIRT.md) | CODIGO->CODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
