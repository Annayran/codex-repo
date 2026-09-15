# TSIACI — Controle de Acesso dos Relatórios

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleAcessoRelatorio | Controle de Acesso dos Relatórios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALTERA | Permite Alteração? | S |  |  | N |
| CODGRUPO | Cód.Grupo de Usuário | I |  |  | N |
| CODREL | Cód.Relatório | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONS | Permite Consulta ? | S |  |  | N |
| FILTRO | Permite Alterar Filtros? | S |  |  | N |
| RESUMO | Permite Alterar Resumo? | S |  |  | N |
| SEGURANCA | Permite Outros Usuários a Alterar | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleAcessoRelatorio / [TSIACI](TSIACI.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |
| ControleAcessoRelatorio / [TSIACI](TSIACI.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| ControleAcessoRelatorio / [TSIACI](TSIACI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
