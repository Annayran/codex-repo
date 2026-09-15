# TSIFCM — Tabela de registro de dispositivos mobile para envio de notificação do sistema para os aplicativos

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
| UsuarioFirebaseMessaging | Usuario Firebase Messaging | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAPP | Aplicação | S |  |  | N |
| CODFCM | Código FCM | S |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DHREGISTRO | Dt./Hr. Registro | H |  |  | N |
| PLATAFORMA | Plataforma | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| UsuarioFirebaseMessaging / [TSIFCM](TSIFCM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
