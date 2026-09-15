# TGFSER — Série Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SerieProduto | Série Produto | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUALESTOQUE | Atualiza estoque | I |  |  | N |
| AVARIADO | Avariado | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data/Hora da alteração | H |  |  | N |
| NUNOTA | Nro único Nota | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SERIE | Série | S |  |  | N |
| SERIEFAB | Série do fabricante | S |  |  | N |
| SMARTCARD | SmartCard | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemNota / [TGFITE](TGFITE.md) | SerieProduto / [TGFSER](TGFSER.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| SerieProduto / [TGFSER](TGFSER.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| SerieProduto / [TGFSER](TGFSER.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
