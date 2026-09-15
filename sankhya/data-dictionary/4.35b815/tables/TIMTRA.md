# TIMTRA — TIMTRA

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimTradutorDados | Tradutor de dados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLASSNAME | Class Name | S |  |  | N |
| CODMODULO | Cód. Módulo | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| NUTRA | Nro. Único | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimTradutorDados / [TIMTRA](TIMTRA.md) | NUTRA->NUTRA | I |
| TimTradutorDados / [TIMTRA](TIMTRA.md) | ModuloAdicional / [TSIMOD](TSIMOD.md) | CODMODULO->CODMODULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
