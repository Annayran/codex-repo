# TFXADF — Tabela de Ajuste de Documento do Checkout

- Módulo técnico: [TFX](../modules/TFX.md)
- Campos: 23
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AjusteDocumentoCheckout | Ajuste Documento Checkout | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQUIVO | Arquivo | C |  |  | N |
| CODEMP | Código da Empresa | I |  |  | N |
| DHIMPORT | Dh. Importação | H |  |  | N |
| DHPROTOCCAN | Dh. Protocolo de Cancelamento | H |  |  | N |
| DHPROTOCINU | Dh. Protocolo da Inutilização | H |  |  | N |
| ERRO | Mensagem de Erro | S |  |  | N |
| EXCCAN | Excluir Cancelamento? | S |  |  | N |
| EXCINU | Excluir Inutilização? | S |  |  | N |
| EXCXML | Excluir XML? | S |  |  | N |
| INCCAN | Incluir Cancelamento? | S |  |  | N |
| INCINU | Incluir Inutilização? | S |  |  | N |
| INCNOTA | Incluir Nota? | S |  |  | N |
| INCXML | Incluir XML? | S |  |  | N |
| MOTIVOCAN | Motivo do Cancelamento | S |  |  | N |
| MOTIVOINU | Motivo da Inutilização | S |  |  | N |
| NUAJUSTE | Nro. Único Ajuste | I |  |  | N |
| NUMNOTA | Nro. Único Fiscal | I |  |  | N |
| NUMPROTOCCAN | Núm. Protocolo de Cancelamento | S |  |  | N |
| NUMPROTOCINU | Núm. Protocolo da Inutilização | S |  |  | N |
| SEQVDA | Seq. Venda | I |  |  | N |
| SERIENOTA | Série | S |  |  | N |
| STATUS | Status | S |  |  | N |
| VLRVENDA | Valor da Nota | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AjusteDocumentoCheckout / [TFXADF](TFXADF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
