# TGFPWU — TGFPWU

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RelacaoPDVUsuario | Relacao PDV Usuario | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODPDV | CODPDV | I |  |  | N |
| CODUSU | Usuario | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroPDV / [TGFPDV](TGFPDV.md) | RelacaoPDVUsuario / [TGFPWU](TGFPWU.md) | CODPDV->CODPDV | I |
| RelacaoPDVUsuario / [TGFPWU](TGFPWU.md) | CadastroPDV / [TGFPDV](TGFPDV.md) | CODPDV->CODPDV | I |
| RelacaoPDVUsuario / [TGFPWU](TGFPWU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
