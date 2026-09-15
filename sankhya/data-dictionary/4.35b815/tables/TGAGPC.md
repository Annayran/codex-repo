# TGAGPC — Grupo Padrão de Classificação

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoPadraoClass | Grupo Padrão de Classificação | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGPC | Código | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PadraoClassificacao / [TGACLT](TGACLT.md) | GrupoPadraoClass / [TGAGPC](TGAGPC.md) | CODGPC->CODGPC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
