# VGFPCP — View Perfil Contato

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewPerfilContatoParceiro | Perfil Contato | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APELIDO | Apelido | S |  |  | N |
| CARGO | Cargo | S |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODTIPPARC | Perfil | I |  |  | N |
| EMAIL | E-Mail | S |  |  | N |
| NOMEPARC | Nome Parceiro | S |  |  | N |
| RAZAOSOCIAL | Razão Social | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Perfil / [TGFTPP](TGFTPP.md) | ViewPerfilContatoParceiro / [VGFPCP](VGFPCP.md) | CODTIPPARC->CODTIPPARC | I |
| ViewPerfilContatoParceiro / [VGFPCP](VGFPCP.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
