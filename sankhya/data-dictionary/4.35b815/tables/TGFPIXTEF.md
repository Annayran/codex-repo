# TGFPIXTEF — Tabela para TGFPIXTEF

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PixTEF | Pix TEF | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CUPOM | Cupom | S |  |  | N |
| CUPOMCLIENTE | Cupom cliente | S |  |  | N |
| CUPOMESTABELECIMENTO | Cupom estabelecimento | S |  |  | N |
| GATEWAY | Gateway | S |  |  | N |
| HOSTNAME | Dispositivo | S |  |  | N |
| IDENTIFICACAO | Identificador TEF | I |  |  | N |
| NSU | NSU | S |  |  | N |
| NUFIN | Nro. Financeiro | I |  |  | N |
| OPERACAO | Operação TEF | I |  |  | N |
| SEQPIX | PIX | I |  |  | N |
| SEQPIXTEF | Nro. Pix TEF | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | PixTEF / [TGFPIXTEF](TGFPIXTEF.md) | PIXTEF->SEQPIXTEF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
