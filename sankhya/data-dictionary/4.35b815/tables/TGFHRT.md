# TGFHRT — Histórico de Processamento de Retorno

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
| HistoricoRetornoBancario | Histórico de Processamento de Retorno Bancario | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | Código | I |  |  | N |
| CODRET | Cód. agendador | I |  |  | N |
| DHPROC | Dt. processamento | H |  |  | N |
| NOMEARQ | Nome arquivo | S |  |  | N |
| NUOBCLOG | Nro. único processamento | I |  |  | N |
| QTDLINNHASARQ | Qtd. linhas arquivo | I |  |  | N |
| QTDLINNHASERR | Qtd. linhas proc. com erro | I |  |  | N |
| QTDLINNHASPROC | Qtd. linhas processadas | I |  |  | N |
| QTDLINNHASSUC | Qtd. linhas proc. c/ sucesso | I |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
