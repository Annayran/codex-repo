# TSIHCF — Histórico de Cópia de Configuração

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoCopiaConfiguracao | Histórico de Cópia Configuração | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABASEG | Conf. Segurança | S |  |  | N |
| CHAVE | Chave | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONFTOP | Configurações de Top | S |  |  | N |
| DHALTER | Data Criação | H |  |  | N |
| NUNICO | Núm. Único | I |  |  | N |
| REMOVEANT | Remover Conf. Atuais | S |  |  | N |
| SELECAOTELAS | Seleção de telas | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoCopiaConfiguracao / [TSIHCF](TSIHCF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
