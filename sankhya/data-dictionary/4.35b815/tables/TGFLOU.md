# TGFLOU — Livro Outros

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LivroOutros | Livro Outros | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAJUSTE | Código ajuste | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODIGO | Código | I |  |  | N |
| CONVPRODUZIR | Convênio produzir | S |  |  | N |
| DATA | Data | H |  |  | N |
| NUMPROCESSO | Número processo | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| ORIGPROCESSO | Origem processo | I |  |  | N |
| TIPOIMPOSTO | Tipo imposto | S |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LivroOutros / [TGFLOU](TGFLOU.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
