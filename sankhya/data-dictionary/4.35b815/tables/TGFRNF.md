# TGFRNF — Complemento Financeiro Reinf

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CompFinancReinf | Complemento Financeiro Reinf | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODDEDIRPF | Tipo Dedução | I |  |  | N |
| CODNATREND | Código Natureza Rendimento | I |  |  | N |
| DESCISENCAO | Descrição | S |  |  | N |
| DTESCRCONT | Dt. Escrituração Contábil. | D |  |  | N |
| DTLAUDO | Data do Laudo | D |  |  | N |
| IRRFISENTO | IRRF Isento? | S |  |  | N |
| NUFIN | Nro Único | I |  |  | N |
| SEMIREXT | Sem IR Retido Exterior | S |  |  | N |
| TEMDEDIRPF | Tem Dedução Base IRPF? | S |  |  | N |
| TPISENCAO | Tipo de Isenção | S |  |  | N |
| TRIBIRRFEXT | Tributação IRRF - Exterior REINF | I |  |  | N |
| VLRDEDBASEIRPF | Valor de Dedução Base IRPF | F |  |  | N |
| VLRISENCAOIDADE | Valor de isenção | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CompFinancReinf / [TGFRNF](TGFRNF.md) | CadastroNaturezaRendimentos /  | CODNATREND->CODNATREND | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CompFinancReinf / [TGFRNF](TGFRNF.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
