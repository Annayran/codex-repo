# TSIIUF — Inscrição de Unidade Federativa

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InscricaoUnidadeFederativa | Inscrição da Unidade Federativa | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODUF | Cod. UF | I |  |  | N |
| DTFIMVALIDADE | Data Fim Validade | D |  |  | N |
| DTINIVALIDADE | Data Início Validade | D |  |  | N |
| INSCRICAO | Inscrição Estadual | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | InscricaoUnidadeFederativa / [TSIIUF](TSIIUF.md) | CODEMP->CODEMP | I |
| InscricaoUnidadeFederativa / [TSIIUF](TSIIUF.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
