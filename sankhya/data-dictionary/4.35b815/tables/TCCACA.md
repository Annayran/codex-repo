# TCCACA — Acabamento CC

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcabamentoCivil | Acabamento Unidades Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCC | CODCC | I |  |  | N |
| CODPROD | Residencial | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| TIPO | Acabamento Diferenciado | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcabamentoCivil / [TCCACA](TCCACA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ContratoCivil / [TCCCON](TCCCON.md) | AcabamentoCivil / [TCCACA](TCCACA.md) | CODCC->CODCC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
