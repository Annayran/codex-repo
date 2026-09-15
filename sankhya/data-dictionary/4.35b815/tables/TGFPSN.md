# TGFPSN — Partilha do Simples Nacional

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 30
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PartilhaSimplesNacional | Partilha do simples nacional | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQCOFINS | % COFINS | F |  |  | N |
| ALIQCOFINSALIEFE | % COFINS | F |  |  | N |
| ALIQCPP | % CPP | F |  |  | N |
| ALIQCPPALIEFE | % CPP | F |  |  | N |
| ALIQCSLL | % CSLL | F |  |  | N |
| ALIQCSLLALIEFE | % CSLL | F |  |  | N |
| ALIQICMS | % ICMS | F |  |  | N |
| ALIQICMSALIEFE | % ICMS | F |  |  | N |
| ALIQIPI | % IPI | F |  |  | N |
| ALIQIPIALIEFE | % IPI | F |  |  | N |
| ALIQIRPJ | % IRPJ | F |  |  | N |
| ALIQIRPJALIEFE | % IRPJ | F |  |  | N |
| ALIQISS | % ISS | F |  |  | N |
| ALIQISSALIEFE | % ISS | F |  |  | N |
| ALIQPISPASEP | % PIS/PASEP | F |  |  | N |
| ALIQPISPASEPALIEFE | % PIS/PASEP | F |  |  | N |
| ALIQUOTA | Alíquota | F |  |  | N |
| ALIQUOTAALIEFE | Alíquota | F |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| NUAPURACAO | Nro. Apuração | I |  |  | N |
| NUFAIXA | Nro. da Faixa | I |  |  | N |
| NUFAIXAALIEFE | Nro. da Faixa | I |  |  | N |
| NUPARTILHA | Nro. Único Partilha/Anexo | I |  |  | N |
| TIPOMERCADO | Tipo de Mercado | S |  |  | N |
| TIPOMERCADOALIEFE | Tipo de Mercado | S |  |  | N |
| TIPOSN | Tipo de Partilha/Anexo | I |  |  | N |
| TIPOSNALIEFE | Tipo de Partilha/Anexo | I |  |  | N |
| VLRRECBRUTA | Receita Bruta (Limite Superior) | F |  |  | N |
| VLRRECBRUTAALIEFE | Receita Bruta (Limite Superior) | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PartilhaSimplesNacXEmp / [TGFEMPSN](TGFEMPSN.md) | PartilhaSimplesNacional / [TGFPSN](TGFPSN.md) | NUPARTILHA->NUPARTILHA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
