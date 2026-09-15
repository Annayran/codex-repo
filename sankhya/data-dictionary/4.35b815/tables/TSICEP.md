# TSICEP — CEP

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CEP | CEP | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CEP | CEP | S |  |  | N |
| CODBAI | Cód.Bairro | I |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODEND | Cód.Endereço | I |  |  | N |
| INTERVALO | Intervalo de Números | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CEP / [TSICEP](TSICEP.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| CEP / [TSICEP](TSICEP.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| CEP / [TSICEP](TSICEP.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
