# TPQENV — Envio de e-mails para destinatários

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EnvioQuestionario | Envio de questionários | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVE | CHAVE | I |  |  | N |
| CHAVE2 | CHAVE2 | I |  |  | N |
| CODFILA | CODFILA | I |  |  | N |
| DHULTENVIO | DHULTENVIO | H |  |  | N |
| EMAIL | EMAIL | S |  |  | N |
| NUINSTANCIA | NUINSTANCIA | I |  |  | N |
| NUPLA | NUPLA | I |  |  | N |
| QTDENVIO | QTDENVIO | I |  |  | N |
| RESPONDIDO | RESPONDIDO | S |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DestinatarioPlanejamento / [TPQDPL](TPQDPL.md) | EnvioQuestionario / [TPQENV](TPQENV.md) | CHAVE->CHAVE; CHAVE2->CHAVE2; NUINSTANCIA->NUINSTANCIA; NUPLA->NUPLA | I |
| EnvioQuestionario / [TPQENV](TPQENV.md) | DestinatarioPlanejamento / [TPQDPL](TPQDPL.md) | CHAVE->CHAVE; CHAVE2->CHAVE2; NUINSTANCIA->NUINSTANCIA; NUPLA->NUPLA | I |
| EnvioQuestionario / [TPQENV](TPQENV.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTANCIA->NUINSTANCIA | I |
| EnvioQuestionario / [TPQENV](TPQENV.md) | Planejamento / [TPQPLA](TPQPLA.md) | NUPLA->NUPLA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
