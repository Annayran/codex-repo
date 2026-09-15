# TCSPPP — PerfilProspect

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PerfilProspect | Perfil Prospect | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPAP | CODPAP | I |  |  | N |
| CODTIPPARC | Cód. Perfil | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ParceiroProspect / [TCSPAP](TCSPAP.md) | PerfilProspect / [TCSPPP](TCSPPP.md) | CODPAP->CODPAP | I |
| PerfilProspect / [TCSPPP](TCSPPP.md) | ParceiroProspect / [TCSPAP](TCSPAP.md) | CODPAP->CODPAP | I |
| PerfilProspect / [TCSPPP](TCSPPP.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
