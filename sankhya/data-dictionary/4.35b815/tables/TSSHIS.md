# TSSHIS — Histórico Instalações Sankhya Store

- Módulo técnico: [TSS](../modules/TSS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoSankhyaStore | Histórico de Instalações de pacotes Sankhya Store | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APPKEY | App Key | S |  |  | N |
| CHAVESS | Chave no Sankhya Store | S |  |  | N |
| CODHIS | Código Histórico | I |  |  | N |
| CODUSUCAD | Usuário Instalador | I |  |  | N |
| DHINST | Data Instalação | H |  |  | N |
| NOMEPAC | Nome Pacote | S |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoSankhyaStore / [TSSHIS](TSSHIS.md) | LogInstalacoesSankhyaStore / [TSSLOG](TSSLOG.md) | CODHIS->CODHIS | I |
| HistoricoSankhyaStore / [TSSHIS](TSSHIS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUCAD->CODUSU | I |
| LogInstalacoesSankhyaStore / [TSSLOG](TSSLOG.md) | HistoricoSankhyaStore / [TSSHIS](TSSHIS.md) | CODHIS->CODHIS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
