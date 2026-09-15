# TCSCSE — Contratos e Serviços

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContratoServico | Contratos de Serviços | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSERV | Serviço | I |  |  | N |
| NUMCONTRATO | Número do Contrato | I |  |  | N |
| PARCELAS | Parcelas | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ComercialParceiro / [TCSCPA](TCSCPA.md) | ContratoServico / [TCSCSE](TCSCSE.md) | NUMCONTRATO->NUMCONTRATO; SEQUENCIA->SEQUENCIA | I |
| ContratoServico / [TCSCSE](TCSCSE.md) | ParceiroInstalacao / [TCSCPA](TCSCPA.md) | NUMCONTRATO->NUMCONTRATO; SEQUENCIA->SEQUENCIA | I |
| ContratoServico / [TCSCSE](TCSCSE.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ParceiroInstalacao / [TCSCPA](TCSCPA.md) | ContratoServico / [TCSCSE](TCSCSE.md) | NUMCONTRATO->NUMCONTRATO; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
