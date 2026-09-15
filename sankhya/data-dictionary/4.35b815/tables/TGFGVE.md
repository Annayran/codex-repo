# TGFGVE — Apuração Pré Acordo Verba

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ApuracaoPreAcordoVerba | ApuracaoPreAcordoVerba | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| METAATINGIDA | Faixa Atingida | F |  |  | N |
| NUAPURACAO | Nro. Apuração | I |  |  | N |
| NUCRITERIO | Critério | I |  |  | N |
| NUPREACORDO | Nro. Acordo | I |  |  | N |
| PERCPROXMETA | % Próxima Faixa | F |  |  | N |
| PROXMETA | Próxima Faixa | F |  |  | N |
| TIPOCRITERIO | Tipo de Critério | S |  |  | N |
| TIPOMETA | Tipo de Meta | S |  |  | N |
| VERBAAPURADA | Verba Apurada | F |  |  | N |
| VLRAPURADO | Valor Apurado | F |  |  | N |
| VLRQTDMOV | Qtd. Movimento | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApuracaoPreAcordoVerba / [TGFGVE](TGFGVE.md) | CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | NUCRITERIO->NUCRITERIO | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | ApuracaoPreAcordoVerba / [TGFGVE](TGFGVE.md) | NUAPURACAO->NUAPURACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
