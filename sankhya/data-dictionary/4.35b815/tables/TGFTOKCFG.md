# TGFTOKCFG — Configurações Consulta Indexada

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoTokenProduto | Configuracões Consulta Indexada | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| ATIVOCRV | Ativo Central Venda Rápida | S |  |  | N |
| CODCFG | Código | I |  |  | N |
| DHULTEXEC | Dh. última execução | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoTokenProduto / [TGFTOKCFG](TGFTOKCFG.md) | CamposTokenProduto / [TGFTOKCAM](TGFTOKCAM.md) | CODCFG->CODCFG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
