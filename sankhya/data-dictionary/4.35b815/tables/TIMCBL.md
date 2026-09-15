# TIMCBL — Contas bancárias do locador

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimContaBancariaLocador | Contas bancárias do locador | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CBLAGENCIA | Agência | S |  |  | N |
| CBLBANCO | Banco | I |  |  | N |
| CBLCODIGO | Código | I |  |  | N |
| CBLCONTACORRENTE | Conta | S |  |  | N |
| CBLLOCADOR | Locador | I |  |  | N |
| CBLTIPOCONTA | Tipo da conta | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Parceiro / [TGFPAR](TGFPAR.md) | TimContaBancariaLocador / [TIMCBL](TIMCBL.md) | CODPARC->CBLLOCADOR | I |
| TimCompradorLote / [TIMLVC](TIMLVC.md) | TimContaBancariaLocador / [TIMCBL](TIMCBL.md) | LVCCLIENTE->CBLLOCADOR; LVCCONTABANC->CBLCODIGO | I |
| TimContaBancariaLocador / [TIMCBL](TIMCBL.md) | Banco / [TSIBCO](TSIBCO.md) | CBLBANCO->CODBCO | I |
| TimContaBancariaLocador / [TIMCBL](TIMCBL.md) | Parceiro / [TGFPAR](TGFPAR.md) | CBLLOCADOR->CODPARC | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | TimContaBancariaLocador / [TIMCBL](TIMCBL.md) | IMPCONTACORRENTE->CBLCODIGO; IMPPROPRIETARIO->CBLLOCADOR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
