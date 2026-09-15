# TIMLPP — TABLE TIMLPP

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimInteresseProspect | Interesses do Prospect | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LPPBUSCA | Cod. Busca | I |  |  | N |
| LPPDHINC | Dh. Inclusão | H |  |  | N |
| LPPRESULTADOS | Resultados | I |  |  | N |
| LPPUSUARIO | Usuário Logado | I |  |  | N |
| LPPXML | Filtros | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimInteresseProspect / [TIMLPP](TIMLPP.md) | Usuario / [TSIUSU](TSIUSU.md) | LPPUSUARIO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
