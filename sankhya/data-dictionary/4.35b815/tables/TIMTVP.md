# TIMTVP — Antigo Comprador

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimAntigoComprador | Antigo Comprador | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| TVPCONTRATO | Cod. Contrato | I |  |  | N |
| TVPENDERECO | Endereço | I |  |  | N |
| TVPPESSOA | Pessoa | I |  |  | N |
| TVPPROCURADOR | Procurador | I |  |  | N |
| TVPTRANSFERENCIA | Cod. Transferência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAntigoComprador / [TIMTVP](TIMTVP.md) | Contato / [TGFCTT](TGFCTT.md) | TVPENDERECO->CODCONTATO; TVPPESSOA->CODPARC | I |
| TimAntigoComprador / [TIMTVP](TIMTVP.md) | Parceiro / [TGFPAR](TGFPAR.md) | TVPPESSOA->CODPARC | I |
| TimAntigoComprador / [TIMTVP](TIMTVP.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | TVPPROCURADOR->CODPARC | I |
| TimAntigoComprador / [TIMTVP](TIMTVP.md) | TimTransferencia / [TIMTLV](TIMTLV.md) | TVPCONTRATO->TLVCONTRATO; TVPTRANSFERENCIA->TLVSEQUENCIA | I |
| TimTransferencia / [TIMTLV](TIMTLV.md) | TimAntigoComprador / [TIMTVP](TIMTVP.md) | TLVCONTRATO->TVPCONTRATO; TLVSEQUENCIA->TVPTRANSFERENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
