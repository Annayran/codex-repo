# TGFDFN — Descontos Finânceiros

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescontoFinanceiro | Descontos Financeiros | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODDESC | Desconto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data da Alteração | H |  |  | N |
| NUFIN | Nro Único Financeiro | I |  |  | N |
| NUNOTA | Nro Único Nota | I |  |  | N |
| VLRDESC | Valor do Desconto | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | DescontoFinanceiro / [TGFDFN](TGFDFN.md) | NUNOTA->NUNOTA | I |
| DescontoFinanceiro / [TGFDFN](TGFDFN.md) | DescontoFinanceiroAplicado / [TGFDFA](TGFDFA.md) | CODDESC->CODDESC; NUFIN->NUFIN | I |
| DescontoFinanceiro / [TGFDFN](TGFDFN.md) | DescontoFinanceiroParceiro / [TGFDFI](TGFDFI.md) | CODDESC->CODDESC | I |
| DescontoFinanceiroAplicado / [TGFDFA](TGFDFA.md) | DescontoFinanceiro / [TGFDFN](TGFDFN.md) | CODDESC->CODDESC; NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
