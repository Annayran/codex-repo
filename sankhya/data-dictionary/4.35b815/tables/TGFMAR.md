# TGFMAR — Marcas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MarcaProduto | Marca | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | Código | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AutorizacaoAcessoXmlNFeMarca / [TGFAAXNM](TGFAAXNM.md) | MarcaProduto / [TGFMAR](TGFMAR.md) | CODMARCA->CODIGO | I |
| Produto / [TGFPRO](TGFPRO.md) | MarcaProduto / [TGFMAR](TGFMAR.md) | CODMARCA->CODIGO | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | MarcaProduto / [TGFMAR](TGFMAR.md) | CODMARCA->CODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
