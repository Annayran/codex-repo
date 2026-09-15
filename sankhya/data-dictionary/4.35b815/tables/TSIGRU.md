# TSIGRU — Grupos de usuários

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 14
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoUsuario | Grupo de Usuário | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODGRUPO | Código | I |  |  | N |
| CODUNN | Unidade de negócio | I |  |  | N |
| EMAIL | Email | S |  |  | N |
| NOMEGRUPO | Nome | S |  |  | N |
| USER_NAME | Nome do Usuário | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcessoUnidadeGerencial / [TMIAUN](TMIAUN.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| AvisoSistema / [TSIAVI](TSIAVI.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| ConsolidacaoAcessos / [TGFUCM](TGFUCM.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| ControleAcessoDashboardsGOL / [TSIACD](TSIACD.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| ControleAcessoRelatorio / [TSIACI](TSIACI.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| ExcecoesGruposLgpd / [TSIEXG](TSIEXG.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| GruposPorUsuario / [TSIGPU](TSIGPU.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| GrupoUsuario / [TSIGRU](TSIGRU.md) | ExcecoesGruposLgpd / [TSIEXG](TSIEXG.md) | CODGRUPO->CODGRUPO | I |
| GrupoUsuario / [TSIGRU](TSIGRU.md) | RoteamentoImpressaoGrupo / [TSIRIM](TSIRIM.md) | CODGRUPO->CODIGO | I |
| GrupoUsuario / [TSIGRU](TSIGRU.md) | UnidadeNegocio / [TGFUNN](TGFUNN.md) | CODUNN->CODUNN | I |
| ImportacaoLegacyAcessos / [TSIILA](TSIILA.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| PermissaoAcesso / [TSIPER](TSIPER.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| Usuario / [TSIUSU](TSIUSU.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| UsuarioCandidatoProcesso / [TWFUCP](TWFUCP.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPOUSU->CODGRUPO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
