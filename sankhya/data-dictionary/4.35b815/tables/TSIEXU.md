# TSIEXU — Exceções para usuários LGPD

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExcecoesUsuariosLgpd | Exceções Usuários LGPD | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLA | Código da classificação | I |  |  | N |
| CODUSU | Código usuário | I |  |  | N |
| TIPOVISU | Tipo de visualização | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExcecoesUsuariosLgpd / [TSIEXU](TSIEXU.md) | ClassificacaoLgpd / [TSICLA](TSICLA.md) | CODCLA->CODCLA | I |
| ExcecoesUsuariosLgpd / [TSIEXU](TSIEXU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
