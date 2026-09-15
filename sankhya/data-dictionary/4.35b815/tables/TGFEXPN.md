# TGFEXPN — Informações de Nota de Exportação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InformacaoNotaExportacao | Informações de Nota Exportação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DTCONHEC | Data Conhecimento | D |  |  | N |
| NROCONHEC | Nro Conhecimento | S |  |  | N |
| NRODECLARACAO | Nro Declaração | S |  |  | N |
| NRORE | Nro Registro | S |  |  | N |
| NUNOTA | Nro Único Nota | I |  |  | N |
| TIPOCONHEC | Tipo Conhecimento | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| InformacaoNotaExportacao / [TGFEXPN](TGFEXPN.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| InformacaoNotaExportacao / [TGFEXPN](TGFEXPN.md) | InformacaoNotaExportacaoInd / [TGFEXPC](TGFEXPC.md) | NRODECLARACAO->NRODECLARACAO; NRORE->NRORE; NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
