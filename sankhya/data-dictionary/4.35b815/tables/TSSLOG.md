# TSSLOG — Log de Instalações Pacotes Sankhya Store

- Módulo técnico: [TSS](../modules/TSS.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LogInstalacoesSankhyaStore | Log Histórico de Instalação Pacotes Sankhya Store | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVESS | Chave Sankhya Store | S |  |  | N |
| CODHIS | Cód. Histórico | I |  |  | N |
| CODLOG | Cód. Log | I |  |  | N |
| NOMEINSREG | Tipo Item Instalação | S |  |  | N |
| NOMEITE | Nome Item | S |  |  | N |
| PKREG | Chave Item Instalação | S |  |  | N |
| PRIVADO | Privado | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoSankhyaStore / [TSSHIS](TSSHIS.md) | LogInstalacoesSankhyaStore / [TSSLOG](TSSLOG.md) | CODHIS->CODHIS | I |
| LogInstalacoesSankhyaStore / [TSSLOG](TSSLOG.md) | HistoricoSankhyaStore / [TSSHIS](TSSHIS.md) | CODHIS->CODHIS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
