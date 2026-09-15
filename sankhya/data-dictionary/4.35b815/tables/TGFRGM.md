# TGFRGM — Regime de Apuração da Contribuição Social e de Apropriação de Crédito

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegimeApurContribSocAproCred | Regime de Apuração da Contrib. Social e Aprop. Crédito | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODINCTRIB | Incidência tributária | I |  |  | N |
| CODTIPOCONT | Tipo de Contribuição Apurada | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| CONSMOVDEVVENDF550 | Considerar as Mov. Dev. Vendas no F550 | S |  |  | N |
| CONSVALJURMULF525 | Considera Valores de Juros e Multa na Geração do F525 | S |  |  | N |
| DESCITETERCF525 | Desconsidera Itens com/de Terceiros no F525. | S |  |  | N |
| DHALTER | Dh. Alteração | H |  |  | N |
| DTFIMREGIME | Dt.Final Regime | D |  |  | N |
| DTINIREGIME | Dt.Inicial Regime | D |  |  | N |
| EXCLUIPISCOFINSBASEDEP | Excluir PIS/COFINS da Base | S |  |  | N |
| INDAPROPCRED | Método de apropriação de créditos comuns | I |  |  | N |
| INDREGCUM | Lucro Presumido/Critério Apuração | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RegimeApurContribSocAproCred / [TGFRGM](TGFRGM.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
