# TGFPPA — Parceiro Contato e Tipo de Parceiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 2
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PerfilContato | Perfil do Contato | S | S |
| PerfilParceiro | Perfil do Parceiro | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCONTATO | Contato | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODTIPPARC | Perfil | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contato / [TGFCTT](TGFCTT.md) | PerfilContato / [TGFPPA](TGFPPA.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | L |
| Parceiro / [TGFPAR](TGFPAR.md) | PerfilParceiro / [TGFPPA](TGFPPA.md) | CODPARC->CODPARC | L |
| Perfil / [TGFTPP](TGFTPP.md) | PerfilContato / [TGFPPA](TGFPPA.md) | CODTIPPARC->CODTIPPARC | I |
| Perfil / [TGFTPP](TGFTPP.md) | PerfilParceiro / [TGFPPA](TGFPPA.md) | CODTIPPARC->CODTIPPARC | I |
| PerfilContato / [TGFPPA](TGFPPA.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| PerfilContato / [TGFPPA](TGFPPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PerfilContato / [TGFPPA](TGFPPA.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| PerfilParceiro / [TGFPPA](TGFPPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PerfilParceiro / [TGFPPA](TGFPPA.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
