# TGFGRA — Grade de Produtos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GradeProduto | Perfil de Consumo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODTIPPARC | Perfil do parceiro | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GradeProduto / [TGFGRA](TGFGRA.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| GradeProduto / [TGFGRA](TGFGRA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Perfil / [TGFTPP](TGFTPP.md) | GradeProduto / [TGFGRA](TGFGRA.md) | CODTIPPARC->CODTIPPARC | I |
| Produto / [TGFPRO](TGFPRO.md) | GradeProduto / [TGFGRA](TGFGRA.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | GradeProduto / [TGFGRA](TGFGRA.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
