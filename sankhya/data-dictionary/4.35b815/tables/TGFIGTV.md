# TGFIGTV — InformacoesGTV

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InformacoesGTV | Informações da GTV | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARGAINFGTV | Quantidade de volumes/malotes | F |  |  | N |
| CODVEICULO | Código do Veículo | I |  |  | N |
| DIGVERINFGTV | Número do Dígito Verificador | I |  |  | N |
| IDINFGTV | Identificador da GTV | S |  |  | N |
| NUMINFGTV | Número da GTV | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| SERINFGTV | Série da GTV | S |  |  | N |
| SUBSERINFGTV | Subsérie da GTV | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EspeciesGTV / [TGFEGTV](TGFEGTV.md) | InformacoesGTV / [TGFIGTV](TGFIGTV.md) | IDINFGTV->IDINFGTV; NUMINFGTV->NUMINFGTV; NUNOTA->NUNOTA | I |
| GuiaTransporteValores / [TGFGTV](TGFGTV.md) | InformacoesGTV / [TGFIGTV](TGFIGTV.md) | NUNOTA->NUNOTA | I |
| InformacoesGTV / [TGFIGTV](TGFIGTV.md) | EspeciesGTV / [TGFEGTV](TGFEGTV.md) | IDINFGTV->IDINFGTV; NUMINFGTV->NUMINFGTV; NUNOTA->NUNOTA | I |
| InformacoesGTV / [TGFIGTV](TGFIGTV.md) | GuiaTransporteValores / [TGFGTV](TGFGTV.md) | NUNOTA->NUNOTA | I |
| InformacoesGTV / [TGFIGTV](TGFIGTV.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
