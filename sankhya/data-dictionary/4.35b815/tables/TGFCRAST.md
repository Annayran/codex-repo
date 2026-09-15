# TGFCRAST — Cabeçalho Rastreamento de Estoque

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoRastreamentoEstoque | Cabeçalho Rastreamento de Estoque | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHFIMRAST | Data Fim do Rastreio | H |  |  | N |
| DHSOLICT | Data do Rastreio | H |  |  | N |
| DTRAST | Data de Cópia/Contagem | H |  |  | N |
| NURAST | Código | I |  |  | N |
| OPCRAST | Opção | I |  |  | N |
| STATUS | Status | S |  |  | N |
| TIPORAST | Tipo do Rastreamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoRastreamentoEstoque / [TGFCRAST](TGFCRAST.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ItemRastreamentoEstoque / [TGFIRAST](TGFIRAST.md) | CabecalhoRastreamentoEstoque / [TGFCRAST](TGFCRAST.md) | NURAST->NURAST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
