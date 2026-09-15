# TCSMFU — Recurso por Modelo de Etapa

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RecursoModEtapa | Recurso por Modelo de Etapa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMETOD | Metodologia | I |  |  | N |
| CODTIPFUNCAO | Função | I |  |  | N |
| NUMMODELO | Número do Modelo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ModeloEtapa / [TCSMOD](TCSMOD.md) | RecursoModEtapa / [TCSMFU](TCSMFU.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |
| RecursoModEtapa / [TCSMFU](TCSMFU.md) | ModeloEtapa / [TCSMOD](TCSMOD.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |
| RecursoModEtapa / [TCSMFU](TCSMFU.md) | TipoRecurso / [TCSTFU](TCSTFU.md) | CODTIPFUNCAO->CODTIPFUNCAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
