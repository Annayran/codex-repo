# TFXIMP — FOX-TFXIMP

- Módulo técnico: [TFX](../modules/TFX.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImpressorasPdv | Impressoras do Pdv | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVA | Ativa | S |  |  | N |
| CODIMP | Cód.Impressora | I |  |  | N |
| CODPDV | Cód.PDV | I |  |  | N |
| COMPARTILHA | Compartilha | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| MODELO | Modelo | S |  |  | N |
| NUVERSAO | NUVERSAO | I |  |  | N |
| PADRAO | Padrão | S |  |  | N |
| PORTA | Porta | S |  |  | N |
| QTDCOLUNAS | Qtd. de Colunas | I |  |  | N |
| TERMICA | Térmica | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroDePdv / [TFXPDV](TFXPDV.md) | ImpressorasPdv / [TFXIMP](TFXIMP.md) | IMPRESSORADEFAULT->CODIMP | I |
| ImpressorasPdv / [TFXIMP](TFXIMP.md) | CadastroDePdv / [TFXPDV](TFXPDV.md) | CODPDV->CODPDV | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
