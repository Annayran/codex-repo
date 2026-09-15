# TSIACD — TABLE TSIACD

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleAcessoDashboardsGOL | Controle Acessos de Dashboards Personalizados no GOL | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPO | Cód. Grupo | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONS | Consultar | S |  |  | N |
| NUDSB | NUDSB | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleAcessoDashboardsGOL / [TSIACD](TSIACD.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| ControleAcessoDashboardsGOL / [TSIACD](TSIACD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
