# TGFSTMVE — Empresas

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
| BaseStMediaVendaEmp | BaseStMediaVendaEmp | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód. Empresa | I |  |  | N |
| NROUNICO | Nro. Único | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BaseStMediaVenda / [TGFSTMV](TGFSTMV.md) | BaseStMediaVendaEmp / [TGFSTMVE](TGFSTMVE.md) | NROUNICO->NROUNICO | I |
| BaseStMediaVendaEmp / [TGFSTMVE](TGFSTMVE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
