# TCSPAD — Parcelas Adicionais

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParcelaAdicional | Parcela Adicional | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Dt. alteração | H |  |  | N |
| DIAPAG | Dia do pagto | I |  |  | N |
| DTREAJUSTE | Dt. base reajuste | D |  |  | N |
| DTVENCTO | Data vencimento | D |  |  | S |
| NUMCONTRATO | Contrato | I |  |  | N |
| PERIODICIDADE | Periodicidade | S |  |  | N |
| QTDGERARPREV | Qtd. provisões a gerar | I |  |  | S |
| QTDPREV | Qtd. provisões | I |  |  | S |
| REFERENCIA | Dt. referência | D |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| VALOR | Valor | F |  |  | S |
| VENCTO | Prazo vencto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contrato / [TCSCON](TCSCON.md) | ParcelaAdicional / [TCSPAD](TCSPAD.md) | NUMCONTRATO->NUMCONTRATO | I |
| ItemParcelaAdicional / [TCSPIT](TCSPIT.md) | ParcelaAdicional / [TCSPAD](TCSPAD.md) | NUMCONTRATO->NUMCONTRATO; SEQUENCIA->SEQUENCIA | I |
| ParcelaAdicional / [TCSPAD](TCSPAD.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ParcelaAdicional / [TCSPAD](TCSPAD.md) | ItemParcelaAdicional / [TCSPIT](TCSPIT.md) | NUMCONTRATO->NUMCONTRATO; SEQUENCIA->SEQUENCIA | I |
| ParcelaAdicional / [TCSPAD](TCSPAD.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
