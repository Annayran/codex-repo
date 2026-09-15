# TSIGPU — Grupos Adicionais

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GruposPorUsuario | Grupos Adicionais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPO | Codigo Grupo | I |  |  | N |
| CODUSU | Codigo Usuario | I |  |  | N |
| DATAFIM | Data limite de acesso | D |  |  | N |
| DATAINICIO | Data inicial de acesso | D |  |  | N |
| REPASSAR | Permissão de Repasse do Grupo | S |  |  | N |
| SEQUENCIA | Sequencia de Registros | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GruposPorUsuario / [TSIGPU](TSIGPU.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| GruposPorUsuario / [TSIGPU](TSIGPU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | GruposPorUsuario / [TSIGPU](TSIGPU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
