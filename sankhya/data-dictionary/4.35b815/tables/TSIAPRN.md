# TSIAPRN — Acesso ao servidor de impressão e impressoras

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 2
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcessoImpressora | Controle de acesso | S | N |
| AcessoServidorImpressao | Controle de acesso | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | Usuário ou Grupo | I |  |  | N |
| NOME | Nome usuário/grupo | S |  |  | S |
| NUPRINTER | Impressora | I |  |  | N |
| NUSVP | Servidor de impressão | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Impressora / [TSIPRN](TSIPRN.md) | AcessoImpressora / [TSIAPRN](TSIAPRN.md) | NUPRINTER->NUPRINTER | I |
| ServidorImpressao / [TSISVP](TSISVP.md) | AcessoServidorImpressao / [TSIAPRN](TSIAPRN.md) | NUSVP->NUSVP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
