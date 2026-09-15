# TCSCSTC — Características de Serviço de Tele/Comunicação

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CaracteristicasSvTeleCom | Características de Serviço de Tele Comunicação | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUMCSTC | Número da Característica de Serviço de Tele/Comunicação | I |  |  | N |
| TIPATE | Tipo Atendimento - SCM | S |  |  | N |
| TIPMEIO | Tipo Meio | S |  |  | N |
| TIPPRO | Tipo Produto - SCM | S |  |  | N |
| TIPSVCOM | Tipo Serviço Comunicação | S |  |  | N |
| TIPTEC | Tipo Tecnologia | S |  |  | N |
| VELCOM | Velocidade Contratada - SCM | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contrato / [TCSCON](TCSCON.md) | CaracteristicasSvTeleCom / [TCSCSTC](TCSCSTC.md) | NUMCSTC->NUMCSTC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
