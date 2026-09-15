# TGFDFI — Descontos Financeiros por Parceiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescontoFinanceiroParceiro | Descontos Financeiros por Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODDESC | Código | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRDESC | Descrição | S |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DescontoFinanceiro / [TGFDFN](TGFDFN.md) | DescontoFinanceiroParceiro / [TGFDFI](TGFDFI.md) | CODDESC->CODDESC | I |
| DescontoFinanceiroAplicado / [TGFDFA](TGFDFA.md) | DescontoFinanceiroParceiro / [TGFDFI](TGFDFI.md) | CODDESC->CODDESC | I |
| DescontoFinanceiroParceiro / [TGFDFI](TGFDFI.md) | DescontoFinanceiroParcPeriodo / [TGFDFP](TGFDFP.md) | CODDESC->CODDESC | I |
| DescontoFinanceiroParcPeriodo / [TGFDFP](TGFDFP.md) | DescontoFinanceiroParceiro / [TGFDFI](TGFDFI.md) | CODDESC->CODDESC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
