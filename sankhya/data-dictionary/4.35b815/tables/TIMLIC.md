# TIMLIC — Locação - Garantia imóvel caução

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLocacaoImovelCaucao | Garantia - Imóvel Caução | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LICCARTORIO | Cartório | I |  |  | N |
| LICCARTORIOAVERBACAO | Cartório de averbação | I |  |  | N |
| LICCIRCUNSCRICAO | Circunscrição | S |  |  | N |
| LICDATAAVERBACAO | Dt. Averbação | D |  |  | N |
| LICDATALAVRATURA | Dt. Lavratura | D |  |  | N |
| LICFOLHAS | Folhas | S |  |  | N |
| LICIDENTIFICADOR | Código | I |  |  | N |
| LICIMOVEL | Imóvel | I |  |  | N |
| LICLIVRO | Livro | S |  |  | N |
| LICLOCACAO | Locação | I |  |  | N |
| LICNUMEROAVERBACAO | Nro. Averbação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoImovelCaucao / [TIMLIC](TIMLIC.md) | LOCCODIGO->LICLOCACAO | I |
| TimLocacaoImovelCaucao / [TIMLIC](TIMLIC.md) | TimCartorio / [TIMCAR](TIMCAR.md) | LICCARTORIO->CARCODIGO | I |
| TimLocacaoImovelCaucao / [TIMLIC](TIMLIC.md) | TimCartorioAverbacao / [TIMCAR](TIMCAR.md) | LICCARTORIOAVERBACAO->CARCODIGO | I |
| TimLocacaoImovelCaucao / [TIMLIC](TIMLIC.md) | TimImovel / [TIMIMV](TIMIMV.md) | LICIMOVEL->IMVCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
