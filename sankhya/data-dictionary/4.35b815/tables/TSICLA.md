# TSICLA — Classificação de campos proteção de dados

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ClassificacaoLgpd | Classificação de Dados | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLA | Código da Classificação | I |  |  | N |
| CODUSUALTER | Código usuário alteração | I |  |  | N |
| CODUSUCRIAC | Código usuário criação | I |  |  | N |
| DESCCLA | Descrição da Classificação | S |  |  | N |
| DTALTER | Data alteração | H |  |  | N |
| DTCRIAC | Data criação | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExcecoesCamposLgpd / [TSIEXC](TSIEXC.md) | ClassificacaoLgpd / [TSICLA](TSICLA.md) | CODCLA->CODCLA | I |
| ExcecoesGruposLgpd / [TSIEXG](TSIEXG.md) | ClassificacaoLgpd / [TSICLA](TSICLA.md) | CODCLA->CODCLA | I |
| ExcecoesUsuariosLgpd / [TSIEXU](TSIEXU.md) | ClassificacaoLgpd / [TSICLA](TSICLA.md) | CODCLA->CODCLA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
