# TGFRPC — Registro de peças

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegistroPecas | Registro de Peças | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPECA | Código | I |  |  | N |
| COMPRIMENTO | Comprimento | F |  |  | N |
| COMPRIMENTOORELHA1 | Comprimento orelha 1 | F |  |  | N |
| COMPRIMENTOORELHA2 | Comprimento orelha 2 | F |  |  | N |
| DESCRICAO | Descrição da peça | S |  |  | N |
| DIAMETROCINTA | Diâmetro da cinta | F |  |  | N |
| DIAMETROEXT | Diâmetro externo | F |  |  | N |
| DIAMETROINT | Diâmetro Interno | F |  |  | N |
| IMAGEM | Imagem | B |  |  | N |
| TIPO | Tipo da peça | S |  |  | N |
| UNIDADE | Unid. Medida | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegistroPecas / [TGFRPC](TGFRPC.md) | DimensaoPecas / [TGFDPC](TGFDPC.md) | CODPECA->CODPECA | I |
| RegistroPecas / [TGFRPC](TGFRPC.md) | ProdutoPeca / [TGFPPP](TGFPPP.md) | CODPECA->CODPECA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
