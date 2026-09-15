# TGFMPX — Registro de Cobranças Pix

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegistroCobrancasPix | Registro de Cobranças Pix | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPOALTERADO | Campo Alterado | S |  |  | N |
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| CODMPX | Código sequencial | I |  |  | N |
| CODPARC | Cod. Parceiro | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DHEVENTO | Dh. Evento | H |  |  | N |
| IDTRANSACAO | ID Transação | S |  |  | N |
| INFENVIADA | Inf. Enviada | S |  |  | N |
| NUFIN | Nro. Único | I |  |  | N |
| SITCOBRANCA | Sit. Cobrança | S |  |  | N |
| STATUSENVIO | Status de Envio | S |  |  | S |
| TIPOMOV | Tipo Mov. | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegistroCobrancasPix / [TGFMPX](TGFMPX.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| RegistroCobrancasPix / [TGFMPX](TGFMPX.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| RegistroCobrancasPix / [TGFMPX](TGFMPX.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RegistroCobrancasPix / [TGFMPX](TGFMPX.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
