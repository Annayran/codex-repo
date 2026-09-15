# TGFCONSER — Série Conferência

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SerieProdutoConferencia | Série Produto | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUALESTOQUE | Atualiza estoque | I |  |  | N |
| AVARIADO | Avariado | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data/Hora da alteração | H |  |  | N |
| NUCONF | Nro conferência | I |  |  | N |
| SEQCONF | Sequência | I |  |  | N |
| SERIE | Série | S |  |  | N |
| SERIEFAB | Série do fabricante | S |  |  | N |
| SMARTCARD | SmartCard | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| SerieProdutoConferencia / [TGFCONSER](TGFCONSER.md) | DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | NUCONF->NUCONF; SEQCONF->SEQCONF | I |
| SerieProdutoConferencia / [TGFCONSER](TGFCONSER.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
