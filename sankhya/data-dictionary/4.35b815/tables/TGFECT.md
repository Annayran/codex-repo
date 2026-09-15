# TGFECT — Serviço Correios

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ServicoCorreios | Serviço Correios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSERVICO | Cód. Serviço | S |  |  | N |
| DESCRSERVICO | Serviço | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TabelaCalculoFrete / [TGFCFR](TGFCFR.md) | ServicoCorreios / [TGFECT](TGFECT.md) | SERVICOECT->CODSERVICO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
