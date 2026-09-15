# TGAPAR — Tabela de Agendamento de Parada de Equipamentos

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AgendamentoParadaEquipamentos | Tabela de Agendamento de Parada de Equipamentos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMOT | Cod. Motivo da Parada | I |  |  | N |
| CODUSU | Cód. Usuário que Realizou a Operação | I |  |  | N |
| DHALTER | Dat. que o Registro foi Inserido/Alterado | H |  |  | N |
| DTFIM | Dat. Fim da Parada | H |  |  | N |
| DTINICIO | Dat. Inicio da Parada | H |  |  | N |
| HRFIM | Hr. de Fim da Parada | I |  |  | N |
| HRINICIO | Hr. que a Parada Iniciou | I |  |  | N |
| NUEQUIP | Cod. Equipamento Parado | I |  |  | N |
| NUMDOC | Nro. Documento de Identificação | I |  |  | N |
| NUPARADA | Nro. da Parada (Pk) | I |  |  | N |
| OBSERVACAO | Obs. da Parada | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendamentoParadaEquipamentos / [TGAPAR](TGAPAR.md) | MaquinasVeiculosEquipamentos / [TGFMEQ](TGFMEQ.md) | NUEQUIP->NUMEQ | I |
| AgendamentoParadaEquipamentos / [TGAPAR](TGAPAR.md) | MotivoParadasEquipamentos / [TGAMTP](TGAMTP.md) | CODMOT->CODMOT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
