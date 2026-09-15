# TGPREL — TABLE TGPREL

- Módulo técnico: [TGP](../modules/TGP.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RedeAvaliadores | Rede Avaliadores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCARGO | CODCARGO | I |  |  | N |
| CODEMPFUNC | Empresa | I |  |  | N |
| CODFUNC | Funcionário | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPORELACAO | Tipo Relação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RedeAvaliadores / [TGPREL](TGPREL.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPFUNC->CODEMP | I |
| RedeAvaliadores / [TGPREL](TGPREL.md) | Funcionario /  | CODFUNC->CODFUNC | I |
| RedeAvaliadores / [TGPREL](TGPREL.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
