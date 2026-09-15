# TGFMDECD — Consulta de Documentos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ManifestoConsultaDocumento | Manifesto Fiscal Consulta Documento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVEACESSO | Chave Acesso | S |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DTCONSULTA | Dh. Evento | H |  |  | N |
| NSU | NSU | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ManifestoConsultaDocumento / [TGFMDECD](TGFMDECD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
