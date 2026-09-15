# TGFNCT — Notas Conhecimento Transporte

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 18
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotaConhecimentoTransporte | Notas Conhecimento Transporte | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BASEICMS | Base ICMS | F |  |  | N |
| BASEST | Base Substit. | F |  |  | N |
| CFOP | CFOP | I |  |  | N |
| CHAVENFE | Chave NF-e | S |  |  | N |
| CODMODDOC | Modelo Documento | I |  |  | N |
| DESCRDOC | Descrição | S |  |  | N |
| DTEMISSAO | Dh. Emissão | H |  |  | N |
| NUMERO | Nro. Doc. | S |  |  | N |
| NUNOTA | Nro. Único CT-e | I |  |  | N |
| PESOB | Peso Bruto | F |  |  | N |
| PESOL | Peso Líquido | F |  |  | N |
| SEGCODBAR | Segundo Código de Barras | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SERIE | Série | S |  |  | N |
| VLRICMS | Valor ICMS | F |  |  | N |
| VLRNOTA | Valor da Nota | F |  |  | N |
| VLRST | Valor Substit. | F |  |  | N |
| VLRTOTPROD | Valor Total dos Produtos | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | NotaConhecimentoTransporte / [TGFNCT](TGFNCT.md) | NUNOTA->NUNOTA | I |
| NotaConhecimentoTransporte / [TGFNCT](TGFNCT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| RelacaoItemCTe / [TGFINCT](TGFINCT.md) | NotaConhecimentoTransporte / [TGFNCT](TGFNCT.md) | NUNOTA->NUNOTA; SEQNOTA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
