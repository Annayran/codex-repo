# TCSMPR — Insumos dos modelos de etapas

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InsumoModeloEtapa | Insumo do Modelo da Etapa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCAL | Local | I |  |  | N |
| CODMETOD | Cód. Método | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVOL | Volume | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| NUMMODELO | Modelo | I |  |  | N |
| QTDNEG | Qtde. Negociada | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| InsumoModeloEtapa / [TCSMPR](TCSMPR.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| InsumoModeloEtapa / [TCSMPR](TCSMPR.md) | ModeloEtapa / [TCSMOD](TCSMOD.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |
| InsumoModeloEtapa / [TCSMPR](TCSMPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| InsumoModeloEtapa / [TCSMPR](TCSMPR.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ModeloEtapa / [TCSMOD](TCSMOD.md) | InsumoModeloEtapa / [TCSMPR](TCSMPR.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
