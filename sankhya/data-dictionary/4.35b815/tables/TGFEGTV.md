# TGFEGTV — Espécies da GTV

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EspeciesGTV | Espécies da GTV | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IDINFGTV | Identificador da GTV | S |  |  | N |
| NUMINFGTV | Número da GTV | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| TIPOESPECIE | Tipo da Espécie | I |  |  | N |
| VLRESPECIE | Valor transportado em espécie | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EspeciesGTV / [TGFEGTV](TGFEGTV.md) | InformacoesGTV / [TGFIGTV](TGFIGTV.md) | IDINFGTV->IDINFGTV; NUMINFGTV->NUMINFGTV; NUNOTA->NUNOTA | I |
| InformacoesGTV / [TGFIGTV](TGFIGTV.md) | EspeciesGTV / [TGFEGTV](TGFEGTV.md) | IDINFGTV->IDINFGTV; NUMINFGTV->NUMINFGTV; NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
