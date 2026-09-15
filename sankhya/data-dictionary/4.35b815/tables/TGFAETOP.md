# TGFAETOP — Exceções por TOP

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
| TopAliquotaEspecialICMS | Exceções por TOP | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAEI | Cód. Alíquota | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaEspecialICMS / [TGFAEI](TGFAEI.md) | TopAliquotaEspecialICMS / [TGFAETOP](TGFAETOP.md) | CODAEI->CODAEI | I |
| TopAliquotaEspecialICMS / [TGFAETOP](TGFAETOP.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
