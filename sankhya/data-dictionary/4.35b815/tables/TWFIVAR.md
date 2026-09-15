# TWFIVAR — Instância de Variáveis

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InstanciaVariavel | Instância de Variáveis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DTA | Data e Hora | H |  |  | N |
| IDINSTPRN | Id Instância do Processo | I |  |  | N |
| IDINSTTAR | Id Instância da Tarefa | I |  |  | N |
| NOME | Nome | S |  |  | N |
| NUMDEC | Número decimal | F |  |  | N |
| NUMINT | Número inteiro | I |  |  | N |
| TEXTO | Texto | S |  |  | N |
| TEXTOLONGO | Texto formatado | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| InstanciaVariavel / [TWFIVAR](TWFIVAR.md) | InstanciaTarefa / [TWFITAR](TWFITAR.md) | IDINSTPRN->IDINSTPRN; IDINSTTAR->IDINSTTAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
