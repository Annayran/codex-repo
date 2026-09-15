# TGFTPP — Tipos de Parceiros

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 2
- Relações DWF: 22
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Perfil | Perfil | S | S |
| PerfilTabPreco | Perfil Tabela de Preço | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODANT | SGE | I |  |  | N |
| CODTAB | Tabela de preço | I |  |  | N |
| CODTIPPARC | Cód. Perfil | I |  |  | N |
| CODTIPPARCPAI | Cód. Perfil Pai | I |  |  | N |
| CODUSUALTREG | Cód. Usuário Alteração | I |  |  | N |
| DESCRTIPPARC | Descrição | S |  |  | N |
| DHALTREG | Dt. Alteração | H |  |  | N |
| GRAU | Grau | I |  |  | N |
| TIPANT | Tipo no SGE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| ComissaoVendedorGrupo / [TGFCVG](TGFCVG.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| GradeProduto / [TGFGRA](TGFGRA.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| MetaAtual / [TGMMET](TGMMET.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| NomeTabelasPreco / [TGFNTA](TGFNTA.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| Perfil / [TGFTPP](TGFTPP.md) | GradeProduto / [TGFGRA](TGFGRA.md) | CODTIPPARC->CODTIPPARC | I |
| Perfil / [TGFTPP](TGFTPP.md) | PerfilContato / [TGFPPA](TGFPPA.md) | CODTIPPARC->CODTIPPARC | I |
| Perfil / [TGFTPP](TGFTPP.md) | PerfilParceiro / [TGFPPA](TGFPPA.md) | CODTIPPARC->CODTIPPARC | I |
| Perfil / [TGFTPP](TGFTPP.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| Perfil / [TGFTPP](TGFTPP.md) | ViewPerfilContatoParceiro / [VGFPCP](VGFPCP.md) | CODTIPPARC->CODTIPPARC | I |
| Perfil / [TGFTPP](TGFTPP.md) | ViewPerfilGradeProduto / [VGFGRA](VGFGRA.md) | CODTIPPARC->CODTIPPARC | I |
| Perfil / [TGFTPP](TGFTPP.md) | ViewPerfilParceiro / [VGFPPA](VGFPPA.md) | CODTIPPARC->CODTIPPARC | I |
| Perfil / [TGFTPP](TGFTPP.md) | ViewTabelaPrecos / [VGFTAB](VGFTAB.md) | CODTAB->CODTAB | I |
| PerfilContato / [TGFPPA](TGFPPA.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| PerfilParceiro / [TGFPPA](TGFPPA.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| PerfilProspect / [TCSPPP](TCSPPP.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| PerfilTabPreco / [TGFTPP](TGFTPP.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
