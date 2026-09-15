# TGFCRED — Tabela de Crediários

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Crediario | Tabela de Crediários | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AVALISTA | Avalista | S |  |  | N |
| CODCLIENTE | Código do cliente | I |  |  | N |
| CODCONTRATO | Contrato | I |  |  | N |
| CODCONTRATOORIG | Contrato Origem | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| DTNEG | Dt Negociação | H |  |  | N |
| MEDATRASODIAS | Média de Atraso (Dias) | F |  |  | S |
| NUNOTA | Nr Nota | I |  |  | N |
| QTDPARCELAS | Qtd Parcelas | I |  |  | N |
| RENEGOCIADO | Renegociado | S |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| VLRCONTRATO | Vlr Contrato | F |  |  | N |
| VLRENTRADA | Vlr Entrada | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Crediario / [TGFCRED](TGFCRED.md) | CabecalhoNotaSaida / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| Crediario / [TGFCRED](TGFCRED.md) | Crediario / [TGFCRED](TGFCRED.md) | CODCONTRATOORIG->CODCONTRATOORIG | I |
| Crediario / [TGFCRED](TGFCRED.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
