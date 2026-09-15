# TWFVAR — Variaveis de Processo

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
| VariavelProcesso | Variáveis de Processo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DEFAULTLONG | Valor padrão | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| ESCOPO | Escopo | S |  |  | N |
| LEITURA | Leitura | S |  |  | N |
| NOME | Nome | S |  |  | N |
| NUELE | Núm. Elemento | I |  |  | N |
| NUVAR | Núm. Variável | I |  |  | N |
| OBRIGATORIO | Obrigatório | S |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VALORPADRAO | Valor padrão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| VariavelProcesso / [TWFVAR](TWFVAR.md) | ElementoProcesso / [TWFELE](TWFELE.md) | NUELE->NUELE | I |
| VariavelProcesso / [TWFVAR](TWFVAR.md) | PropriedadeVariavelProcesso / [TWFPRV](TWFPRV.md) | NUVAR->NUVAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
