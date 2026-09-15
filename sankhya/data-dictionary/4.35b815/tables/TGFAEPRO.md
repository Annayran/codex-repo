# TGFAEPRO — Exceções por Produto

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
| ProdutoAliquotaEspecialICMS | Exceções por Produto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAEI | Cód. Alíquota | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaEspecialICMS / [TGFAEI](TGFAEI.md) | ProdutoAliquotaEspecialICMS / [TGFAEPRO](TGFAEPRO.md) | CODAEI->CODAEI | I |
| ProdutoAliquotaEspecialICMS / [TGFAEPRO](TGFAEPRO.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
