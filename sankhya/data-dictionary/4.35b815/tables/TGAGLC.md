# TGAGLC — Grupo de características Analisáveis

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoCaracteristicaAnalisavel | GrupoCaracteristica Analisavel | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | ANALITICO | S |  |  | N |
| ATIVO | ATIVO | S |  |  | N |
| CODGRUPOCLC | CODGRUPOCLC | I |  |  | N |
| CODGRUPOCLCPAI | CODGRUPOCLCPAI | I |  |  | N |
| DESCRICAO | DESCRICAO | S |  |  | N |
| GRAU | Grau | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | GrupoCaracteristicaAnalisavel / [TGAGLC](TGAGLC.md) | CODGRUPOCLC->CODGRUPOCLC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
