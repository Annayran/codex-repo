# TCSMPD — Modelos de etapas predecessoras

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ModeloEtapaPredecessora | Modelo de Etapa Predecessora | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMETOD | Cód. Método | I |  |  | N |
| NUMMODELO | Modelo | I |  |  | N |
| NUMMODELOPRED | Etapa Predecessora | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ModeloEtapa / [TCSMOD](TCSMOD.md) | ModeloEtapaPredecessora / [TCSMPD](TCSMPD.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |
| ModeloEtapaPredecessora / [TCSMPD](TCSMPD.md) | ModeloEtapa / [TCSMOD](TCSMOD.md) | CODMETOD->CODMETOD; NUMMODELOPRED->NUMMODELO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
