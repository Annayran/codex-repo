# TGFSEU — Relacao de servicos e usuarios

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ServicoProdutoExecutante | ServicoProdutoExecutante | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODSERV | Serviço | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ServicoProdutoExecutante / [TGFSEU](TGFSEU.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ServicoProdutoExecutante / [TGFSEU](TGFSEU.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| Usuario / [TSIUSU](TSIUSU.md) | ServicoProdutoExecutante / [TGFSEU](TGFSEU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
