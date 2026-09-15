# TGFPFX — Cabeçalho de IRPF/INSS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoTabelaIrpfInss | Cabeçalho da tabela de impostos IRPF e INSS | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| COMPETENCIA | Competência | D |  |  | N |
| CONSDESCSIMPIRPF | Considerar desconto simplificado para cálculo do IRPF? | S |  |  | N |
| REGIME | Regime de apuração | S |  |  | N |
| USACALCINSSPROG | Utilizar cálculo INSS cumulativo | S |  |  | N |
| USAPARCINSSFUNRURAL | Considerar FUNRURAL/INSS Parceiro? | S |  |  | N |
| VLRDEPENDENTE | Valor dependente (IRPF) | F |  |  | N |
| VLRMAXIMO | Valor máximo INSS | F |  |  | N |
| VLRMINIMO | Valor mínimo INSS | F |  |  | N |
| VLRMINIMOIRPF | Valor mínimo IRPF | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoTabelaIrpfInss / [TGFPFX](TGFPFX.md) | ParceiroTabelaProgressiva / [TGFPTP](TGFPTP.md) | COMPETENCIA->COMPETENCIA | I |
| CabecalhoTabelaIrpfInss / [TGFPFX](TGFPFX.md) | TabelaINSS / [TGFFAI](TGFFAI.md) | COMPETENCIA->COMPETENCIA | I |
| CabecalhoTabelaIrpfInss / [TGFPFX](TGFPFX.md) | TabelaIRPF / [TGFFAI](TGFFAI.md) | COMPETENCIA->COMPETENCIA | I |
| CabecalhoTabelaIrpfInss / [TGFPFX](TGFPFX.md) | TabelaIRPFDedAdicional / [TGFFAI](TGFFAI.md) | COMPETENCIA->COMPETENCIA | I |
| InssReferencia / [TGFINSSREF](TGFINSSREF.md) | CabecalhoTabelaIrpfInss / [TGFPFX](TGFPFX.md) | COMPETENCIA->COMPETENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
