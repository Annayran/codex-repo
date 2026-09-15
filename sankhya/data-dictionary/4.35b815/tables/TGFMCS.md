# TGFMCS — Múltiplos Componentes de Serviço

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MultiplosComponentesServico | Multiplos Componentes de Servico | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCOMP | Cód. Componente | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| TIPCOMP | Tipo de Componente | I |  |  | N |
| VLRPADRAO | Vlr. Padrão | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MultiplosComponentesServico / [TGFMCS](TGFMCS.md) | ComponenteServico / [TGFCSV](TGFCSV.md) | CODCOMP->CODCOMP | I |
| MultiplosComponentesServico / [TGFMCS](TGFMCS.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | MultiplosComponentesServico / [TGFMCS](TGFMCS.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
