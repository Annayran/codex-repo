# TGFTEF — Tabela para TEF

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 20
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TEF | TEF | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AUTORIZACAO | Autorização | S |  |  | N |
| BANDEIRA | Bandeira | S |  |  | N |
| CODUSU | Cód. usuário | I |  |  | N |
| COMPROVANTE | Comprovante | C |  |  | N |
| CONFIRMADO | Confirmado | S |  |  | N |
| DESDOBRAMENTO | Desdobramento | I |  |  | N |
| DTTRANSACAO | Dt. Transação | H |  |  | N |
| IDENTIFICACAOTEF | Identificação | I |  |  | N |
| NOMEREDE | Nome rede | S |  |  | N |
| NUFIN | Nro. Único | I |  |  | N |
| NUMCV | Nro. Comprovante de Venda | S |  |  | N |
| NUMDOC | Nro. Documento | S |  |  | N |
| NUMNSU | NSU | S |  |  | N |
| NUMPV | Nro. Ponto de Venda | S |  |  | N |
| OPERACAOTEF | Operação | S |  |  | N |
| PROCESSO | Processo | S |  |  | N |
| REDE | Rede | I |  |  | N |
| TIPODOC | Tipo de Doc. | I |  |  | N |
| VLRTAXA | Vlr. Taxa | F |  |  | N |
| VLRTRANSACAO | Vlr. Transação | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TEF / [TGFTEF](TGFTEF.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
