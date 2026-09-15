# TSIDBQUERY — TABLE TSIDBQUERY

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DbExplorerQuery | Queries do DbExplorer | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| DHALTER | Dh. alteração | H |  |  | N |
| NUQUERY | Nro. único | I |  |  | N |
| TEXTOQUERY | Texto SQL | C |  |  | N |
| TITULOQUERY | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DbExplorerQuery / [TSIDBQUERY](TSIDBQUERY.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
