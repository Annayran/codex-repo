# TGFDFA — Descontos Finânceiros Aplicados

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescontoFinanceiroAplicado | Descontos Financeiros Aplicados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODDESC | Cód. Desconto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DIFCONTRATPLICAD | Desc. Contr. - Desc. Aplic. | F |  |  | S |
| DTALTER | Data de Alteração | H |  |  | N |
| NUFIN | Número único Financeiro | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| VLRDESC | Valor de Desconto | F |  |  | N |
| VLRDESCCONTRATADO | Valor de Desconto do Contrato | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DescontoFinanceiro / [TGFDFN](TGFDFN.md) | DescontoFinanceiroAplicado / [TGFDFA](TGFDFA.md) | CODDESC->CODDESC; NUFIN->NUFIN | I |
| DescontoFinanceiroAplicado / [TGFDFA](TGFDFA.md) | DescontoFinanceiro / [TGFDFN](TGFDFN.md) | CODDESC->CODDESC; NUFIN->NUFIN | I |
| DescontoFinanceiroAplicado / [TGFDFA](TGFDFA.md) | DescontoFinanceiroParceiro / [TGFDFI](TGFDFI.md) | CODDESC->CODDESC | I |
| DescontoFinanceiroAplicado / [TGFDFA](TGFDFA.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| DescontoFinanceiroAplicado / [TGFDFA](TGFDFA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
