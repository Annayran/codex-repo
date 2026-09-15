# TGFIMC — Cadastro de Imposto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 24
- Instâncias DWF: 1
- Relações DWF: 12
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Imposto | Imposto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACUMBASEICMS | Acumular na Base ICMS? | S |  |  | N |
| ACUMBASEIPI | Acumular na Base IPI? | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| BASEIMPFIN | Base do Imposto no Financeiro | S |  |  | N |
| CALCBASDIA | Calcular com Base Diária? | S |  |  | N |
| CALCBASEMENSAL | Calcular com Base Mensal? | S |  |  | N |
| CODCTACTB1 | Conta Contábil 1 | I |  |  | N |
| CODCTACTB2 | Conta Contábil 2 | I |  |  | N |
| CODIMP | Código | I |  |  | N |
| CODREC | Código Receita (Darf) | S |  |  | N |
| CODTAB | Tab.p/Pauta | I |  |  | N |
| CONSIDERARIMP | Considerar Impostos | S |  |  | N |
| DESCRICAO | Descrição do Imposto | S |  |  | N |
| F600EFD | Gerar reg. F600 p/EFD Contribuições | S |  |  | N |
| GRUPOVLRMIN | Grupo Vlr Mínimo | S |  |  | N |
| IMPBAIXAPARCIAL | Destaque do Imposto | S |  |  | N |
| IMPFRETE | Proporcionalizar imposto dos itens ao Frete | S |  |  | N |
| NOMEIMP | Nome do Imposto | S |  |  | N |
| REGCALCIMPRET | Registrar cálculo quando o imposto retido for inferior ao valor mínimo? | S |  |  | N |
| REGRA | Tipo | S |  |  | N |
| TIPOIMPOSTO | Tipo Imposto | I |  |  | N |
| USARPRECOCUSTO | Usar Preço de Custo | S |  |  | N |
| VLRMAXINSS | Valor Maxímo INSS | F |  |  | N |
| VLRMIN | Valor Mínimo do Imposto | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaImposto / [TGFIMA](TGFIMA.md) | Imposto / [TGFIMC](TGFIMC.md) | CODIMP->CODIMP | I |
| AliquotaImpostoGrupoProd / [TGFIMA](TGFIMA.md) | Imposto / [TGFIMC](TGFIMC.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoEmpresa / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoGrupoProd / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoParceiro / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoProd / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoServico / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoTOP / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | PlanoContaEmpresa /  | CODCTACTB1->CODCTACTB | I |
| Imposto / [TGFIMC](TGFIMC.md) | PlanoContaEmpresa2 /  | CODCTACTB2->CODCTACTB | I |
| ImpostoFinanceiro / [TGFIMF](TGFIMF.md) | Imposto / [TGFIMC](TGFIMC.md) | CODIMP->CODIMP | I |
| ImpostoNota / [TGFIMN](TGFIMN.md) | Imposto / [TGFIMC](TGFIMC.md) | CODIMP->CODIMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
