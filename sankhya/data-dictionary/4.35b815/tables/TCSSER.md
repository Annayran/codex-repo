# TCSSER — Produtos e Serviços

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoServico | Produtos e Serviços | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPROD | Serviço | I |  |  | N |
| FREQUENCIA | Freqüência | I |  |  | N |
| LIMITANTE | Limitante | S |  |  | N |
| QTDEPREVISTA | Quantidade prevista | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| USAOSPORTAL | Usado em OS no portal | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Natureza / [TGFNAT](TGFNAT.md) | ProdutoServico / [TCSSER](TCSSER.md) | CODNAT->CODNAT | I |
| ProdutoServico / [TCSSER](TCSSER.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ProdutoServico / [TCSSER](TCSSER.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
