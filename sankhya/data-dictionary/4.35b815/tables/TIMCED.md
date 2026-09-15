# TIMCED — Configuração Parâmetros

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimConfigParamEDI | Configuração de Parámetros | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CEDCODIGO | Código | I |  |  | N |
| CEDMODULO | Modulo | S |  |  | N |
| CEDTITULO | Titulo | S |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimConfigParamEDI / [TIMCED](TIMCED.md) | TimParametro / [TIMCFP](TIMCFP.md) | CEDCODIGO->CFPCODIGO; CEDMODULO->CFPMODULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
