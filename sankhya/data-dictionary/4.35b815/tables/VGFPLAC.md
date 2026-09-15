# VGFPLAC — View Cabeçalho Planejamento Entrega

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewCabPlanejamentoEntrega | View Cabeçalho Plan. Entrega | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCONTATO | Contato | I |  |  | N |
| CODPARCFAT | Parceiro | I |  |  | N |
| CODPARCTRANSP | Parceiro Transportadora | I |  |  | N |
| ENTREGUE | Entregue | I |  |  | N |
| NUMNOTADEST | Nro. Nota | I |  |  | N |
| NUNOTADEST | Nro. Único | I |  |  | N |
| NUPLAN | Nro. Planejamento | I |  |  | N |
| ORDEMCARGA | Ordem de Carga | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewCabPlanejamentoEntrega / [VGFPLAC](VGFPLAC.md) | ApontamentoEntrega / [TGFAEC](TGFAEC.md) | CODCONTATO->CODCONTATO; CODPARCFAT->CODPARCFAT; NUPLAN->NUPLAN | I |
| ViewCabPlanejamentoEntrega / [VGFPLAC](VGFPLAC.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO | I |
| ViewCabPlanejamentoEntrega / [VGFPLAC](VGFPLAC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFAT->CODPARC | I |
| ViewCabPlanejamentoEntrega / [VGFPLAC](VGFPLAC.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
