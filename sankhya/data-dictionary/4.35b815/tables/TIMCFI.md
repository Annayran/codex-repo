# TIMCFI — Anúncios Classificado

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimClassificadoAnuncio | Anúncios do Classificado | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CFIANUNCIO | Nº Anúncio | I |  |  | N |
| CFICLASSIFICADO | Classificado | I |  |  | N |
| CFIDESCRICAO | Anúncio Completo | S |  |  | N |
| CFIESTAGIO | Publicar | S |  |  | N |
| CFIIMOVEL | Imóvel | I |  |  | N |
| CFIVALOR | Valor | F |  |  | N |
| CLAGERENTECOR | Gerente | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimClassificado / [TIMCLF](TIMCLF.md) | TimClassificadoAnuncio / [TIMCFI](TIMCFI.md) | CLFCODIGO->CFICLASSIFICADO | I |
| TimClassificadoAnuncio / [TIMCFI](TIMCFI.md) | TimAnuncio / [TIMANU](TIMANU.md) | CFIANUNCIO->ANUCODIGO; CFIIMOVEL->ANUIMOVEL | I |
| TimClassificadoAnuncio / [TIMCFI](TIMCFI.md) | TimClassificado / [TIMCLF](TIMCLF.md) | CFICLASSIFICADO->CLFCODIGO | I |
| TimClassificadoAnuncio / [TIMCFI](TIMCFI.md) | TimCorretor / [TIMCOR](TIMCOR.md) | CLAGERENTECOR->CORCODIGO | I |
| TimClassificadoAnuncio / [TIMCFI](TIMCFI.md) | TimImovel / [TIMIMV](TIMIMV.md) | CFIIMOVEL->IMVCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
