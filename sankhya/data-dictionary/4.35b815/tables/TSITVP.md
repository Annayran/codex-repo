# TSITVP — DashboardPlayer

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DashboardPlayer | Dashboard Player | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| NUGDG | Gadget | I |  |  | N |
| NUGDGINT | Gadget interno | I |  |  | N |
| NUTV | Cód. Slide player | I |  |  | N |
| ORDEM | Ordem de apresentação | I |  |  | N |
| TEMPO | Tempo de apresentação (em segundos) | I |  |  | N |
| URL | URL da página | S |  |  | N |
| URLRSS | URL do feed rss | S |  |  | N |
| URLVIDEOYOUTUBE | URL do vídeo do Youtube | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DashboardPlayer / [TSITVP](TSITVP.md) | Gadget / [TSIGDG](TSIGDG.md) | NUGDG->NUGDG | I |
| DashboardPlayer / [TSITVP](TSITVP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | DashboardPlayer / [TSITVP](TSITVP.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
