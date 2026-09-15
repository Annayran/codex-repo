# TSISVP — Servidores de Impressão

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ServidorImpressao | Servidores de Impressão | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODUSUINC | Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| NUSVP | Código | I |  |  | N |
| PORTA | Porta | I |  |  | N |
| URL | Nome ou IP | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ServidorImpressao / [TSISVP](TSISVP.md) | AcessoServidorImpressao / [TSIAPRN](TSIAPRN.md) | NUSVP->NUSVP | I |
| ServidorImpressao / [TSISVP](TSISVP.md) | Impressora / [TSIPRN](TSIPRN.md) | NUSVP->NUSVP | I |
| ServidorImpressao / [TSISVP](TSISVP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUINC->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
