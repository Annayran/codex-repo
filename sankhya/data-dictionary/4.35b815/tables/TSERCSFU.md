# TSERCSFU — Cheques sem Fundos

- Módulo técnico: [TSE](../modules/TSE.md)
- Campos: 18
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ChequeSemFundoAchei | Cheques sem Fundos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGENCIACCF | Agência | I |  |  | N |
| ALIN | Alínea | I |  |  | N |
| BANCOCCF | Banco | S |  |  | N |
| CCF | CCF | S |  |  | N |
| CDNATU | Código da natureza | S |  |  | N |
| CIDACCF | Cidade | S |  |  | N |
| CPFCNPJ | Documento CPF/CNPJ | S |  |  | N |
| DATAOCOR | Data da ocorrência | D |  |  | N |
| MOEDA | Moeda | S |  |  | N |
| NMRCHEQUE | Número do cheque | S |  |  | N |
| NUCONSULTA | NUCONSULTA | I |  |  | N |
| QTDEBANCO | Quantidade no banco | I |  |  | N |
| QTDEOCOR | QTDEOCOR | I |  |  | N |
| RESERVADOSERASA | ÁREA RESERVADA | S |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| UFCCF | UF | S |  |  | N |
| VALOR | Valor | F |  |  | N |
| VALORTOTAL | VALORTOTAL | F |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
