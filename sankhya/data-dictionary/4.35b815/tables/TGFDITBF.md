# TGFDITBF — Importação tributos detalhes de PIS/COFINS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalheImportacaoTributoFed | Detalhe Integração Tributo Federal | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQCOFINS | Alíquota COFINS | F |  |  | N |
| ALIQPIS | Alíquota PIS | F |  |  | N |
| CODEMP | Código da Empresa | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CSTENT | CST Entrada | I |  |  | N |
| CSTSAI | CST Saída | I |  |  | N |
| DHIMPORT | Data | H |  |  | N |
| NCM | NCM | S |  |  | N |
| NRI | Natureza de Receita Isenta | S |  |  | N |
| STATUSIMP | Status importação | S |  |  | N |
| TIPOIMP | Tipo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheImportacaoTributo / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoFed / [TGFDITBF](TGFDITBF.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |
| DetalheImportacaoTributoFed / [TGFDITBF](TGFDITBF.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ImportacaoTributoFederal / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoFed / [TGFDITBF](TGFDITBF.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
