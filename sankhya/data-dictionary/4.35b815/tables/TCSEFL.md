# TCSEFL — Executantes por fluxo de processo

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExecutantePorFluxoProcesso | Executantes por fluxo de processo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Cód. Produto | I |  |  | N |
| CODSERV | Cód. Serviço | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |
| VARIACAO | Variação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExecutantePorFluxoProcesso / [TCSEFL](TCSEFL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
