# TIMCFP — Parâmetros

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimParametro | Parâmetros | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CFPCODIGO | Código | I |  |  | N |
| CFPDEFAULTVALUE | Valor Padrão | S |  |  | N |
| CFPDESCRICAO | Descrição | S |  |  | N |
| CFPINSTANCIA | Nome Instancia Dest. | S |  |  | N |
| CFPMODULO | Modulo | S |  |  | N |
| CFPNOME | Nome | S |  |  | N |
| CFPNUCAMPOINSTDEST | Nro. Campo Inst. Destino | S |  |  | N |
| CFPOPCOES | Opções | S |  |  | N |
| CFPREQUERIDO | Requerido | S |  |  | N |
| CFPSEQUENCE | Ordem | I |  |  | N |
| CFPTIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimConfigParamEDI / [TIMCED](TIMCED.md) | TimParametro / [TIMCFP](TIMCFP.md) | CEDCODIGO->CFPCODIGO; CEDMODULO->CFPMODULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
