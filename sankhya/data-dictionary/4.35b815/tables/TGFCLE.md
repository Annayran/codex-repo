# TGFCLE — CategoriaLancamentoExtrato

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CategoriaLancamentoExtrato | Cat. Lanç. Extrato Bancário | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Conta bancária | I |  |  | N |
| CODIGOEDI | Cód. Categoria (EDI) | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| SEQUENCIA | Seq. categoria | I |  |  | N |
| TIPOLANC | Tipo de lançamento | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContaBancaria / [TSICTA](TSICTA.md) | CategoriaLancamentoExtrato / [TGFCLE](TGFCLE.md) | CODCTABCOINT->CODCTABCOINT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
