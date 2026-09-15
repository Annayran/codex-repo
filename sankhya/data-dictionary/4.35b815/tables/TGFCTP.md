# TGFCTP — Contagem por Página

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContagemPagina | Contagem por Página | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVOL | Volume | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| NUIMP | Nro. Impressão | I |  |  | N |
| PAGINA | Página | I |  |  | N |
| QTD | Quantidade | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContagemPagina / [TGFCTP](TGFCTP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
