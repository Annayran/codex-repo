# TGMCFGF — Log alteração Limitações Intervalo Único

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
| LogLimitacoesIntervaloUnico | Log alteração Limitações Intervalo Único | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMETA | CODMETA | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data Alteração | D |  |  | N |
| DTFIM | Data fim. | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | LogLimitacoesIntervaloUnico / [TGMCFGF](TGMCFGF.md) | CODMETA->CODMETA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
