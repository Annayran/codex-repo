# TSIAGE — Agências Bancárias

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 13
- Instâncias DWF: 2
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AgenciaBancaria | Agência Bancária | S | N |
| AgenciaCorrespondenteBancario | Agência do Correspondente Bancário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CEP | CEP | S |  |  | N |
| CODAGE | Agência | S |  |  | N |
| CODBAI | Bairro | I |  |  | N |
| CODBCO | Banco | I |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODEND | Endereço | I |  |  | N |
| COMPLEMENTO | Complemento | S |  |  | N |
| EMAIL | Email | S |  |  | N |
| FAX | Fax | S |  |  | N |
| NOMEAGE | Nome da Agência | S |  |  | N |
| NOMEGER | Nome do Gerente | S |  |  | N |
| NUMEND | Número | S |  |  | N |
| TELEFONE | Telefone | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgenciaBancaria / [TSIAGE](TSIAGE.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| AgenciaBancaria / [TSIAGE](TSIAGE.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| AgenciaBancaria / [TSIAGE](TSIAGE.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| AgenciaBancaria / [TSIAGE](TSIAGE.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| ContaBancaria / [TSICTA](TSICTA.md) | AgenciaBancaria / [TSIAGE](TSIAGE.md) | CODAGE->CODAGE; CODBCO->CODBCO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
