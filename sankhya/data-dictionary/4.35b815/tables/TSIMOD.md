# TSIMOD — Módulo Adicional

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ModuloAdicional | Módulo Adicional | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMODULO | Módulo | I |  |  | N |
| DESCRMODULO | Descrição | S |  |  | N |
| RESOURCEID | Identificador | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcaoAgendada / [TSIAAG](TSIAAG.md) | ModuloAdicional / [TSIMOD](TSIMOD.md) | CODMODULO->CODMODULO | I |
| ModuloAdicional / [TSIMOD](TSIMOD.md) | ArquivoModulo / [TSIJAR](TSIJAR.md) | CODMODULO->CODMODULO | I |
| TimColetorDados / [TIMCOL](TIMCOL.md) | ModuloAdicional / [TSIMOD](TSIMOD.md) | CODMODULO->CODMODULO | I |
| TimGeradorDados / [TIMRND](TIMRND.md) | ModuloAdicional / [TSIMOD](TSIMOD.md) | CODMODULO->CODMODULO | I |
| TimIntegracaoPortal / [TIMINP](TIMINP.md) | ModuloAdicional / [TSIMOD](TSIMOD.md) | INPMODULOJAVA->CODMODULO | I |
| TimTradutorDados / [TIMTRA](TIMTRA.md) | ModuloAdicional / [TSIMOD](TSIMOD.md) | CODMODULO->CODMODULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
