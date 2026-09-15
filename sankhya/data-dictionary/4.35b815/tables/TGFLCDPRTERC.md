# TGFLCDPRTERC — Cadastro de Terceiros

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TerceiroProducaoRural | Cadastro de Terceiros | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODPARC | Parceiro Vinculado | I |  |  | N |
| PERCEXPLORACAO | Percentual de Exploração | F |  |  | N |
| TIPOEXPLORACAO | Tipo de Exploração | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TerceiroProducaoRural / [TGFLCDPRTERC](TGFLCDPRTERC.md) | CODEMP->CODEMP | I |
| TerceiroProducaoRural / [TGFLCDPRTERC](TGFLCDPRTERC.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| TerceiroProducaoRural / [TGFLCDPRTERC](TGFLCDPRTERC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
