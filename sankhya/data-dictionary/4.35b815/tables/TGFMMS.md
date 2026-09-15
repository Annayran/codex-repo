# TGFMMS — GF Movimento Mensal Sintético

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MovimentoMensalSintetico | Movimento Mensal Sintético | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANO | Ano | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODGRUPOPROD | Grupo Produtos | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| DTAM | Data ano e mês | H |  |  | N |
| GRUPO | Grupo | S |  |  | N |
| MES | Mês | I |  |  | N |
| TIPMOV | Tipo de Movimento | S |  |  | N |
| VLRMOV | Valor do movimento | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MovimentoMensalSintetico / [TGFMMS](TGFMMS.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| MovimentoMensalSintetico / [TGFMMS](TGFMMS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
