# TGFIMPMOBIS — TGFIMPMOBIS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImportacaoArquivoMOBIS | ImportacaoArquivoMOBIS | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQHEADER | Primeira Linha | S |  |  | N |
| DHIMP | DH Processamento | H |  |  | N |
| MSG | Mensagens | C |  |  | N |
| NOMEARQ | Nome | S |  |  | N |
| NUIMP | Importação | I |  |  | N |
| QTDPEDIMP | Qtd. Pedidos Importados | I |  |  | N |
| QTDPROC | Qtd. Processado | I |  |  | N |
| QTDTOTPED | Qtd. Pedidos | I |  |  | N |
| REPROCESSAR | Reprocessar | S |  |  | N |
| TIPARQUIVO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImportacaoArquivoMOBIS / [TGFIMPMOBIS](TGFIMPMOBIS.md) | ItemImportacaoMOBIS / [TGFIIMPMOBIS](TGFIIMPMOBIS.md) | NUIMP->NUIMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
