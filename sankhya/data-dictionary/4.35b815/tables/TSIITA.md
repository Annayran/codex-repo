# TSIITA — Acessos - Importador de Dados Tabelas

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImportacaoLegacyAcessosTabelas | Acessos - Importador de Dados Tabelas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODITA | Id tabela acesso | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImportacaoLegacyAcessos / [TSIILA](TSIILA.md) | ImportacaoLegacyAcessosTabelas / [TSIITA](TSIITA.md) | CODITA->CODITA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
