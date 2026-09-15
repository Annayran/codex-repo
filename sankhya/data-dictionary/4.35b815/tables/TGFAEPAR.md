# TGFAEPAR — Exceções por Parceiro

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
| ParceiroAliquotaEspecialICMS | Exceções Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAEI | Cód. Alíquota | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaEspecialICMS / [TGFAEI](TGFAEI.md) | ParceiroAliquotaEspecialICMS / [TGFAEPAR](TGFAEPAR.md) | CODAEI->CODAEI | I |
| ParceiroAliquotaEspecialICMS / [TGFAEPAR](TGFAEPAR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
