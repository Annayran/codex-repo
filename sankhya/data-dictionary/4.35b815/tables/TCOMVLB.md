# TCOMVLB — Vínculos de Locação de Bens Móveis

- Módulo técnico: [TCO](../modules/TCO.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VinculoLocacaoBemMovel | Vinculos de Locacao de Bens Moveis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Código Produto/Bem | I |  |  | N |
| DESCRICAO | Descrição do Bem | S |  |  | N |
| IDVLB | ID Vínculo de Locação | I |  |  | N |
| NCM | Código NCM | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| QUANTIDADE | Quantidade | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| VinculoLocacaoBemMovel / [TCOMVLB](TCOMVLB.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| VinculoLocacaoBemMovel / [TCOMVLB](TCOMVLB.md) | Ncm / [TGFNCM](TGFNCM.md) | NCM->CODNCM | I |
| VinculoLocacaoBemMovel / [TCOMVLB](TCOMVLB.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
