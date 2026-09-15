# TGFEBOL — Email de boletos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EmailBoletos | Email de boletos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFILA | Código na Fila | I |  |  | N |
| NUFIN | Nro Único | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmailBoletos / [TGFEBOL](TGFEBOL.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| EmailBoletos / [TGFEBOL](TGFEBOL.md) | MSDFilaMensagem / [TMDFMG](TMDFMG.md) | CODFILA->CODFILA | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | EmailBoletos / [TGFEBOL](TGFEBOL.md) | CODFILA->CODFILA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
