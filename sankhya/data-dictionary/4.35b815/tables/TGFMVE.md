# TGFMVE — Modelos de Veiculos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ModeloVeiculo | Modelos de Veículos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CILINDRADA | Cilindrada | I |  |  | N |
| CODMODVEI | Cód. Modelo | I |  |  | N |
| COMBUSTIVEL | Combustível | S |  |  | N |
| DESCRMODVEI | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProdutoModeloVeiculo / [TGFPLM](TGFPLM.md) | ModeloVeiculo / [TGFMVE](TGFMVE.md) | CODMODVEI->CODMODVEI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
