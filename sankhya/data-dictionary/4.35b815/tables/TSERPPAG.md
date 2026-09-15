# TSERPPAG — Pendência de pagamento

- Módulo técnico: [TSE](../modules/TSE.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalhePendenciaPagamento | DetalhePendenciaPagamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBANCO | Código da instituição | S |  |  | N |
| CONTRATO | Contrato | S |  |  | N |
| DTOCORR | Data | D |  |  | N |
| FILIAL | Filial | S |  |  | N |
| NUCONSULTA | NUCONSULTA | I |  |  | N |
| ORIGEM | Empresa | S |  |  | N |
| PRINCIPAL | Avalista? | S |  |  | N |
| QTDEOCORR | Total de ocorrências | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| SIGLAMOD | Modalidade | S |  |  | N |
| SUBJUDX | Condição de Sub Judice | S |  |  | N |
| TIPOMOEDA | Tipo de moeda | S |  |  | N |
| UF | UF | S |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
