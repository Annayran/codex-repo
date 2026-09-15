# TCCITE — Item CC

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemContratoCivil | Itens Contrato Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCC | Contrato | I |  |  | N |
| CODPROD | Unidade | I |  |  | N |
| VLRNEG | Valor Negociado | F |  |  | N |
| VLRTAB | Preço Tabela | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoCivil / [TCCCON](TCCCON.md) | ItemContratoCivil / [TCCITE](TCCITE.md) | CODCC->CODCC | I |
| ItemContratoCivil / [TCCITE](TCCITE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
