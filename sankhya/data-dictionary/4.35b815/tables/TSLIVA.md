# TSLIVA — Variáveis customizadas adicionais.

- Módulo técnico: [TSL](../modules/TSL.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrevisaoVariaveisAdicionais | Variáveis customizadas adicionais. | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVE | Chave da variável | S |  |  | N |
| CODEXEC | Código da Execução | I |  |  | N |
| TIPO | Chave da variável | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrevisaoVendasExecucoes / [TSLIPX](TSLIPX.md) | PrevisaoVariaveisAdicionais / [TSLIVA](TSLIVA.md) | CODEXEC->CODEXEC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
