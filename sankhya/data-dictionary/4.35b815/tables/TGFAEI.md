# TGFAEI — Alíquota Especial de ICMS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AliquotaEspecialICMS | Alíquota Especial de ICMS | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAEI | Cód. Alíquota | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| DTFIN | Dt. Fim Vigor | H |  |  | N |
| DTINI | Dt. Início Vigor | H |  |  | N |
| VLRALIQ | Vlr. Alíquota | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaEspecialICMS / [TGFAEI](TGFAEI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AliquotaEspecialICMS / [TGFAEI](TGFAEI.md) | ParceiroAliquotaEspecialICMS / [TGFAEPAR](TGFAEPAR.md) | CODAEI->CODAEI | I |
| AliquotaEspecialICMS / [TGFAEI](TGFAEI.md) | ProdutoAliquotaEspecialICMS / [TGFAEPRO](TGFAEPRO.md) | CODAEI->CODAEI | I |
| AliquotaEspecialICMS / [TGFAEI](TGFAEI.md) | TopAliquotaEspecialICMS / [TGFAETOP](TGFAETOP.md) | CODAEI->CODAEI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
