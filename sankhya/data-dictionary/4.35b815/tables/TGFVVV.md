# TGFVVV — Vínculo de Verba

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VinculoVerbaBonificacao | Vínculo de verba com nota bonificada | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUVERBA | Nro. Verba | I |  |  | N |
| NUVINCULO | Nro. Vinculo | I |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| VinculoVerbaBonificacao / [TGFVVV](TGFVVV.md) | Verbas / [TGFGEV](TGFGEV.md) | NUVERBA->NUVERBA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
