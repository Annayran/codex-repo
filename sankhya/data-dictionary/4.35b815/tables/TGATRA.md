# TGATRA — Transferir contratos armazenagem

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 22
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ApuracaoArmazenagemTrans | Transferencia Apu Cont Armarzenagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AVULSA | Avulsa | S |  |  | N |
| CODDEST | Contrato Destino | I |  |  | N |
| CODTRA | Expedição | I |  |  | N |
| DATACAD | Data | H |  |  | N |
| DATAFINAL | Data Final | H |  |  | N |
| DATAINICIO | Data Inicio | H |  |  | N |
| NUMCONTRATO | Contrato (Origem) | I |  |  | N |
| NUMTRANS | Transferência de Propriedade | I |  |  | N |
| PESTOTAL | Peso Total | F |  |  | N |
| QTDTRANS | Qtd. da Transferência (Origem) | F |  |  | N |
| QTDTRIDA | Qtd. da Transferência (Destino) | F |  |  | N |
| SACASTOTAL | Total Sacas | F |  |  | N |
| SACASTRANS | Sacas da Transferência | F |  |  | N |
| SCTRIDA | Sacas da Transferência (Destino) | F |  |  | N |
| STATUS | Status | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VLRMEDIOSC | Vlr Médio por SC | F |  |  | N |
| VLRPORSC | Vlr. por SC (Origem) | F |  |  | N |
| VLRPORSCRIDA | Vlr. por SC (Destino) | F |  |  | N |
| VLRSERVPEN | Vlr. do Serviço pendente | F |  |  | N |
| VLRSERVTRANS | Vlr. do Serviço a Transferir | F |  |  | N |
| VLRSERVTRIDA | Vlr. do Serviço a Transferir (Destino) | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApuracaoArmazenagemDesc / [TGADES](TGADES.md) | ApuracaoArmazenagemTrans / [TGATRA](TGATRA.md) | CODTRA->CODTRA | I |
| ApuracaoArmazenagemTrans / [TGATRA](TGATRA.md) | ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | CODDEST->NUMCONTRATO | I |
| ApuracaoArmazenagemTrans / [TGATRA](TGATRA.md) | HistoricoTransPro / [TGAHTP](TGAHTP.md) | NUMTRANS->CODHIST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
