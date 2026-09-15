# TGFMDE — Múltiplas Datas de Entrega

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MultiplasDatasEntrega | Múltipas Datas de Entrega | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CABECALHO | CABECALHO | S |  |  | N |
| CODLOCAL | CODLOCAL | I |  |  | N |
| CODPARC | CODPARC | I |  |  | N |
| CODPROD | CODPROD | I |  |  | N |
| CONTROLE | CONTROLE | S |  |  | N |
| DATAENTREGA | Data de entrega | H |  |  | N |
| DIFERENCIADOR | DIFERENCIADOR | I |  |  | N |
| NUMCOTACAO | NUMCOTACAO | I |  |  | N |
| QTDENTREGA | Qtd. entrega | F |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ColetaItemCotacao / [TGFITC](TGFITC.md) | MultiplasDatasEntrega / [TGFMDE](TGFMDE.md) | CABECALHO->CABECALHO; CODLOCAL->CODLOCAL; CODPARC->CODPARC; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
