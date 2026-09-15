# TGFIRAST — Item Rastreamento de Estoque

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemRastreamentoEstoque | Item Rastreamento de Estoque | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód.Empresa | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| NURAST | Código | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemRastreamentoEstoque / [TGFIRAST](TGFIRAST.md) | CabecalhoRastreamentoEstoque / [TGFCRAST](TGFCRAST.md) | NURAST->NURAST | I |
| ItemRastreamentoEstoque / [TGFIRAST](TGFIRAST.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ItemRastreamentoEstoque / [TGFIRAST](TGFIRAST.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
