# TGFROM — Romaneio Carga

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RomaneioCarga | Romaneio de Carga | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMPOC | Empresa Ordem Carga | I |  |  | N |
| NUNOTA | Nota faturamento | I |  |  | N |
| NUNOTAOP | Nota Ordem de Produção | I |  |  | N |
| NUPLAN | Num. Planejamento | I |  |  | N |
| OBSNOTA | Observação | S |  |  | N |
| ORDEMCARGA | Ordem Carga | I |  |  | N |
| QTDPROD | Quantidade Produzida | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OrdemCarga / [TGFORD](TGFORD.md) | RomaneioCarga / [TGFROM](TGFROM.md) | CODEMP->CODEMPOC; ORDEMCARGA->ORDEMCARGA | I |
| RomaneioCarga / [TGFROM](TGFROM.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAOP->NUNOTA | I |
| RomaneioCarga / [TGFROM](TGFROM.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMPOC->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| RomaneioCarga / [TGFROM](TGFROM.md) | PlanejamentoProducao / [TGFPPL](TGFPPL.md) | NUPLAN->NUPLAN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
