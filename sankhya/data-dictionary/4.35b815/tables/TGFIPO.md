# TGFIPO — Importação de Planejamento Orçamentário

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegImportacaoPO | Registro de Importação do Plan. Orçamentário | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CABARQ | Cabeçalho da Importação | S |  |  | N |
| CODMETA | Cód. Meta | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTPROC | Data do Processamento | H |  |  | N |
| NUIMPORT | Nro Importação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LogImportacaoPO / [TGFIPOLOG](TGFIPOLOG.md) | RegImportacaoPO / [TGFIPO](TGFIPO.md) | NUIMPORT->NUIMPORT | I |
| RegImportacaoPO / [TGFIPO](TGFIPO.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
