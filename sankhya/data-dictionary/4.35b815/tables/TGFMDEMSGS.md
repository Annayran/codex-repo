# TGFMDEMSGS — Mensagens MD-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MensagensMDe | Mensagens MD-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVEACESSO | Chave NF-e | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| DHRESP | Dh Resposta | H |  |  | N |
| MOTIVORESP | Motivo Resposta | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| STATUSRESP | Cód Status Resposta | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MensagensMDe / [TGFMDEMSGS](TGFMDEMSGS.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
