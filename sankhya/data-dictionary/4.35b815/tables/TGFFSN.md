# TGFFSN — Faixas do Simples Nacional

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FaixaSimplesNacional | Faixas do Simples Nacional | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQUOTA | Alíquota | F |  |  | N |
| COFINS | % COFINS | F |  |  | N |
| CPP | % CPP | F |  |  | N |
| CSLL | % CSLL | F |  |  | N |
| ICMS | % ICMS | F |  |  | N |
| IPI | % IPI | F |  |  | N |
| IRPJ | % IRPJ | F |  |  | N |
| ISS | % ISS | F |  |  | N |
| NUANEXO | Nro. do Anexo | I |  |  | N |
| NUFAIXA | Nro. da Faixa | I |  |  | N |
| PARCDEDUZIR | Parcela a Deduzir | F |  |  | N |
| PISPASEP | % PIS/PASEP | F |  |  | N |
| RECBRUTAFIN | Receita Bruta Final | F |  |  | N |
| RECBRUTAINI | Receita Bruta Inicial | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnexoSimplesNacional / [TGFASN](TGFASN.md) | FaixaSimplesNacional / [TGFFSN](TGFFSN.md) | NUANEXO->NUANEXO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
