# TGFRCD — Sequencia Entrega

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SequenciaEntrega | Sequência de Entrega | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCID | Cód. Cidade | I |  |  | N |
| CODROTA | Rota | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data alteração | H |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Cidade / [TSICID](TSICID.md) | SequenciaEntrega / [TGFRCD](TGFRCD.md) | CODCID->CODCID | I |
| Rota / [TGFROT](TGFROT.md) | SequenciaEntrega / [TGFRCD](TGFRCD.md) | CODROTA->CODROTA | I |
| SequenciaEntrega / [TGFRCD](TGFRCD.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| SequenciaEntrega / [TGFRCD](TGFRCD.md) | Rota / [TGFROT](TGFROT.md) | CODROTA->CODROTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
