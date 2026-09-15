# TCSCOM — Comissões

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Comissao | Comissões | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| NUMCONTRATO | Número do Contrato | I |  |  | N |
| PERCCOM | Percentual de Comissão | F |  |  | N |
| VLRCOMISSAO | Valor da Comissão | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Comissao / [TCSCOM](TCSCOM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Comissao / [TCSCOM](TCSCOM.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Contrato / [TCSCON](TCSCON.md) | Comissao / [TCSCOM](TCSCOM.md) | NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
