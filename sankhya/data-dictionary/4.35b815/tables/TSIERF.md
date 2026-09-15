# TSIERF — Entidades Recentes e Favoritas

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EntidadesRecentesFavoritas | Entidades Recentes e Favoritas | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| DHULTIMOACESSO | Último acesso | H |  |  | N |
| FAVORITO | Favorito | S |  |  | N |
| NOMEINSTANCIA | Nome da Instância | S |  |  | N |
| PKREGISTRO | Chave do registro | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EntidadesRecentesFavoritas / [TSIERF](TSIERF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
