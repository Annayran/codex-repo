# TWFGRU — Grupo de Processos

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoProcesso | Grupo de Processos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cod. Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| GRUPOPAI | Grupo Pai | I |  |  | N |
| NUGRUPO | Nro. Grupo de Processo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoProcesso / [TWFGRU](TWFGRU.md) | GrupoProcesso / [TWFGRU](TWFGRU.md) | GRUPOPAI->NUGRUPO | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | GrupoProcesso / [TWFGRU](TWFGRU.md) | NUGRUPO->NUGRUPO | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | GrupoProcesso / [TWFGRU](TWFGRU.md) | NUGRUPO->NUGRUPO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
