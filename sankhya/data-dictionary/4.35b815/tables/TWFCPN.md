# TWFCPN — Compartilhamento de Processos

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CompartilhamentoProcesso | Compartilhamento de Processo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPRN | Cód. Processo | I |  |  | N |
| CODUSU | Cód. Usuário/Grupo/Equipe | I |  |  | N |
| PERMITECOMPARTILHAR | Permite Compartilhar | S |  |  | N |
| PERMITEEDICAO | Permite Edição | S |  |  | N |
| PERMITEINICIAR | Permite Iniciar Processo | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPUSUGRU | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CompartilhamentoProcesso / [TWFCPN](TWFCPN.md) | ViewUsuarioGrupo / [VSIUSU](VSIUSU.md) | CODUSU->CODUSU; TIPUSUGRU->TIPUSUGRU | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | CompartilhamentoProcesso / [TWFCPN](TWFCPN.md) | CODPRN->CODPRN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
