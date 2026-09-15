# VGFCABBEM — View Despesas dos Bens

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DespesasVinculadasAoBem | Despesas Vinculadas Ao Bem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Código do bem | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| DTENTSAI | Dt. Entrada/Saída | D |  |  | N |
| DTNEG | Dt. Neg. | D |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| STATUSRATEIO | Status do Rateio | S |  |  | N |
| VLRICMS | Vlr. do ICMS | F |  |  | N |
| VLRICMSRATEIO | ICMS sobre o frete | F |  |  | N |
| VLRNOTA | Vlr. Nota | F |  |  | N |
| VLRRATEIO | Valor Rateio | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Imobilizado / [TCIBEM](TCIBEM.md) | DespesasVinculadasAoBem / [VGFCABBEM](VGFCABBEM.md) | CODBEM->CODBEM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
