# TGFGVA — TGFGVA

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 17
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ApuracaoProPreAcordoVerba | ApuracaoProPreAcordoVerba | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVENFE | Chave NFE | S |  |  | N |
| CODEMP | Código da Empresa | I |  |  | N |
| CODPARC | Codigo do Parceiro | I |  |  | N |
| CODPROD | Código do Produto | I |  |  | N |
| DTENTSAI | Data de Entrada e Saída | H |  |  | N |
| DTNEG | Data da Negociação | H |  |  | N |
| NUAPURACAO | Código Apuração | I |  |  | N |
| NUCRITERIO | Código Criterio | I |  |  | N |
| NUNOTA | Número da Nota | I |  |  | N |
| NUPREACORDO | Nro. Acordo | I |  |  | N |
| QTDNEG | Quantidade | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| UNIDADE | Unidade | S |  |  | N |
| VLRDESC | Valor do Desconto | F |  |  | N |
| VLRNOTA | Valor da Nota | F |  |  | N |
| VLRTOT | Valor Total | F |  |  | N |
| VLRUNIT | Valor Unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | ApuracaoPreAcordoVerba / [TGFGVE](TGFGVE.md) | NUAPURACAO->NUAPURACAO | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | CHAVENFE->CHAVENFE; DTENTSAI->DTENTSAI; DTNEG->DTNEG; VLRNOTA->VLRNOTA | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | NUCRITERIO->NUCRITERIO | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA; VLRDESC->VLRDESC; VLRUNIT->VLRUNIT | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | ParceiroFornecedor / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | PreAcordoVerba / [TGFGVP](TGFGVP.md) | NUPREACORDO->NUPREACORDO | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | Volume / [TGFVOL](TGFVOL.md) | UNIDADE->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
