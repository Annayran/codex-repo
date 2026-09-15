# TCSCVE — Contrato Veículos

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContratoVeiculo | Contrato Veículos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSERV | Serviço | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEICULO | Veiculo | I |  |  | N |
| DTALTER | Data de alteração | H |  |  | N |
| DTDESINSTAL | Data de Desinstalação | H |  |  | N |
| DTINSTAL | Data de Instalação | H |  |  | N |
| NUMCONTRATO | Número do Contrato | I |  |  | N |
| NUMOSDESINSTAL | OS Desinstalação | I |  |  | N |
| NUMOSINSTAL | OS Instalação | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TELEFONE | Telefone | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoVeiculo / [TCSCVE](TCSCVE.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ContratoVeiculo / [TCSCVE](TCSCVE.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
