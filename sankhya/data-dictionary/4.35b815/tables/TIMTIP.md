# TIMTIP — TIMTIP

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimImovelPlano | Imóveis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| TIPBANHEIROS | Banheiros | I |  |  | S |
| TIPCHURRASQUEIRA | Churrasqueira | S |  |  | S |
| TIPDESCRESUMO | Descrição resumida | S |  |  | S |
| TIPDESCRICAOCOMPLETA | Descrição completa | S |  |  | S |
| TIPGARAGENS | Garagens | I |  |  | S |
| TIPIMOVEL | Imóvel | I |  |  | N |
| TIPMOBILIA | Mobiliado | S |  |  | S |
| TIPMOSTRAREND | Divulgar Endereço | S |  |  | S |
| TIPMOSTRARMAPA | Divulgar Mapa | S |  |  | S |
| TIPPISCINA | Piscina | S |  |  | S |
| TIPPLANO | Plano | I |  |  | N |
| TIPQUARTOS | Quartos | I |  |  | S |
| TIPQUINTAL | Quintal | S |  |  | S |
| TIPSUITE | Suítes | I |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimImovelPlano / [TIMTIP](TIMTIP.md) | TimImovel / [TIMIMV](TIMIMV.md) | TIPIMOVEL->IMVCODIGO | I |
| TimImovelPlano / [TIMTIP](TIMTIP.md) | TimPlanos / [TIMTPL](TIMTPL.md) | TIPPLANO->TPLCODIGO | I |
| TimPlanos / [TIMTPL](TIMTPL.md) | TimImovelPlano / [TIMTIP](TIMTIP.md) | TPLCODIGO->TIPPLANO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
