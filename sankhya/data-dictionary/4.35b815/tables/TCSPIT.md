# TCSPIT — Itens Parcelas Adicionais

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemParcelaAdicional | Item Parcela Adicional | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| NUMCONTRATO | Contrato | I |  |  | N |
| PARCPROP | 1ª Parc. Proporcional | S |  |  | N |
| QUANTIDADE | Quantidade | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRIGUALPARC | Valor igual à Parcela? | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemParcelaAdicional / [TCSPIT](TCSPIT.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ItemParcelaAdicional / [TCSPIT](TCSPIT.md) | ParcelaAdicional / [TCSPAD](TCSPAD.md) | NUMCONTRATO->NUMCONTRATO; SEQUENCIA->SEQUENCIA | I |
| ItemParcelaAdicional / [TCSPIT](TCSPIT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ParcelaAdicional / [TCSPAD](TCSPAD.md) | ItemParcelaAdicional / [TCSPIT](TCSPIT.md) | NUMCONTRATO->NUMCONTRATO; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
