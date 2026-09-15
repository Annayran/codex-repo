# TGFSTMVPRO — Produtos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BaseStMediaVendaPro | BaseStMediaVendaPro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód. Empresa | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| CODVOL | Unidade Padrão | S |  |  | N |
| NROUNICO | Nro. Único | I |  |  | N |
| QTDMES | Quantidade | F |  |  | N |
| REFERENCIA | Referência | D |  |  | N |
| TIPOBUSCA | Tipo de Busca | I |  |  | N |
| VLRMEDIOMES | Valor médio | F |  |  | N |
| VLRMES | Valor Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BaseStMediaVendaPro / [TGFSTMVPRO](TGFSTMVPRO.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| BaseStMediaVendaPro / [TGFSTMVPRO](TGFSTMVPRO.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| BaseStMediaVendaPro / [TGFSTMVPRO](TGFSTMVPRO.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| BaseStMediaVendaProDet / [TGFSTMVPRODET](TGFSTMVPRODET.md) | BaseStMediaVendaPro / [TGFSTMVPRO](TGFSTMVPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
