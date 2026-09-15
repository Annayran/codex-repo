# TPQGRQ — Grupo de Questionário

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoQuestionario | Grupo de Questionário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODGRUPOQUEST | Cód. Grupo | I |  |  | N |
| CODGRUPOQUESTPAI | Código do pai | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRGRUPOQUEST | Descrição | S |  |  | N |
| DHALTER | Data de alteração | H |  |  | N |
| GRAU | Grau | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Questionario / [TPQQUE](TPQQUE.md) | GrupoQuestionario / [TPQGRQ](TPQGRQ.md) | CODGRUPOQUEST->CODGRUPOQUEST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
