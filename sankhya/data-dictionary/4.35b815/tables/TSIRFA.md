# TSIRFA — Arquivos de Relatórios

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ArquivoRelatorio | Arquivo de Relatório | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQUIVO | ARQUIVO | C |  |  | N |
| ARQUIVOBIN | Arquivo em bytes | B |  |  | N |
| NOME | NOME | S |  |  | N |
| NURFE | NURFE | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Relatorio / [TSIRFE](TSIRFE.md) | ArquivoRelatorio / [TSIRFA](TSIRFA.md) | NURFE->NURFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
