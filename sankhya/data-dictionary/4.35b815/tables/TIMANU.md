# TIMANU — Anúncios do Imóvel

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimAnuncio | Anúncios do Imóvel | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANUCODIGO | Sequência | I |  |  | N |
| ANUDESCRICAO | Anúncio | S |  |  | N |
| ANUIMOVEL | Imóvel | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAnuncio / [TIMANU](TIMANU.md) | TimImovel / [TIMIMV](TIMIMV.md) | ANUIMOVEL->IMVCODIGO | I |
| TimClassificadoAnuncio / [TIMCFI](TIMCFI.md) | TimAnuncio / [TIMANU](TIMANU.md) | CFIANUNCIO->ANUCODIGO; CFIIMOVEL->ANUIMOVEL | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimAnuncio / [TIMANU](TIMANU.md) | IMVCODIGO->ANUIMOVEL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
