# TIMIEO — Caracterização do objeto

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimCaracterizacaoObjeto | Infraestrutura | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IEOCODIGO | Código | I |  |  | N |
| IEOEDIFICIO | Edificio | I |  |  | N |
| IEOEMPREEND | Empreendimento | I |  |  | N |
| IEOIMOVEL | Imóvel | I |  |  | N |
| IEOINFRAESTRUTURA | Infraestrutura | I |  |  | N |
| IEOLOTEAMEN | Loteamento | I |  |  | N |
| IEOOBSERVACAO | Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | IEOEDIFICIO->EDGCODIGO | I |
| TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | TimInfraestrutura / [TIMIFE](TIMIFE.md) | IEOINFRAESTRUTURA->IFECODIGO | I |
| TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | IEOLOTEAMEN->LOTCODIGO | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | EDGCODIGO->IEOEDIFICIO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | IMVCODIGO->IEOIMOVEL | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | LOTCODIGO->IEOLOTEAMEN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
