# TWFGPR — Gestor de Processo

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GestorProcesso | Gestores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGESTOR | Gestor | I |  |  | N |
| CODPRN | Código do Processo de Négocio | I |  |  | N |
| SEQUENCIA | Seq. Gestor | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GestorProcesso / [TWFGPR](TWFGPR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODGESTOR->CODUSU | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | GestorProcesso / [TWFGPR](TWFGPR.md) | CODPRN->CODPRN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
