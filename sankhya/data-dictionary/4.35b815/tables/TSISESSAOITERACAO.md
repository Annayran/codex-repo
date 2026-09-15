# TSISESSAOITERACAO — Sessão de iteração

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
| SessaoIteracaoTela | Sessão Iteracao Tela | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| DHINICIO | Dh. Início | H |  |  | N |
| DHULTIMAATIVIDADE | Última atividade | H |  |  | N |
| QTDITERACOES | Qtd. acesso | I |  |  | N |
| RESOURCEID | ResourceID | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| SessaoIteracaoTela / [TSISESSAOITERACAO](TSISESSAOITERACAO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
