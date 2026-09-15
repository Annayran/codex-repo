# TWFFORM — Formularios de Processo

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormularioProcessoNegocio | Formulario do Processo de Negócio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARDINALIDADE | Cardinalidade | S |  |  | N |
| CONFIGCAMPOS | Configuração de campos | C |  |  | N |
| DESCRICAO | Descrição do formulário | S |  |  | N |
| ESCOPO | Escopo | S |  |  | N |
| NOMEINSTANCIA | Nome do formulário | S |  |  | N |
| NUELE | Núm. Elemento | I |  |  | N |
| NUFORM | Núm. Formulário | I |  |  | N |
| NUFORMPAI | Núm. Formulário pai | I |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| ORDEMABAS | Ordem abas | S |  |  | N |
| POLITICAREETRANCIA | Política Reentrância | S |  |  | N |
| SALVARDESTINO | Quando salvar destino | S |  |  | N |
| SALVARFINALPROCESSO | Salvar no final do processo | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| UTILIZATAREFA | Utiliza na tarefa | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormularioProcessoNegocio / [TWFFORM](TWFFORM.md) | ElementoProcesso / [TWFELE](TWFELE.md) | NUELE->NUELE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
