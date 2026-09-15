# TIMLVC — Comprador do Lote

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimCompradorLote | Compradores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LVCBOLETO | Resp. Boleto | S |  |  | N |
| LVCCLIENTE | Cliente | I |  |  | N |
| LVCCONTABANC | Conta Bancária | I |  |  | N |
| LVCCONTRATO | Contrato | I |  |  | N |
| LVCDESCRICAO | Descrição | S |  |  | S |
| LVCENDERECO | Endereço | I |  |  | N |
| LVCPROCURADOR | Procurador | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimCompradorLote / [TIMLVC](TIMLVC.md) | Contato / [TGFCTT](TGFCTT.md) | LVCCLIENTE->CODPARC; LVCENDERECO->CODCONTATO | I |
| TimCompradorLote / [TIMLVC](TIMLVC.md) | Parceiro / [TGFPAR](TGFPAR.md) | LVCCLIENTE->CODPARC | I |
| TimCompradorLote / [TIMLVC](TIMLVC.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | LVCPROCURADOR->CODPARC | I |
| TimCompradorLote / [TIMLVC](TIMLVC.md) | TimContaBancariaLocador / [TIMCBL](TIMCBL.md) | LVCCLIENTE->CBLLOCADOR; LVCCONTABANC->CBLCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimCompradorLote / [TIMLVC](TIMLVC.md) | LTVCODIGO->LVCCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
