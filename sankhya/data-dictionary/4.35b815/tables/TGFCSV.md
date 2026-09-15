# TGFCSV — Componentes de Serviços

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ComponenteServico | Componente de Serviço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCOMP | Cód. Componente | I |  |  | N |
| DESCRCOMP | Descrição do Componente de Serviço | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MultiplosComponentesServico / [TGFMCS](TGFMCS.md) | ComponenteServico / [TGFCSV](TGFCSV.md) | CODCOMP->CODCOMP | I |
| Servico / [TGFPRO](TGFPRO.md) | ComponenteServico / [TGFCSV](TGFCSV.md) | CODCOMP->CODCOMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
