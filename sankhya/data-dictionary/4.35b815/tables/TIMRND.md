# TIMRND — TIMRND

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimGeradorDados | Gerador de dados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLASSNAME | Class Name | S |  |  | N |
| CODMODULO | Cód. Módulo | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DIRNAME | Nome da Pasta | S |  |  | N |
| ENCODING | Codificação | S |  |  | N |
| FILENAME | Nome do Arquivo | S |  |  | N |
| JAVASCRIPT | Javascript (JSON) | C |  |  | N |
| LAYOUTXML | Layout (XML) | C |  |  | N |
| NURND | Nro. Único | I |  |  | N |
| OUTPUTTYPE | Extensão do Arquivo | S |  |  | N |
| TIPOLINGUA | Linguagem | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimGeradorDados / [TIMRND](TIMRND.md) | ModuloAdicional / [TSIMOD](TSIMOD.md) | CODMODULO->CODMODULO | I |
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimGeradorDados / [TIMRND](TIMRND.md) | NURND->NURND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
