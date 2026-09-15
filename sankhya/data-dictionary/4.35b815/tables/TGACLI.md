# TGACLI — Ítens do Padrão Classificação Produto

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CaracteristicaClassificacao | Características Classificação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLC | Característica | I |  |  | N |
| CODCLT | Padrão Classificação | I |  |  | N |
| DESCONTAR | Desconto | F |  |  | N |
| FORMULADESC | Fórmula Desconto | S |  |  | N |
| MAXIMO | Máximo | F |  |  | N |
| MINIMO | Mínimo | F |  |  | N |
| OBRIGATORIO | Obrigatório | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| USAINDICE | Usa Índices | S |  |  | N |
| USAINTERVALO | Usa Intervalos | S |  |  | N |
| VLRCALCINDICE | Vlr. Base p/ Cálculo por Índices | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CaracteristicaClassificacao / [TGACLI](TGACLI.md) | CODCLC->CODCLC | I |
| CaracteristicaClassificacao / [TGACLI](TGACLI.md) | CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CODCLC->CODCLC | I |
| CaracteristicaClassificacao / [TGACLI](TGACLI.md) | PadCarIntervalo / [TGAPCI](TGAPCI.md) | CODCLC->CODCLC; CODCLT->CODCLT | I |
| CaracteristicaClassificacao / [TGACLI](TGACLI.md) | PadraoClassificacao / [TGACLT](TGACLT.md) | CODCLT->CODCLT | I |
| ItemLaudo / [TGACLR](TGACLR.md) | CaracteristicaClassificacao / [TGACLI](TGACLI.md) | CODCLC->CODCLC; CODCLT->CODCLT | I |
| PadCarIntervalo / [TGAPCI](TGAPCI.md) | CaracteristicaClassificacao / [TGACLI](TGACLI.md) | CODCLC->CODCLC; CODCLT->CODCLT | I |
| PadraoClassificacao / [TGACLT](TGACLT.md) | CaracteristicaClassificacao / [TGACLI](TGACLI.md) | CODCLT->CODCLT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
