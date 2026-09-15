# TGFPJBH — Historico boletos rapido

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BoletoRapidoHist | Historico Boleto Rápido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | USUARIO ALTERAÇÃO | I |  |  | N |
| DTALT | DATA MOVIMENTO | H |  |  | N |
| IDUNICO | Nº IDENTIFICAÇÃO BOLETO | I |  |  | N |
| MOTIVO | MOTIVO | S |  |  | N |
| SITREGBOL | SITUAÇÃO BOLETO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BoletoRapidoHist / [TGFPJBH](TGFPJBH.md) | BoletoRapido / [TGFPJB](TGFPJB.md) | IDUNICO->IDUNICO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
