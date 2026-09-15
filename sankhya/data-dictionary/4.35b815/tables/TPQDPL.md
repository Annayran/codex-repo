# TPQDPL — Destinatario Planejamento

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DestinatarioPlanejamento | Destinatário do Planejamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APRESENTACAO | Valor do campo de apresentação | S |  |  | S |
| CHAVE | CHAVE | I |  |  | N |
| CHAVE2 | CHAVE2 | I |  |  | N |
| EVENTO | EVENTO | S |  |  | N |
| NUINSTANCIA | NUINSTANCIA | I |  |  | N |
| NUPLA | NUPLA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DestinatarioPlanejamento / [TPQDPL](TPQDPL.md) | EnvioQuestionario / [TPQENV](TPQENV.md) | CHAVE->CHAVE; CHAVE2->CHAVE2; NUINSTANCIA->NUINSTANCIA; NUPLA->NUPLA | I |
| DestinatarioPlanejamento / [TPQDPL](TPQDPL.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTANCIA->NUINSTANCIA | I |
| DestinatarioPlanejamento / [TPQDPL](TPQDPL.md) | Planejamento / [TPQPLA](TPQPLA.md) | NUPLA->NUPLA | I |
| EnvioQuestionario / [TPQENV](TPQENV.md) | DestinatarioPlanejamento / [TPQDPL](TPQDPL.md) | CHAVE->CHAVE; CHAVE2->CHAVE2; NUINSTANCIA->NUINSTANCIA; NUPLA->NUPLA | I |
| Planejamento / [TPQPLA](TPQPLA.md) | DestinatarioPlanejamento / [TPQDPL](TPQDPL.md) | NUPLA->NUPLA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
