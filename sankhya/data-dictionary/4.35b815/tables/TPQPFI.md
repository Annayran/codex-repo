# TPQPFI — Perfil X Questionário

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PerfilQuestionario | Perfil X Questionário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPERFIL | Cód. Perfil | I |  |  | N |
| CODQUEST | Cód. do questionário | I |  |  | N |
| DHALTER | Data da alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PerfilQuestionario / [TPQPFI](TPQPFI.md) | PerfilFolhaPagamento /  | CODPERFIL->CODPERFIL | I |
| Questionario / [TPQQUE](TPQQUE.md) | PerfilQuestionario / [TPQPFI](TPQPFI.md) | CODQUEST->CODQUEST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
