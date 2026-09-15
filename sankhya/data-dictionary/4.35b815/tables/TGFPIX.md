# TGFPIX — Dados das transacoes PIX

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TransacaoPIX | Transacoes PIX | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVERECEBEDOR | Chave do recebedor | S |  |  | N |
| CPFCNPJPAGADOR | CPF/CNPJ do pagador | S |  |  | N |
| DADOSCOBRANCA | Dados da cobranca | C |  |  | N |
| DADOSPAGAMENTO | Dados do pagamento | C |  |  | N |
| DTTRANSACAO | Data | H |  |  | N |
| IDTRANSACAO | Id. Transacao | S |  |  | N |
| NOMEPAGADOR | Nome do pagador | S |  |  | N |
| NSU | NSU | S |  |  | N |
| NUFIN | Nro. Unico financeiro | I |  |  | N |
| ORIGEM | Origem | S |  |  | N |
| SEQPIX | Sequencial | I |  |  | N |
| STATUS | STATUS | S |  |  | N |
| TIPOPIX | Tipo Pix | S |  |  | N |
| VLRTRANSACAO | Valor | F |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
