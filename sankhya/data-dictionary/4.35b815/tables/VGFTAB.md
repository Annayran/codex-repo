# VGFTAB — ViewTabelaPrecos

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewTabelaPrecos | Tabela de preços | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | ATIVO | S |  |  | N |
| CODMOEDA | CODMOEDA | I |  |  | N |
| CODREG | CODREG | I |  |  | N |
| CODTAB | CODTAB | I |  |  | N |
| CODTABFLEX | CODTABFLEX | I |  |  | N |
| CODTABORIG | CODTABORIG | I |  |  | N |
| CODTIPPARC | CODTIPPARC | I |  |  | N |
| DECVENDA | DECVENDA | I |  |  | N |
| DTALTER | DTALTER | H |  |  | N |
| DTVIGOR | DTVIGOR | H |  |  | N |
| FORMULA | FORMULA | S |  |  | N |
| NOMETAB | NOMETAB | S |  |  | N |
| NUTAB | NUTAB | I |  |  | N |
| OBS | OBS | S |  |  | N |
| PERCENTUAL | PERCENTUAL | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Perfil / [TGFTPP](TGFTPP.md) | ViewTabelaPrecos / [VGFTAB](VGFTAB.md) | CODTAB->CODTAB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
