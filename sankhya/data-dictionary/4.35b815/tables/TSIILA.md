# TSIILA — Acessos - Importador de Dados

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImportacaoLegacyAcessos | Acessos - Importador de Dados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPO | Grupo de Usuário | I |  |  | N |
| CODILA | Id acesso | I |  |  | N |
| CODITA | Tabelas de Acessos Importador de Dados | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImportacaoLegacyAcessos / [TSIILA](TSIILA.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| ImportacaoLegacyAcessos / [TSIILA](TSIILA.md) | ImportacaoLegacyAcessosTabelas / [TSIITA](TSIITA.md) | CODITA->CODITA | I |
| ImportacaoLegacyAcessos / [TSIILA](TSIILA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
