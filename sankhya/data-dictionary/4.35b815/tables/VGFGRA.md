# VGFGRA — VIEW VGFGRA

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewPerfilGradeProduto | Produtos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODTIPPARC | Perfil | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Perfil / [TGFTPP](TGFTPP.md) | ViewPerfilGradeProduto / [VGFGRA](VGFGRA.md) | CODTIPPARC->CODTIPPARC | I |
| ViewPerfilGradeProduto / [VGFGRA](VGFGRA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
