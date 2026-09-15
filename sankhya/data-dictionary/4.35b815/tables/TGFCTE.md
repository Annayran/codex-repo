# TGFCTE — GF Contagem de Estoque

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 17
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContagemEstoque | Contagem de Estoque | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHCONFBOMI | DHCONFBOMI | H |  |  | N |
| DTCONTAGEM | Data contagem | D |  |  | N |
| DTFABRICACAO | Dt. Fabricação | D |  |  | N |
| DTVAL | Dt. Validade | D |  |  | N |
| ERROCONFBOMI | ERROCONFBOMI | S |  |  | N |
| NOMEARQBOMI | NOMEARQBOMI | S |  |  | N |
| NUIVT | Inventário WMS | I |  |  | N |
| QTDEST | Estoque | F |  |  | N |
| QTDESTUNCAD | Qtd. Unidade Padrão | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContagemEstoque / [TGFCTE](TGFCTE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContagemEstoque / [TGFCTE](TGFCTE.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ContagemEstoque / [TGFCTE](TGFCTE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ContagemEstoque / [TGFCTE](TGFCTE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ContagemEstoque / [TGFCTE](TGFCTE.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
