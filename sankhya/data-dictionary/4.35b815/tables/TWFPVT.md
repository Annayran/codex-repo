# TWFPVT — Prazo de Vencimento da Tarefa

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrazoVencimentoTarefa | Prazo de Vencimento da Tarefa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Active | S |  |  | N |
| CARGAHORARIA | Carga horária | I |  |  | N |
| NUELE | Núm. Elemento | I |  |  | N |
| TEMPOFIXO | Tempo fixo | F |  |  | N |
| TEMPOVARIAVEL | Tempo variável | S |  |  | N |
| TIPOCONTAGEM | Tipo de Contagem | S |  |  | N |
| TIPOPRAZO | Term type | S |  |  | N |
| TIPOTEMPO | Weather type | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrazoVencimentoAlerta / [TWFAPVT](TWFAPVT.md) | PrazoVencimentoTarefa / [TWFPVT](TWFPVT.md) | NUELE->NUELE | I |
| PrazoVencimentoTarefa / [TWFPVT](TWFPVT.md) | ElementoProcesso / [TWFELE](TWFELE.md) | NUELE->NUELE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
