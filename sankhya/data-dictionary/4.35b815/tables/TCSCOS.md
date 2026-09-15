# TCSCOS — Classificação de OS

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ClassificacaoOs | Classificação de OS | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCOS | Código | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| FECHARSLA | Fechar SLA | S |  |  | N |
| SUSPENDERSLA | Suspender SLA | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OrdemServico / [TCSOSE](TCSOSE.md) | ClassificacaoOs / [TCSCOS](TCSCOS.md) | CODCOS->CODCOS | I |
| Servico / [TGFPRO](TGFPRO.md) | ClassificacaoOs / [TCSCOS](TCSCOS.md) | CODCOS->CODCOS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
