# TGAPCI — Faixas de Desconto

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PadCarIntervalo | Intervalo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLC | Característica | I |  |  | N |
| CODCLT | Padrão Classificação | I |  |  | N |
| CODINT | Codigo Intervalo | I |  |  | N |
| EXIBLIB | Exibe Liberação | S |  |  | N |
| INDICE | Índice | F |  |  | N |
| VALDESC | % Descontar | F |  |  | N |
| VALMAX | Máximo | F |  |  | N |
| VALMIN | Minimo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CaracteristicaClassificacao / [TGACLI](TGACLI.md) | PadCarIntervalo / [TGAPCI](TGAPCI.md) | CODCLC->CODCLC; CODCLT->CODCLT | I |
| PadCarIntervalo / [TGAPCI](TGAPCI.md) | CaracteristicaClassificacao / [TGACLI](TGACLI.md) | CODCLC->CODCLC; CODCLT->CODCLT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
