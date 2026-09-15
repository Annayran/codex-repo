# TGFEFR — Registros Escrituracao Fiscal Digital

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegistrosEscrituracaoFiscal | Registros de Escrituração Fiscal Digital | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BLOCO | Bloco | S |  |  | N |
| CODEMP | CODEMP | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| GERARENTRADA | Gerar Entrada | S |  |  | N |
| GERARREGISTRO | Gerar Registro | S |  |  | N |
| GERARSAIDA | Gerar Saída | S |  |  | N |
| REGISTRO | Registro | S |  |  | N |
| TIPO | Tipo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BlocosEscrituracaoFiscal / [TGFEFB](TGFEFB.md) | RegistrosEscrituracaoFiscal / [TGFEFR](TGFEFR.md) | BLOCO->BLOCO; CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RegistrosEscrituracaoFiscal / [TGFEFR](TGFEFR.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
