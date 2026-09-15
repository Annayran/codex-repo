# TGFPPO — Permissões por Ocorrência

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PermissoesPorOcorrencia | Permissões por Ocorrência | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| NUCRAF | NUCRAF | I |  |  | N |
| REGRA | Cód. Regra | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OcorrAlteracaoFinanceiro / [TGFOAF](TGFOAF.md) | PermissoesPorOcorrencia / [TGFPPO](TGFPPO.md) | NUCRAF->NUCRAF; SEQUENCIA->SEQUENCIA | I |
| PermissoesPorOcorrencia / [TGFPPO](TGFPPO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
