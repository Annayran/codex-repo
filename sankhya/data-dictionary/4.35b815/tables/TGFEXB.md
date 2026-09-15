# TGFEXB — Extrato Bancário

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 18
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExtratoBancario | Extrato Bancário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CNPJ_CPF | Cnpj/Cpf | S |  |  | N |
| CODBCO | Banco | I |  |  | N |
| CODCATEG | Cód. Categoria | I |  |  | N |
| CODUSU | Cód. Usuário Alteração | I |  |  | N |
| CONCILIADO | Conciliado | S |  |  | N |
| CONVENIO | Convênio | F |  |  | N |
| DHALTER | Data alteração | H |  |  | N |
| DTLANC | Data Lançamento | D |  |  | N |
| FITID | ID da Transação | S |  |  | N |
| HIST | Histórico | S |  |  | N |
| NROCTA | Núm. Conta | S |  |  | N |
| NRODOC | Núm. Documento | I |  |  | N |
| NUBCO | Núm. Único Bancário | I |  |  | N |
| NUEXB | Cód. Extrato | I |  |  | N |
| NUIMPORT | Núm. Importação | I |  |  | N |
| RECDESP | Receita/Despesa | I |  |  | N |
| TIPOTRANSACAO | Tipo de transação | S |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExtratoBancario / [TGFEXB](TGFEXB.md) | ContaBancaria / [TSICTA](TSICTA.md) | NROCTA->CODCTABCO | I |
| ImportacaoExtratoBancario / [TGFIEB](TGFIEB.md) | ExtratoBancario / [TGFEXB](TGFEXB.md) | NUIMPORT->NUIMPORT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
