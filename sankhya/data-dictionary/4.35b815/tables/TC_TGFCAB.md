# TC_TGFCAB — Eixos Estratégicos

- Módulo técnico: [TC_](../modules/TC_.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CarimboCabecalhoNota | Controle CabecalhoNota | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APPKEY | Appkey | S |  |  | N |
| DHALTER | Data de inclusão | H |  |  | N |
| HASHLINHA | Hash | S |  |  | N |
| IDPERSONALIZACAO | Id Personalização | S |  |  | N |
| INTEGRADO | Integrado | S |  |  | N |
| NUCHAVE | Nro Nota | I |  |  | N |
| ORIGEM | Origem | S |  |  | S |
| STATUS | Status | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CarimboCabecalhoNota / [TC_TGFCAB](TC_TGFCAB.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUCHAVE->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
