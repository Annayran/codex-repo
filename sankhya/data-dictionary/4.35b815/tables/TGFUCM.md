# TGFUCM — Acessos por consolidação de movimentos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConsolidacaoAcessos | Acessos por consolidação de movimentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCONFIG | Cód. Configuração | I |  |  | N |
| CODGRUPO | Cód. Grupo | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONSULTAR | Consultar | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConsolidacaoAcessos / [TGFUCM](TGFUCM.md) | ConsolidacaoConfig / [TGFCMV](TGFCMV.md) | CODCONFIG->CODCONFIG | I |
| ConsolidacaoAcessos / [TGFUCM](TGFUCM.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| ConsolidacaoAcessos / [TGFUCM](TGFUCM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ConsolidacaoConfig / [TGFCMV](TGFCMV.md) | ConsolidacaoAcessos / [TGFUCM](TGFUCM.md) | CODCONFIG->CODCONFIG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
