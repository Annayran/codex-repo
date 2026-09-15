# VGFCOS — VIEW VGFCOS

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewContatosOS | View Contatos da OS | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACEITA | Aceita | S |  |  | N |
| CODCONTATO | Cód. Contato | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| MOTIVO | Motivo | S |  |  | N |
| NOMECONTATO | Nome | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewContatosOS / [VGFCOS](VGFCOS.md) | OrdemServico / [TCSOSE](TCSOSE.md) | CODCONTATO->CODCONTATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
