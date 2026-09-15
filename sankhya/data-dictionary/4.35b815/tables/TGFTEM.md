# TGFTEM — E-mails da TOP

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EmailTOP | E-mails da TOP | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQMODEMAIL | Arquivo modelo de e-mail | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| DIASAVISO | Dias aviso | I |  |  | N |
| EMAIL | E-mail | S |  |  | N |
| FATURAR | Faturar | S |  |  | N |
| LIBERAR | Cancelar reservas | S |  |  | N |
| MODELO | Modelo | I |  |  | N |
| TEXTO | Texto p/ e-mail | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmailTOP / [TGFTEM](TGFTEM.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | MODELO->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | EmailTOP / [TGFTEM](TGFTEM.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
