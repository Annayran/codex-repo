# TGFPMPE — Preço Médio Produto Empresa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrecoMedioProdutoEmpresa | Preço Médio Produto Empresa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód. Empresa | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| DTREF | Dt. Referência | D |  |  | N |
| VLRUNITMEDIO | Valor unitário médio | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrecoMedioProdutoEmpresa / [TGFPMPE](TGFPMPE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PrecoMedioProdutoEmpresa / [TGFPMPE](TGFPMPE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
