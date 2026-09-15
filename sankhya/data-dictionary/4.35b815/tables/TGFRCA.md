# TGFRCA — TABLE TGFRCA

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RastreabilidadeContratoAgrupa | RastreabilidadeContratoAgrupa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DTREFERENCIA | Data de Referência | H |  |  | N |
| NUMCONTRATO | Nro. Contrato | I |  |  | N |
| NUNOTA | Nro. Nota | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RastreabilidadeContratoAgrupa / [TGFRCA](TGFRCA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| RastreabilidadeContratoAgrupa / [TGFRCA](TGFRCA.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
