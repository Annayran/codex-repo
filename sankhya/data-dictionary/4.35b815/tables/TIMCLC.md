# TIMCLC — TimCompradorVenda

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimCompradorVenda | Compradores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLCCOMPRADOR | Comprador | I |  |  | N |
| CLCCONTRATO | Contrato | I |  |  | N |
| CLCESTAGIO | Ativo | S |  |  | N |
| CLCPERCENTUALDACOMPRA | Percentual da Compra | F |  |  | N |
| CLCRESPBOL | Responsável pelo Bolelo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimCompradorVenda / [TIMCLC](TIMCLC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CLCCOMPRADOR->CODPARC | I |
| TimCompradorVenda / [TIMCLC](TIMCLC.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | CLCCONTRATO->CLACODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimCompradorVenda / [TIMCLC](TIMCLC.md) | CLACODIGO->CLCCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
