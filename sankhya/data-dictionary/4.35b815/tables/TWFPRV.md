# TWFPRV — Propriedade das Variáveis de Processo

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PropriedadeVariavelProcesso | Propriedade das Variáveis de Processo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NOME | Nome | S |  |  | N |
| NUVAR | Núm. Variável | I |  |  | N |
| VALOR | Valor | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| VariavelProcesso / [TWFVAR](TWFVAR.md) | PropriedadeVariavelProcesso / [TWFPRV](TWFPRV.md) | NUVAR->NUVAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
