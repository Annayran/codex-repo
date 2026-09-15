# TGFREFIMP — Impostos Reforma Tributária

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 27
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImpostosReformaTrib | Impostos Reforma Tributária | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUNOTA | Nro. Único | I |  |  | N |
| NUREFIMP | Nro. Único Imposto | I |  |  | N |
| VALOR_TOTAL | Valor Total do Documento Fiscal | F |  |  | N |
| VBCIBSCBS | Valor total da BC do IBS e da CBS | F |  |  | N |
| VCBS | Valor total da CBS | F |  |  | N |
| VCBSMONO | Total da CBS monofásica | F |  |  | N |
| VCBSMONORET | Total da CBS monofásica retida anteriormente | F |  |  | N |
| VCBSMONORETEN | Total da CBS monofásica sujeita à retenção | F |  |  | N |
| VCRDPRESCONSUSCBS | Total do crédito presumido em condição suspensiva (CBS) | F |  |  | N |
| VCRDPRESCONSUSIBS | Total do crédito presumido em condição suspensiva (IBS) | F |  |  | N |
| VCREDPRESCBS | Total do crédito presumido (CBS) | F |  |  | N |
| VCREDPRESIBS | Total do crédito presumido (IBS) | F |  |  | N |
| VDEVTRIBCBS | Valor total de devolução de tributos (CBS) | F |  |  | N |
| VDEVTRIBIBSMUN | Valor total de devolução de tributos IBS do Município | F |  |  | N |
| VDEVTRIBIBSUF | Valor total de devolução de tributos IBS da UF | F |  |  | N |
| VDIFCBS | Valor total do diferimento (CBS) | F |  |  | N |
| VDIFIBSMUN | Valor total do diferimento IBS do Município | F |  |  | N |
| VDIFIBSUF | Valor total do diferimento IBS da UF | F |  |  | N |
| VIBS | Valor total do IBS | F |  |  | N |
| VIBSMONO | Total do IBS monofásico | F |  |  | N |
| VIBSMONORET | Total do IBS monofásico retido anteriormente | F |  |  | N |
| VIBSMONORETEN | Total do IBS monofásico sujeito à retenção | F |  |  | N |
| VIBSMUN | Valor total do IBS do Município | F |  |  | N |
| VIBSUF | Valor total do IBS da UF | F |  |  | N |
| VIS | Total do imposto seletivo | F |  |  | N |
| VNFTOT | Total da NFe/NFCe | F |  |  | N |
| VTOTDFE | Total do DFe | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ImpostosReformaTrib / [TGFREFIMP](TGFREFIMP.md) | NUNOTA->NUNOTA | I |
| ImpostosReformaTrib / [TGFREFIMP](TGFREFIMP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
