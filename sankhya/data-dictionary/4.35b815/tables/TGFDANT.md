# TGFDANT — Documentos Anteriores

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DocumentoAnterior | Documento Anterior | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVEDANT | Chave de Acesso (doc. anterior) | S |  |  | N |
| CODPARC | Parceiro Emissor do Documento | I |  |  | N |
| CTEREF | CT-e Referenciado | S |  |  | N |
| DEMI | Data de Emissão | H |  |  | N |
| NDOC | Número do Documento Fiscal | S |  |  | N |
| NUNOTA | Nro. Único da Nota | I |  |  | N |
| NUNOTAORIG | CT-e Ref. Documento Fiscal Anterior (Ente Governamental) | I |  |  | N |
| SEQDANT | Sequência do Documento | I |  |  | N |
| SERIE | Série da nota | S |  |  | N |
| SUBSER | Subsérie da nota | S |  |  | N |
| TPDOCTA | Tipo do Documento de Transporte Anterior | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | DocumentoAnterior / [TGFDANT](TGFDANT.md) | NUNOTA->NUNOTA | I |
| DocumentoAnterior / [TGFDANT](TGFDANT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DocumentoAnterior / [TGFDANT](TGFDANT.md) | NotaOrigem / [TGFCAB](TGFCAB.md) | NUNOTAORIG->NUNOTA | I |
| DocumentoAnterior / [TGFDANT](TGFDANT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
