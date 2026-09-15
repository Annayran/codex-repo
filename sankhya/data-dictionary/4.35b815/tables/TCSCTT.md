# TCSCTT — ContatoProspect

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContatoProspect | Contato Prospect | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APELIDO | Apelido | S |  |  | N |
| CARGO | Cargo | S |  |  | N |
| CELULAR | Celular | S |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODPAP | Prospect | I |  |  | N |
| EMAIL | E-mail | S |  |  | N |
| NOMECONTATO | Nome | S |  |  | N |
| RAMAL | Ramal | S |  |  | N |
| TELEFONE | Telefone | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContatoProspect / [TCSCTT](TCSCTT.md) | ParceiroProspect / [TCSPAP](TCSPAP.md) | CODPAP->CODPAP | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ContatoProspect / [TCSCTT](TCSCTT.md) | CODCONTATOPAP->CODCONTATO; CODPAP->CODPAP | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | ContatoProspect / [TCSCTT](TCSCTT.md) | CODPAP->CODPAP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
