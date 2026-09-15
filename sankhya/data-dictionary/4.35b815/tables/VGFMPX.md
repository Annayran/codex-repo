# VGFMPX — VIEW VGFMPX

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewRegistroCobrancasPix | Registro de Cobrancas Pix | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| DTTRANSACAO | Dt. Transação | H |  |  | N |
| IDTRANSACAO | ID Transação | S |  |  | N |
| NUFIN | Nro. Único Financeiro | I |  |  | N |
| SITCOBRANCA | Situação Cobrança | S |  |  | N |
| STATUSPIX | Status de Envio | S |  |  | N |
| VLRTRANSACAO | Vlr. transação | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewRegistroCobrancasPix / [VGFMPX](VGFMPX.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ViewRegistroCobrancasPix / [VGFMPX](VGFMPX.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| ViewRegistroCobrancasPix / [VGFMPX](VGFMPX.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
