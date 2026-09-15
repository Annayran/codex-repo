# TGIITE — TGIITE

- Módulo técnico: [TGI](../modules/TGI.md)
- Campos: 60
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemKitPropostaComercial | Item | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQICMS | Alíq.ICMS | F |  |  | N |
| ALIQII | Alíq.II | F |  |  | N |
| ALIQIPI | Alíq.IPI | F |  |  | N |
| CODNBM | Código NBM | S |  |  | N |
| CODPARCFORN | Fornecedor | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| DESCRITEM | Descrição do item | S |  |  | N |
| FATORIMPOSTOS | Fator Impostos s/Venda | F |  |  | S |
| FATORVERNET | Fator Margem | F |  |  | S |
| NUNOTA | Nro Único Pedido | I |  |  | N |
| NUPROJETO | Projeto | I |  |  | N |
| PARTNUMBER | Part Number | S |  |  | S |
| PERCCOFINS | % Cofins | F |  |  | N |
| PERCCSSL | % CSSL | F |  |  | N |
| PERCDESC | % Desc | F |  |  | N |
| PERCFRETE | % Frete | F |  |  | N |
| PERCIMPOSTOSCOM | % Impostos s/Compra | F |  |  | N |
| PERCIMPOSTOSVEN | % Impostos s/Venda | F |  |  | N |
| PERCINTERNACAO | % Internação | F |  |  | N |
| PERCMARGEMPROP | % Margem Lucro | F |  |  | N |
| PERCPIS | % PIS | F |  |  | N |
| QTD | Qtde | F |  |  | N |
| QTDSPARE | Qtde Spare | F |  |  | N |
| RATEADO | RATEADO | S |  |  | S |
| SEQITEM | Sequencia | I |  |  | N |
| SEQKIT | Sequencia Kit | I |  |  | N |
| SEQUENCIA | Seq.Pedido | I |  |  | N |
| SERVICO | Serviço | S |  |  | N |
| TIPKIT | Tipo Kit | S |  |  | N |
| VERSAOPROJETO | Versão | S |  |  | N |
| VLRDESCONTO | Unit. Desc | F |  |  | N |
| VLRIMPOSTOSCOM | Unit Impostos S/Compra | F |  |  | N |
| VLRIMPOSTOSVEN | Unit Impostos S/Venda | F |  |  | N |
| VLRINTERNACAO | Unit Internação | F |  |  | N |
| VLRMARGEM | Unit Margem | F |  |  | N |
| VLRTOTCIF | Total CIF | F |  |  | N |
| VLRTOTCOMMARGEM | Total Final U$ | F |  |  | N |
| VLRTOTCOMSPARE | Total c/Rateio R$ | F |  |  | N |
| VLRTOTDESC | Total Desconto | F |  |  | N |
| VLRTOTFINAL | Total Final R$ | F |  |  | N |
| VLRTOTFINALARRED | Total Arred. R$ | F |  |  | N |
| VLRTOTFOB | Total FOB c/Desc. | F |  |  | N |
| VLRTOTIMPOSTOSCOM | Total Impostos s/Compra | F |  |  | N |
| VLRTOTIMPOSTOSVEN | Total Impostos s/Venda | F |  |  | N |
| VLRTOTINTERNACAO | Total Internação | F |  |  | N |
| VLRTOTMARGEM | Total Margem Vernet | F |  |  | N |
| VLRUNITCIF | Unit CIF | F |  |  | N |
| VLRUNITCOFINS | Unit. Cofins | F |  |  | N |
| VLRUNITCOMMARGEM | Unit Final U$ | F |  |  | N |
| VLRUNITCOMSPARE | Unit c/Rateio R$ | F |  |  | N |
| VLRUNITCSSL | Unit. CSSL | F |  |  | N |
| VLRUNITFINAL | Unit Final R$ | F |  |  | N |
| VLRUNITFINALARRED | Unit Arred. R$ | F |  |  | N |
| VLRUNITFOB | Preço FOB | F |  |  | N |
| VLRUNITFOBDESC | Unit FOB c/Desc. | F |  |  | N |
| VLRUNITFRETE | Unit.Frete | F |  |  | N |
| VLRUNITICMS | Unit. ICMS | F |  |  | N |
| VLRUNITII | Unit.II | F |  |  | N |
| VLRUNITIPI | Unit. IPI | F |  |  | N |
| VLRUNITPIS | Unit. PIS | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemKitPropostaComercial / [TGIITE](TGIITE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| ItemKitPropostaComercial / [TGIITE](TGIITE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| KitPropostaComercial / [TGIKIT](TGIKIT.md) | ItemKitPropostaComercial / [TGIITE](TGIITE.md) | NUPROJETO->NUPROJETO; SEQKIT->SEQKIT; TIPKIT->TIPKIT; VERSAOPROJETO->VERSAOPROJETO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
