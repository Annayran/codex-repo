# TGACLR — Resultado por Ítem Classificação Produto

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemLaudo | Item de laudo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APROVADORESULTADO | Aprovado? | S |  |  | S |
| CODCLC | Cód. Característica | I |  |  | N |
| CODCLT | Cód.Classificação | I |  |  | N |
| DESCONTAR | Descontar | F |  |  | N |
| NUCLL | Nro. Laudo | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| ORDEM | Ordem | I |  |  | S |
| RESULTADO | Resultado | F |  |  | N |
| RESULTADOCONF | Avaliação da Conformidade | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemLaudo / [TGACLR](TGACLR.md) | CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CODCLC->CODCLC | I |
| ItemLaudo / [TGACLR](TGACLR.md) | CaracteristicaClassificacao / [TGACLI](TGACLI.md) | CODCLC->CODCLC; CODCLT->CODCLT | I |
| ItemLaudo / [TGACLR](TGACLR.md) | LaudoClassificacao / [TGACLL](TGACLL.md) | NUCLL->NUCLL | I |
| ItemLaudo / [TGACLR](TGACLR.md) | PadraoClassificacao / [TGACLT](TGACLT.md) | CODCLT->CODCLT | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | ItemLaudo / [TGACLR](TGACLR.md) | NUCLL->NUCLL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
