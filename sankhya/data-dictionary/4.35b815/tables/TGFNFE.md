# TGFNFE — Arquivos XML de NFe

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 26
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ArquivoNFe | Arquivos xml NFe | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVENFE | CHAVENFE | S |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| QRCODE | QRCODE | S |  |  | N |
| XML | XML | C |  |  | N |
| XMLCANC | XMLCANC | C |  |  | N |
| XMLENVCANCCONF | Xml de envio do Cancelamento do Evento de Conciliação Financeira | C |  |  | N |
| XMLENVCANCPRORROG | Xml de envio do Cancelamento do Evento de Prorrogação de Prazo de Suspensão do Icms | C |  |  | N |
| XMLENVCARTA | Xml Envio Carta de Correção | C |  |  | N |
| XMLENVCLI | XMLENVCLI | C |  |  | N |
| XMLENVCLICANC | XMLENVCLICANC | C |  |  | N |
| XMLENVCLICANCCONF | XMLENVCLICANCCONF | C |  |  | N |
| XMLENVCLICANCPRORROG | XMLENVCLICANCPRORROG | C |  |  | N |
| XMLENVCLICARTA | Xml de Distribuição da Carta de Correção | C |  |  | N |
| XMLENVCLIECONF | XMLENVCLICONF | C |  |  | N |
| XMLENVCLIPRORROG | XMLENVCLIPRORROG | C |  |  | N |
| XMLENVECONF | Xml de envio do Evento de Conciliação Financeira | C |  |  | N |
| XMLENVEPEC | XMLENVEPEC | C |  |  | N |
| XMLENVPRORROG | Xml de envio do Evento de Prorrogação de Prazo de Suspensão do Icms | C |  |  | N |
| XMLPROTAUTCANCCONF | Xml de Autorização do Cancelamento do Evento de Conciliação Financeira | C |  |  | N |
| XMLPROTAUTCANCPRORROG | Xml de Autorização do Cancelamento do Evento de Prorrogação de Prazo de Suspensão do Icms | C |  |  | N |
| XMLPROTAUTCARTA | Xml de Autorização de Carta de Correção | C |  |  | N |
| XMLPROTAUTECONF | Xml de Autorização do Evento de Conciliação Financeira | C |  |  | N |
| XMLPROTAUTEPEC | XMLPROTAUTEPEC | C |  |  | N |
| XMLPROTAUTNOT | XMLPROTAUTNOT | C |  |  | N |
| XMLPROTAUTPRORROG | Xml de Autorização do Evento de Prorrogação de Prazo de Suspensão do Icms | C |  |  | N |
| XMLPROTCANC | XMLPROTCANC | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ArquivoNFe / [TGFNFE](TGFNFE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ArquivoNFe / [TGFNFE](TGFNFE.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
