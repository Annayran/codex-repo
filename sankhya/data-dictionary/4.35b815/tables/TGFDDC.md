# TGFDDC — Detalhes de Desenhos de chapas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 17
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalheDesenhoChapas | Detalhes de Desenhos de chapas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPECA | CODPECA | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| COMPRIMENTO | Comprimento | F |  |  | N |
| COMPRIMENTOORELHA1 | Comprimento orelha 1 | F |  |  | N |
| COMPRIMENTOORELHA2 | Comprimento orelha 2 | F |  |  | N |
| COMPRIMENTOTOTAL | Comprimento total | F |  |  | S |
| DESENHO | Desenho | B |  |  | N |
| DIAMETROCINTA | Diâmetro da cinta | F |  |  | N |
| DIAMETROEXT | Diâmetro externo | F |  |  | N |
| DIAMETROINT | Diâmetro interno | F |  |  | N |
| NOME | Nome no projeto | S |  |  | N |
| NOMEELEMENTO | Elemento | S |  |  | N |
| NUNOTA | Nro. Nota | I |  |  | N |
| QTD | Quantidade | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| UNIDADE | Unid. Medida | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheDesenhoChapas / [TGFDDC](TGFDDC.md) | FacesChapa / [TGFFAC](TGFFAC.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| DetalheDesenhoChapas / [TGFDDC](TGFDDC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
