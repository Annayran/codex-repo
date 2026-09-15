# TSICTLG — Controle de tentativas de login

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleTentativaLogin | Controle de tentativas de login | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHBLOQUEIO | Dh. bloqueio | H |  |  | N |
| DHDESBLOQUEIO | Dh. desbloqueio | H |  |  | N |
| DHULTFALHA | Dh. última falha | H |  |  | N |
| DHULTLOGIN | Dh. último login | H |  |  | N |
| QTDTENTATIVAS | Qtd. tentativas | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleTentativaLogin / [TSICTLG](TSICTLG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
