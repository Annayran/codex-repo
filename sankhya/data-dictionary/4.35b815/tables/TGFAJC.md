# TGFAJC — Tabela de Controle Créditos Fiscais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleCreditosFiscais | Controle Créditos Fiscais | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. usuário | I |  |  | N |
| CREDAPROPRIADO | Crédito Apropriado | F |  |  | N |
| CREDRECEBIDO | Crédito Recebido | F |  |  | N |
| CREDUTILIZADO | Crédito Utilizados | F |  |  | N |
| DTALTER | Dt. Alteração | D |  |  | N |
| NUAJA | Nro. único ajuste | I |  |  | N |
| SALDOCREDANT | Saldo Créd. Ant. | F |  |  | N |
| SALDOCREDPROX | Saldo Créd. Prox. | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleCreditosFiscais / [TGFAJC](TGFAJC.md) | UtilizacaoCreditosFiscais / [TGFAJU](TGFAJU.md) | NUAJA->NUAJA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
