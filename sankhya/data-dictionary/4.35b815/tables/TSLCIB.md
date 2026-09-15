# TSLCIB — Tabela de contingências de informações não sincronizadas com o servidor da BIA

- Módulo técnico: [TSL](../modules/TSL.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContingenciaInformacaoBia | Contingências de informações não sincronizadas com o servidor da BIA | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGBI | Código da informação | I |  |  | N |
| DHOCORRENCIA | Data e hora da contingência | H |  |  | N |
| SEQUENCIA | Identificador único | I |  |  | N |
| TENTENVIO | Número de tentativas não bem sucedidas | I |  |  | N |
| TIPO | Tipo da contingência | S |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
