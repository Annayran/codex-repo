# TGFFAI — Tabela IRPF/ INSS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 3
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TabelaINSS | Tabela INSS | S | N |
| TabelaIRPF | Tabela IRPF | S | N |
| TabelaIRPFDedAdicional | Tabela Dedução Adicional de IRRF | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQUOTA | Alíquota | F |  |  | N |
| COEFICIENTE | Coeficiente | F |  |  | N |
| COMPETENCIA | Competência | D |  |  | N |
| TIPTABELA | Tipo de Tabela | S |  |  | N |
| VLRDEDUCAO | Valor da dedução | F |  |  | N |
| VLRLIMITE | Limite | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoTabelaIrpfInss / [TGFPFX](TGFPFX.md) | TabelaINSS / [TGFFAI](TGFFAI.md) | COMPETENCIA->COMPETENCIA | I |
| CabecalhoTabelaIrpfInss / [TGFPFX](TGFPFX.md) | TabelaIRPF / [TGFFAI](TGFFAI.md) | COMPETENCIA->COMPETENCIA | I |
| CabecalhoTabelaIrpfInss / [TGFPFX](TGFPFX.md) | TabelaIRPFDedAdicional / [TGFFAI](TGFFAI.md) | COMPETENCIA->COMPETENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
