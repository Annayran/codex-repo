# TGFGTV — Guia de Transporte de Valores

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GuiaTransporteValores | Guia de Transporte de Valores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCEVENTO | Descrição do Evento | S |  |  | N |
| NUNOTA | Nro. Nota | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GuiaTransporteValores / [TGFGTV](TGFGTV.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| GuiaTransporteValores / [TGFGTV](TGFGTV.md) | InformacoesGTV / [TGFIGTV](TGFIGTV.md) | NUNOTA->NUNOTA | I |
| InformacoesGTV / [TGFIGTV](TGFIGTV.md) | GuiaTransporteValores / [TGFGTV](TGFGTV.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
