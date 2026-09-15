# TGFEEP — Etapa da  Estrutura de Produção

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EtapaEstruturaProducao | Etapas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABREVIACAO | Abreviação | S |  |  | N |
| CODEST | Estrutura de Produção | I |  |  | N |
| CODETP | Código | I |  |  | N |
| COMPART | Compartilha Produção | S |  |  | N |
| CQL | CQL (Laudo) | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| GERAPRODUCAO | Gera Produção | S |  |  | N |
| LEADTIME | Lead Time | I |  |  | N |
| LEADTIMECQL | Lead Time CQL | I |  |  | N |
| TOPPROD | TOP Produção | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EstruturaProducao / [TGFESP](TGFESP.md) | EtapaEstruturaProducao / [TGFEEP](TGFEEP.md) | CODEST->CODEST | I |
| EtapaEstruturaProducao / [TGFEEP](TGFEEP.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | TOPPROD->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
