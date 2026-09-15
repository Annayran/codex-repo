# TGFPPP — Produto por peça

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
| ProdutoPeca | Produto por peça | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPECA | Peça | I |  |  | N |
| CODPROD | Produto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProdutoPeca / [TGFPPP](TGFPPP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RegistroPecas / [TGFRPC](TGFRPC.md) | ProdutoPeca / [TGFPPP](TGFPPP.md) | CODPECA->CODPECA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
