# TGADPR — Diagnósticos agrícola

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DiagnosticosProdutos | Diagnósticos para produtos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Cód.Produto | I |  |  | N |
| NUDIG | Diagnóstico | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DiagnosticosProdutos / [TGADPR](TGADPR.md) | DiagnosticoAgricolaProduto / [TGADIG](TGADIG.md) | NUDIG->NUDIG | I |
| DiagnosticosProdutos / [TGADPR](TGADPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
