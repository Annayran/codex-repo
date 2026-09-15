# TGFNCTE — Arquivos XML de CTe

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ArquivoCTe | Arquivo CT-e | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVECTE | Chave CT-e | S |  |  | N |
| NUNOTA | Nro. Único da Nota | I |  |  | N |
| QRCODE | QRCODE | S |  |  | N |
| XML | XML | C |  |  | N |
| XMLCANC | XMLCANC | C |  |  | N |
| XMLCANCENVCLI | XMLCANCENVCLI | C |  |  | N |
| XMLCANCPROTAUT | XMLCANCPROTAUT | C |  |  | N |
| XMLENVCARTA | XMLENVCARTA | C |  |  | N |
| XMLENVCARTENVCLI | XMLENVCARTENVCLI | C |  |  | N |
| XMLENVCARTROTAUT | XMLENVCARTROTAUT | C |  |  | N |
| XMLENVCLI | XMLENVCLI | C |  |  | N |
| XMLENVCLICANC | XMLENVCLICANC | C |  |  | N |
| XMLENVEPEC | XMLENVEPEC | C |  |  | N |
| XMLPROTAUTCTE | XMLPROTAUTCTE | C |  |  | N |
| XMLPROTAUTEPEC | XMLPROTAUTEPEC | C |  |  | N |
| XMLPROTCANC | XMLPROTCANC | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ArquivoCTe / [TGFNCTE](TGFNCTE.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
