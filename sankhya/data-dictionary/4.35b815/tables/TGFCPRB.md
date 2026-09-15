# TGFCPRB — Tabela Atividades, Prods. e Sers. p/ CPRB

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TabelaCPRB | Tabela Atividades, Prods. e Sers. p/ CPRB | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQUOTA | Alíquota | F |  |  | N |
| CODCPRB | Cód. Atividade (Prest. Serviços e Produtos) | S |  |  | N |
| CODCTACTB | Conta contábil | I |  |  | N |
| CODRECOLHIMENTO | Código de Recolhimento | I |  |  | N |
| DESCATIVIDADE | Desc. Atividade (Prest. Serviços e Produtos) | S |  |  | N |
| DTFINAL | Data Final | D |  |  | N |
| DTINICIAL | Data Inicial | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | TabelaCPRB / [TGFCPRB](TGFCPRB.md) | CODCPRB->CODCPRB | I |
| Servico / [TGFPRO](TGFPRO.md) | TabelaCPRB / [TGFCPRB](TGFCPRB.md) | CODCPRB->CODCPRB | I |
| TabelaCPRB / [TGFCPRB](TGFCPRB.md) | PlanoContaEmpresa /  | CODCTACTB->CODCTACTB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
