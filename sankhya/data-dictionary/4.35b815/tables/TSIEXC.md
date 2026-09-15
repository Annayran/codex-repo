# TSIEXC — Exceções de Campos Proteção de Dados

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExcecoesCamposLgpd | Exceções campos proteção de dados | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLA | Código da classificação da proteção de dados | I |  |  | N |
| CODUSU | Código do usuário | I |  |  | N |
| NOMECAMPO | Nome do campo dicionário de dados | S |  |  | N |
| NOMETAB | Nome tabela da instância | S |  |  | N |
| TIPOVISU | Tipo de visualização | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExcecoesCamposLgpd / [TSIEXC](TSIEXC.md) | ClassificacaoLgpd / [TSICLA](TSICLA.md) | CODCLA->CODCLA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
