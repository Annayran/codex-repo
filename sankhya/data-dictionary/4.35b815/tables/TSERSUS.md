# TSERSUS — Contumância e Sustação

- Módulo técnico: [TSE](../modules/TSE.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContumanciaSustacao | ContumanciaSustacao | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGENCIA | Agência | I |  |  | N |
| CHFINAL | Número do cheque final | I |  |  | N |
| CHINICIAL | Número do cheque inicial | I |  |  | N |
| CONTACORR | Conta corrente | I |  |  | N |
| DTOCORR | Data da ocorrência | D |  |  | N |
| MOTIVOSUSTA | Motivo da sustação | S |  |  | N |
| NUCONSULTA | NUCONSULTA | I |  |  | N |
| ORIGEM | Nome do Banco | S |  |  | N |
| QTDEOCORR | Quantidade total de ocorrências | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
