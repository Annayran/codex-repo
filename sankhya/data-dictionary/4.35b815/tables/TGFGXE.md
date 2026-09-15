# TGFGXE — Relação Genérico Específico

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RelacaoGenericoEspecifico | Relação Genérico Específico | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto genérico | I |  |  | N |
| CODPRODESP | Produto específico | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | RelacaoGenericoEspecifico / [TGFGXE](TGFGXE.md) | CODPROD->CODPROD | I |
| RelacaoGenericoEspecifico / [TGFGXE](TGFGXE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPRODESP->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
