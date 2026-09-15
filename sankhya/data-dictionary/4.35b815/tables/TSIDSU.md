# TSIDSU — DataSource por Usuário

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DataSourceUsuario | DataSource por Usuário | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| DATASOURCE | Fonte de dados | S |  |  | N |
| NUGDG | Componente de BI | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DataSourceUsuario / [TSIDSU](TSIDSU.md) | Gadget / [TSIGDG](TSIGDG.md) | NUGDG->NUGDG | I |
| Usuario / [TSIUSU](TSIUSU.md) | DataSourceUsuario / [TSIDSU](TSIDSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
