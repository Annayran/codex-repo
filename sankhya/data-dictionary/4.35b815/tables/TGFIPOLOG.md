# TGFIPOLOG — Log de Importação de Planejamento Orçamentário

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LogImportacaoPO | Log de Importação do Planejamento Orçamentário | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ERRO | ERRO | S |  |  | N |
| NROLINHA | NROLINHA | I |  |  | N |
| NUIMPORT | NUIMPORT | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LogImportacaoPO / [TGFIPOLOG](TGFIPOLOG.md) | RegImportacaoPO / [TGFIPO](TGFIPO.md) | NUIMPORT->NUIMPORT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
