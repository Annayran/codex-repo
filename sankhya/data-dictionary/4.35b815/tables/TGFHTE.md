# TGFHTE — GF Histórico de telemarketing

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoTele | Histórico de Telemarketing/Cobrança | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| COBRANCA | Tipo | S |  |  | N |
| CODHIST | Código | I |  |  | N |
| DESCRHIST | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Relacionamento / [TGFTEL](TGFTEL.md) | HistoricoTele / [TGFHTE](TGFHTE.md) | CODHIST->CODHIST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
