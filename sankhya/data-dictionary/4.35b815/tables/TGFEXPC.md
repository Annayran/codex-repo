# TGFEXPC — Informações de Nota de Exportação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InformacaoNotaExportacaoInd | Exportação Direta/Indireta | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCFO | CFOP | I |  |  | N |
| NATUREZA | Natureza | S |  |  | N |
| NRODECLARACAO | Nro Declaração | S |  |  | N |
| NRORE | Nro Registro | S |  |  | N |
| NUNOTA | Nro Único Nota | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| InformacaoNotaExportacao / [TGFEXPN](TGFEXPN.md) | InformacaoNotaExportacaoInd / [TGFEXPC](TGFEXPC.md) | NRODECLARACAO->NRODECLARACAO; NRORE->NRORE; NUNOTA->NUNOTA | I |
| InformacaoNotaExportacaoInd / [TGFEXPC](TGFEXPC.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
