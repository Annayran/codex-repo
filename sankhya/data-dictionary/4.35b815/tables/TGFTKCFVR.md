# TGFTKCFVR — Configurações Consulta Indexada Venda Rápida

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoTokenProdutoVR | Configuracões Consulta Indexada Venda Rápida | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODCFG | Código | I |  |  | N |
| DHULTEXEC | Dh. última execução | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoTokenProdutoVR / [TGFTKCFVR](TGFTKCFVR.md) | CamposTokenProdutoVR / [TGFTKCAVR](TGFTKCAVR.md) | CODCFG->CODCFG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
