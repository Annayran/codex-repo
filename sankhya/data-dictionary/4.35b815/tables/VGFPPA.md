# VGFPPA — View Perfil Parceiro

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewPerfilParceiro | Perfil Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| CODTIPPARC | Perfil | I |  |  | N |
| EMAIL | E-Mail | S |  |  | N |
| NOMEPARC | Nome Parceiro | S |  |  | N |
| RAZAOSOCIAL | Razão Social | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Perfil / [TGFTPP](TGFTPP.md) | ViewPerfilParceiro / [VGFPPA](VGFPPA.md) | CODTIPPARC->CODTIPPARC | I |
| ViewPerfilParceiro / [VGFPPA](VGFPPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
