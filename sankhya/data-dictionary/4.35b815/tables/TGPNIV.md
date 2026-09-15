# TGPNIV — TABLE TGPNIV

- Módulo técnico: [TGP](../modules/TGP.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Nivel | Nivel | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNIVEL | CODNIVEL | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DESCRNIVEL | DESCRNIVEL | S |  |  | N |
| DHALTER | DHALTER | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NivelCargo / [TGPNVC](TGPNVC.md) | Nivel / [TGPNIV](TGPNIV.md) | CODNIVEL->CODNIVEL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
