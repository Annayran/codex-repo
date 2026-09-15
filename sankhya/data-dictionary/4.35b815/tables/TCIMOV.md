# TCIMOV — Movimentação de Imobilizado

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MovimentacaoImobilizado | Movimentação de Imobilizado | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Cód.Código do Bem | S |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODDEP | Cód.Departamento | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODPROD | Cód.Produto | I |  |  | N |
| DTMOVTO | Data do movimento | H |  |  | N |
| NUMLANC | Lançamento | I |  |  | N |
| NUMLOTE | Lote | I |  |  | N |
| REFERENCIA | Data referência | H |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPMOV | Tipo Movimento | S |  |  | N |
| TIPO | Tipo | I |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRDEP | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MovimentacaoImobilizado / [TCIMOV](TCIMOV.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| MovimentacaoImobilizado / [TCIMOV](TCIMOV.md) | Departamento /  | CODDEP->CODDEP | I |
| MovimentacaoImobilizado / [TCIMOV](TCIMOV.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| MovimentacaoImobilizado / [TCIMOV](TCIMOV.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| saldoImobilizado / [TCISAL](TCISAL.md) | MovimentacaoImobilizado / [TCIMOV](TCIMOV.md) | CODBEM->CODBEM; CODPROD->CODPROD; REFERENCIA->REFERENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
