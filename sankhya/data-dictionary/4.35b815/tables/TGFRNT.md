# TGFRNT — Tabela Reintegra Previdência

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ReintegraPrevidencia | Reintegra Previdência | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQCONT | Alíquota | F |  |  | N |
| CNAE | Código Atividade Prest. Serviços e Produtos | S |  |  | N |
| CODCTACTB | Código Conta Contábil | I |  |  | N |
| CODEMP | Código Empresa | I |  |  | N |
| CODREC | Código de Recolhimento | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCATIVIDADE | Desc. Atividade Prest. Serviços e Produtos | S |  |  | N |
| DHALTER | Data Alteração | H |  |  | N |
| DTFIM | Dt. Final | D |  |  | N |
| DTINI | Dt. Inicial | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ReintegraPrevidencia / [TGFRNT](TGFRNT.md) | CODEMP->CODEMP | I |
| ReintegraPrevidencia / [TGFRNT](TGFRNT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| ReintegraPrevidencia / [TGFRNT](TGFRNT.md) | PlanoContaEmpresa /  | CODCTACTB->CODCTACTB | I |
| ReintegraPrevidencia / [TGFRNT](TGFRNT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
