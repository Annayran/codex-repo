# TGFIGTVE — Informações da GTV-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InformacoesGTVE | Informações da GTV-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVECTE | Chave de acesso da GTV-e | S |  |  | N |
| IDGTVE | ID Informacoes GTV-e | I |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | InformacoesGTVE / [TGFIGTVE](TGFIGTVE.md) | NUNOTA->NUNOTA | I |
| InformacoesGTVE / [TGFIGTVE](TGFIGTVE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| InformacoesGTVE / [TGFIGTVE](TGFIGTVE.md) | ComplementosGTVE / [TGFCGTVE](TGFCGTVE.md) | IDGTVE->IDGTVE; NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
