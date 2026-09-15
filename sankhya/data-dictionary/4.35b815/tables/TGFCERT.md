# TGFCERT — Certificado Pix

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CertificadoPix | Certificado Pix | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CERTIFICADO | Dados do Certificado | S |  |  | N |
| CODCTABCOINT | Código da Conta | I |  |  | N |
| DADOSCERTIFICADO | Dados do Certificado | C |  |  | N |
| DTATIVACAO | Data de Ativação | H |  |  | N |
| DTINICIORENOVACAO | Data de início do período de renovação | H |  |  | N |
| DTVENCIMENTO | Data de Vencimento | H |  |  | N |
| EMAIL | e-mail | S |  |  | N |
| FALRENOVA | Falha Renovação | S |  |  | N |
| QTDTENTATIVAS | Quantidade de tentativas | I |  |  | N |
| SEQCERT | Sequência | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CertificadoPix / [TGFCERT](TGFCERT.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
