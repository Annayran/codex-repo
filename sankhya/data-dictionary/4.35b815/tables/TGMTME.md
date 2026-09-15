# TGMTME — Tipos de Metas

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoMeta | TipoMeta | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMETA | Meta | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| COMPROMISSO | Compromisso | S |  |  | N |
| DTALTER | Dt. Alteração | H |  |  | N |
| RECDESP | Receita/Despesa | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | TipoMeta / [TGMTME](TGMTME.md) | CODMETA->CODMETA | I |
| TipoMeta / [TGMTME](TGMTME.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
