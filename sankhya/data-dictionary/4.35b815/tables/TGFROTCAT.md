# TGFROTCAT — Categorias Rota

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CategoriasRota | Categoria | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CATEGORIA | Categoria do veículo | S |  |  | N |
| CODROTA | Rota | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Rota / [TGFROT](TGFROT.md) | CategoriasRota / [TGFROTCAT](TGFROTCAT.md) | CODROTA->CODROTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
