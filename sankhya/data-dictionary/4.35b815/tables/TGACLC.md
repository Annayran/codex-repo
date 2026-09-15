# TGACLC — Classificação Produto Característica Analisável

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CaracteristicaAnalisavel | Característica Analisável | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALISETERC | Análise de Terceiros/REPORT SUPPLIER | S |  |  | N |
| CARACTCONFORME | Característica Conforme - Não Conforme | S |  |  | N |
| CODCLC | Código | I |  |  | N |
| CODGRUPOCLC | Grupo | I |  |  | N |
| CODVINC | Vinculado a | I |  |  | N |
| DECQTD | Qtd. Decimais | I |  |  | N |
| EXIGLIB | Exibe Liberação? | S |  |  | N |
| NOMECLC | Descrição | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| SIGLA | Sigla | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CaracteristicaAnalisavelUsu / [TGFCAU](TGFCAU.md) | CODCLC->CODCLC | I |
| CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CaracteristicaClassificacao / [TGACLI](TGACLI.md) | CODCLC->CODCLC | I |
| CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | GrupoCaracteristicaAnalisavel / [TGAGLC](TGAGLC.md) | CODGRUPOCLC->CODGRUPOCLC | I |
| CaracteristicaClassificacao / [TGACLI](TGACLI.md) | CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CODCLC->CODCLC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CODCLC->CODCLC | I |
| ItemLaudo / [TGACLR](TGACLR.md) | CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CODCLC->CODCLC | I |
| PrecoUmidadeArm / [TGATDC](TGATDC.md) | CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CODCLC->CODCLC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
