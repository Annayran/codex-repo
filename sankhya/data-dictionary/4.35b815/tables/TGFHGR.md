# TGFHGR — Histórico de Geração de Remessa Bancária

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoGerRemBanc | Histórico de Geração de Remessa Bancária | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQUIVO | Arquivo | C |  |  | N |
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| CODIGOLAYOUT | Layout | I |  |  | N |
| DHGERACAO | Data e hora remessa | H |  |  | N |
| NOMEARQ | Nome arquivo | S |  |  | N |
| NUREMESSA | Número remessa | I |  |  | N |
| NUTGFHGR | Número único | I |  |  | N |
| QTDTITPROC | Qtd. títulos processados | I |  |  | N |
| VLRTOTALREM | Valor Total da Remessa | F |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
