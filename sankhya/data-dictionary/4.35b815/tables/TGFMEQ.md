# TGFMEQ — Tabela de Maquinas e Equipamentos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MaquinasVeiculosEquipamentos | Tabela de Máquinas, Veículos e Equipamentos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Cod. do Bem | S |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODPARC | Cód. Parceiro Relacionando a Maq/Eqpto. | I |  |  | N |
| CODPROD | Cod. Produto | I |  |  | N |
| CODUSU | Cod. Usuário que Realizou a Operação | I |  |  | N |
| CODVEICULO | Cod. Veiculo | I |  |  | N |
| DESCRMEQ | Desc. da Maquina/Equipamento | S |  |  | N |
| DHALTER | Dat. de Inclusão/Alteração do Registro | H |  |  | N |
| NUMEQ | Nro. do Equipamento | I |  |  | N |
| OBSERVACAO | Obs. sobre Maquinas e Equipamentos | S |  |  | N |
| TERCEIRO | Indica de o Equipmento pertence a um Terceiro | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendamentoParadaEquipamentos / [TGAPAR](TGAPAR.md) | MaquinasVeiculosEquipamentos / [TGFMEQ](TGFMEQ.md) | NUEQUIP->NUMEQ | I |
| PlanoManutencao / [TGFEPP](TGFEPP.md) | MaquinasVeiculosEquipamentos / [TGFMEQ](TGFMEQ.md) | NUMEQ->NUMEQ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
