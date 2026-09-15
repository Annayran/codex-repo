# TGFCMN — Controle Multimodal Notas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleMultimodal | Controle Multimodal | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCIDINICIO | Cód. Cidade Início | I |  |  | N |
| CODCIDTERMINO | Cód. Cidade Término | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPMODALCTE | Modal CT-e | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ControleMultimodal / [TGFCMN](TGFCMN.md) | NUNOTA->NUNOTA | I |
| ControleMultimodal / [TGFCMN](TGFCMN.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ControleMultimodal / [TGFCMN](TGFCMN.md) | Cidade / [TSICID](TSICID.md) | CODCIDINICIO->CODCID | I |
| ControleMultimodal / [TGFCMN](TGFCMN.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDTERMINO->CODCID | I |
| ControleMultimodal / [TGFCMN](TGFCMN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
