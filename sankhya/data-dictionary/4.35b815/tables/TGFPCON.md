# TGFPCON — Tabela de Peso da Conferência

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PesoConferencia | Peso da Conferência | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPCON | Código | I |  |  | N |
| NUCONF | Nro conferência | I |  |  | N |
| PESOBRUTO | Peso bruto | F |  |  | N |
| PESOLIQUIDO | Peso líquido | F |  |  | N |
| SEQCONF | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PesoConferencia / [TGFPCON](TGFPCON.md) | DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | NUCONF->NUCONF; SEQCONF->SEQCONF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
