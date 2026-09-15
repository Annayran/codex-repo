# TFCCAR — Cargos selecionados no currículo

- Módulo técnico: [TFC](../modules/TFC.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CargosSelecionados | Cargos Pretendidos | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CANDIDATO | Candidato | S |  |  | N |
| CODCARGO | Cargo | I |  |  | N |
| NUCURRICULO | Curriculo | I |  |  | N |
| TRIAGEM | Triagem | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CargosSelecionados / [TFCCAR](TFCCAR.md) | Cargo /  | CODCARGO->CODCARGO | I |
| Curriculo / [TFCBCO](TFCBCO.md) | CargosSelecionados / [TFCCAR](TFCCAR.md) | NUCURRICULO->NUCURRICULO | L |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
