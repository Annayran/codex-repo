# TGFMFCI — Controle FCI

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleFCI | Controle FCI | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo? | S |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODFCI | Código da FCI | S |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleFCI / [TGFMFCI](TGFMFCI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ControleFCI / [TGFMFCI](TGFMFCI.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ControleFCI / [TGFMFCI](TGFMFCI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
