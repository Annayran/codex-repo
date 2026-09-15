# TGMUSLB — Liberação Planejamento

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UsuarioLiberadorMeta | Liberação Planejamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMETA | Código | I |  |  | N |
| CODUSU | Código | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | UsuarioLiberadorMeta / [TGMUSLB](TGMUSLB.md) | CODMETA->CODMETA | I |
| UsuarioLiberadorMeta / [TGMUSLB](TGMUSLB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
