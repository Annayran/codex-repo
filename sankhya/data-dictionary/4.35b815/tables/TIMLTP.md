# TIMLTP — TimLoteamentoProprietarios

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLoteamentoProprietarios | Proprietários | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LTPESTAGIO | Estagio | S |  |  | N |
| LTPFORMADEREPASSE | Forma de repasse | S |  |  | N |
| LTPLOTEAMENTO | Loteamento | I |  |  | N |
| LTPPERCENTUAL | Percentual | F |  |  | N |
| LTPPROCURADOR | Procurador | I |  |  | N |
| LTPPROPRIETARIO | Proprietario | I |  |  | N |
| LTPREPAGENCIA | Agência | S |  |  | N |
| LTPREPBANCO | Banco | I |  |  | N |
| LTPREPCONTA | Conta | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimLoteamentoProprietarios / [TIMLTP](TIMLTP.md) | LOTCODIGO->LTPLOTEAMENTO | I |
| TimLoteamentoProprietarios / [TIMLTP](TIMLTP.md) | Banco / [TSIBCO](TSIBCO.md) | LTPREPBANCO->CODBCO | I |
| TimLoteamentoProprietarios / [TIMLTP](TIMLTP.md) | Parceiro / [TGFPAR](TGFPAR.md) | LTPPROPRIETARIO->CODPARC | I |
| TimLoteamentoProprietarios / [TIMLTP](TIMLTP.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | LTPPROCURADOR->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
