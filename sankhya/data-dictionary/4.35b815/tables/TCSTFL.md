# TCSTFL — Transições por fluxo de processo

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TransicaoPorFluxoProcesso | Transições por fluxo de processo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Cód. Produto | I |  |  | N |
| CODSERVDEST | Cód. Serviço de destino | I |  |  | N |
| CODSERVORIG | Cód. Serviço de origem | I |  |  | N |
| SEQUENCIADEST | Sequência de destino | I |  |  | N |
| SEQUENCIAORIG | Sequencia de origem | I |  |  | N |
| VARIACAO | Variação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TransicaoPorFluxoProcesso / [TCSTFL](TCSTFL.md) | Produto / [TGFPRO](TGFPRO.md) | CODSERVORIG->CODPROD | I |
| TransicaoPorFluxoProcesso / [TCSTFL](TCSTFL.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERVDEST->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
