# TGAHTP — Historico de Transferência

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 20
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoTransPro | Historico Transferencia Propriedade | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODHIST | Nro Transferência | I |  |  | N |
| CODPARCENT | Cod. Parceiro Destino | I |  |  | S |
| CODPARCSAI | Cod. Parceiro Origem | I |  |  | S |
| CODUSU | Usuário | I |  |  | N |
| DHEMISSAO | Dt. Emissão Nota de Referência | D |  |  | N |
| DHTRANS | Dt. Transferência | D |  |  | N |
| DTEMISSAOENTRADA | Dt. Emissão Nota de Entrada | D |  |  | S |
| DTEMISSAOSAIDA | Dt. Emissão Nota de Saída | D |  |  | S |
| NOMEPARCDEST | Parceiro Contrato Destino | S |  |  | S |
| NOMEPARCENT | Parceiro Contrato Origem | S |  |  | S |
| NUMCONTDEST | Contrato Destino | I |  |  | N |
| NUMCONTORIG | Contrato Origem | I |  |  | N |
| NUMNOTA | Nº Nota Referência | S |  |  | N |
| NUMNOTAENT | Nº Nota Entrada | I |  |  | S |
| NUMNOTASAI | Nº Nota Saída | I |  |  | S |
| NUNOTAENT | Nº Único Nota Entrada | I |  |  | N |
| NUNOTASAI | Nº Único Nota Saída | I |  |  | N |
| STATUSNOTAENT | Status Nota de Entrada | S |  |  | S |
| STATUSNOTASAI | Status Nota de Saída | S |  |  | S |
| VLRNOTA | Vlr. Nota Referência | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApuracaoArmazenagemTrans / [TGATRA](TGATRA.md) | HistoricoTransPro / [TGAHTP](TGAHTP.md) | NUMTRANS->CODHIST | I |
| HistoricoTransPro / [TGAHTP](TGAHTP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
