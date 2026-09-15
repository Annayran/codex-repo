# VGFLEADTIMECOMPRA — View Lead Time de Compra

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewLeadTimeCompra | View Lead Time de Compra | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODPARCFORN | Fornecedor | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| DESCRPROD | Descrição Produto | S |  |  | N |
| LEADTIME_EMP | Lead time registrado | I |  |  | N |
| LEADTIME_PROD | Lead time registrado | I |  |  | N |
| MAIORPRAZO | Maior tempo de entrega | I |  |  | N |
| MENORPRAZO | Menor tempo de entrega | I |  |  | N |
| PRAZOMEDIO | Tempo médio de entrega | I |  |  | N |
| QTDFORN | Qtd. Fornecedores | I |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
