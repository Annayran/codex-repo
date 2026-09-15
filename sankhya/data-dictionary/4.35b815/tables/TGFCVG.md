# TGFCVG — Comissão Vendedor Grupo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ComissaoVendedorGrupo | Comissão por Grupo/Perfil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPOPROD | Grupo de Produtos | I |  |  | N |
| CODTIPPARC | Perfil Principal Parceiro | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| COMISSAO | Comissão do Vendedor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ComissaoVendedorGrupo / [TGFCVG](TGFCVG.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| ComissaoVendedorGrupo / [TGFCVG](TGFCVG.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| Vendedor / [TGFVEN](TGFVEN.md) | ComissaoVendedorGrupo / [TGFCVG](TGFCVG.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
