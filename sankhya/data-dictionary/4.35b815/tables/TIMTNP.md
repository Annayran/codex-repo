# TIMTNP — Novo Comprador

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimNovoComprador | Novo Comprador | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| TNPBOLETO | Resp. Boleto | S |  |  | N |
| TNPCONTRATO | Cod. Contrato | I |  |  | N |
| TNPENDERECO | Endereço | I |  |  | N |
| TNPPESSOA | Pessoa | I |  |  | N |
| TNPPROCURADOR | Procurador | I |  |  | N |
| TNPTRANSFERENCIA | Cod. Transferência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimNovoComprador / [TIMTNP](TIMTNP.md) | Contato / [TGFCTT](TGFCTT.md) | TNPENDERECO->CODCONTATO; TNPPESSOA->CODPARC | I |
| TimNovoComprador / [TIMTNP](TIMTNP.md) | Parceiro / [TGFPAR](TGFPAR.md) | TNPPESSOA->CODPARC | I |
| TimNovoComprador / [TIMTNP](TIMTNP.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | TNPPROCURADOR->CODPARC | I |
| TimNovoComprador / [TIMTNP](TIMTNP.md) | TimTransferencia / [TIMTLV](TIMTLV.md) | TNPCONTRATO->TLVCONTRATO; TNPTRANSFERENCIA->TLVSEQUENCIA | I |
| TimTransferencia / [TIMTLV](TIMTLV.md) | TimNovoComprador / [TIMTNP](TIMTNP.md) | TLVCONTRATO->TNPCONTRATO; TLVSEQUENCIA->TNPTRANSFERENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
