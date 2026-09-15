# TGFPTP — Parceiros sujeitos a tabela progressiva

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
| ParceiroTabelaProgressiva | Parceiros sujeitos a tabela progressiva | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Serviço | I |  |  | N |
| COMPETENCIA | Competência | D |  |  | N |
| DEPENDENTE | Número de Dependentes | I |  |  | N |
| RETEMINSSORIGFIN | Retém INSS em documentos de origem financeira? | S |  |  | N |
| TEM13 | Tem 13º ? | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoTabelaIrpfInss / [TGFPFX](TGFPFX.md) | ParceiroTabelaProgressiva / [TGFPTP](TGFPTP.md) | COMPETENCIA->COMPETENCIA | I |
| ParceiroTabelaProgressiva / [TGFPTP](TGFPTP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroTabelaProgressiva / [TGFPTP](TGFPTP.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
