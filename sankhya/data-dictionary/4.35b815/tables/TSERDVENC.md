# TSERDVENC — Dívida vencida

- Módulo técnico: [TSE](../modules/TSE.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DividaVencida | DividaVencida | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CPFCNPJ | Documento CPF/CNPJ | S |  |  | N |
| DTOCORR | Data da ocorrência | D |  |  | N |
| LOCAL | Local | S |  |  | N |
| MODALID | Modalidade | S |  |  | N |
| NUCONSULTA | NUCONSULTA | I |  |  | N |
| ORIGEM | Instituição Cobradora | S |  |  | N |
| QTDEOCORR | Quantidade total de ocorrências | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TIPOMOEDA | Tipo de moeda | S |  |  | N |
| TITULO | Número do título | S |  |  | N |
| VALOR | Valor do título | F |  |  | N |
| VALORGERAL | Valor | F |  |  | N |
| VALORTOTAL | VALORTOTAL | F |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
