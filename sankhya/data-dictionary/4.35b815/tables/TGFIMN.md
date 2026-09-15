# TGFIMN — GF Imposto na Nota

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImpostoNota | Imposto na Nota | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQUOTA | Alíquota | F |  |  | N |
| BASE | Base | F |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODIMP | Imposto | I |  |  | N |
| CODNATREND | Código Natureza de Rendimento | I |  |  | N |
| CODRECEITA | Cód. Receita | S |  |  | N |
| DIGITADO | Digitado | S |  |  | N |
| NUNOTA | Nro Único Nota | I |  |  | N |
| PERCINSSESPECIAL | % INSS Especial | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPIMP | Tipo Imposto | I |  |  | N |
| TIPOINSSESPECIAL | Tipo de INSS Especial | S |  |  | N |
| TPIRRFEXT | Tributação IRRF - Exterior REINF | I |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRINSSESPECIAL | Vlr. INSS Especial | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImpostoNota / [TGFIMN](TGFIMN.md) | CadastroNaturezaRendimentos /  | CODNATREND->CODNATREND | I |
| ImpostoNota / [TGFIMN](TGFIMN.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| ImpostoNota / [TGFIMN](TGFIMN.md) | Imposto / [TGFIMC](TGFIMC.md) | CODIMP->CODIMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
