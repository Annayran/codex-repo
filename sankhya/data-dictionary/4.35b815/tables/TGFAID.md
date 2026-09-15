# TGFAID — Alíquota Interna Destino do Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AliquotaInternaDestino | Alíquota Interna de Destino | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQINTDEST | Alíq. Interna Destino | F |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUF | UF | I |  |  | N |
| PERCICMSFCP | Perc. ICMS Fundo Comb. Pobreza | F |  |  | N |
| PERCREDBASEDEST | Perc. Red. Base Destino | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaInternaDestino / [TGFAID](TGFAID.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaInternaDestino / [TGFAID](TGFAID.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
