# TGMSUB — Campos Substitutos

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CampoSubstituto | Campo Substituto | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPOSUBSTITUTO | Campos Substitutos | S |  |  | N |
| CODCAMPO | Campos Disponíveis | S |  |  | N |
| CODMETA | Cód.Meta | I |  |  | N |
| GRAU | Grau | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CampoSubstituto / [TGMSUB](TGMSUB.md) | CODMETA->CODMETA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
