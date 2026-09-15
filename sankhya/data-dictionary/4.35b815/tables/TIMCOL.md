# TIMCOL — TIMCOL

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
| TimColetorDados | Coletor de dados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLASSNAME | Class Name | S |  |  | N |
| CODMODULO | Cód. Módulo | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| NUCOL | Nro. Único | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimColetorDados / [TIMCOL](TIMCOL.md) | ModuloAdicional / [TSIMOD](TSIMOD.md) | CODMODULO->CODMODULO | I |
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimColetorDados / [TIMCOL](TIMCOL.md) | NUCOL->NUCOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
