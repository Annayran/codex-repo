# TSIPRN — Impressoras

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Impressora | Impressoras | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIASLOCAL | Nome Local | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| DHSTATUS | Data de Verificação | H |  |  | N |
| NOME | Nome | S |  |  | N |
| NUPRINTER | Código | I |  |  | N |
| NUSVP | Servidor de impressão | I |  |  | N |
| PRINTERURI | Endereço | S |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Impressora / [TSIPRN](TSIPRN.md) | AcessoImpressora / [TSIAPRN](TSIAPRN.md) | NUPRINTER->NUPRINTER | I |
| ServidorImpressao / [TSISVP](TSISVP.md) | Impressora / [TSIPRN](TSIPRN.md) | NUSVP->NUSVP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
