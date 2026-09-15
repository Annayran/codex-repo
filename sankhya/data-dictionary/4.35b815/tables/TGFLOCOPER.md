# TGFLOCOPER — Local Operação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LocalOperacao | Imóvel/Construção Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ART | Art | S |  |  | N |
| CEI | CEI | S |  |  | N |
| CEP | CEP | S |  |  | N |
| CODBAI | Cod. Bairro | I |  |  | N |
| CODCIB | Cód. CIB | S |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODEND | Endereço | I |  |  | N |
| CODLOCOPER | Cod. do Local da Operação | I |  |  | N |
| CODOBRA | Cód. da Obra | S |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| COMPLEMENTO | Complemento | S |  |  | N |
| INSCIMBFIS | Insc. Imob. Fiscal | S |  |  | N |
| NUMERO | Numero | S |  |  | N |
| OPERACAO | Operação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | CODLOCOPER->CODLOCOPER | I |
| LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
