# TGFIAS — Itens da agenda

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemAgenda | Itens da agenda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSERV | Serviço | I |  |  | N |
| HORAFINAL | Hora final | T |  |  | N |
| HORAINICIAL | Hora inicial | T |  |  | N |
| NUAGENDA | Nro agenda | I |  |  | N |
| QTDMODULOS | Qtd. módulos | I |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoAgenda / [TGFCAS](TGFCAS.md) | ItemAgenda / [TGFIAS](TGFIAS.md) | NUAGENDA->NUAGENDA | I |
| ItemAgenda / [TGFIAS](TGFIAS.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
