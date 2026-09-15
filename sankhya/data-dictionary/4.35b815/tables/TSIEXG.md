# TSIEXG — Exceções para Proteção de Dados

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExcecoesGruposLgpd | Exceções para Proteção de Dados | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLA | Código da classificação | I |  |  | N |
| CODGRUPO | Código de grupo do usuário | I |  |  | N |
| TIPOVISU | Tipo de visualização | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExcecoesGruposLgpd / [TSIEXG](TSIEXG.md) | ClassificacaoLgpd / [TSICLA](TSICLA.md) | CODCLA->CODCLA | I |
| ExcecoesGruposLgpd / [TSIEXG](TSIEXG.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| GrupoUsuario / [TSIGRU](TSIGRU.md) | ExcecoesGruposLgpd / [TSIEXG](TSIEXG.md) | CODGRUPO->CODGRUPO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
