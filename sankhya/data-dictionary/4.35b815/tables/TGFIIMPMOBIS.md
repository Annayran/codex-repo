# TGFIIMPMOBIS — TGFIIMPMOBIS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemImportacaoMOBIS | ItemImportacaoMOBIS | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IMPSTATUS | Status | S |  |  | N |
| INVOICENUMBER | Invoice Number | S |  |  | N |
| LOG | Log | C |  |  | N |
| MSG | Mensagens | C |  |  | N |
| NUIMP | Importação | I |  |  | N |
| NUNOTA | Nro. Único Pedido | I |  |  | N |
| ORDERNUMBER | Order number | S |  |  | N |
| SEQIMP | Sequencia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImportacaoArquivoMOBIS / [TGFIMPMOBIS](TGFIMPMOBIS.md) | ItemImportacaoMOBIS / [TGFIIMPMOBIS](TGFIIMPMOBIS.md) | NUIMP->NUIMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
