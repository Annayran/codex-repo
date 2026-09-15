# TGFDFAGR — Defensivos Agricolas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DefensivosAgricolas | Defensivos Agricolas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CPFRESPTEC | CPF do Responsável Técnico, emitente do receituário | S |  |  | N |
| IDDEFAGR | ID Defensivo | I |  |  | N |
| NUMRECEITAGRO | Número da receita ou receituário do agrotóxico / defensivo agrícola. | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | DefensivosAgricolas / [TGFDFAGR](TGFDFAGR.md) | NUNOTA->NUNOTA | I |
| DefensivosAgricolas / [TGFDFAGR](TGFDFAGR.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
