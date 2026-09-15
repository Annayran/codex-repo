# TGFCAU — CaracteristicaAnalisavelUsu

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CaracteristicaAnalisavelUsu | Característica Analisável do Usuário | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLC | CODCLC | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUCLC | Cód. Usuário | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CaracteristicaAnalisavelUsu / [TGFCAU](TGFCAU.md) | CODCLC->CODCLC | I |
| CaracteristicaAnalisavelUsu / [TGFCAU](TGFCAU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUCLC->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
