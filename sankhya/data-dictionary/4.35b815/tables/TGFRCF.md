# TGFRCF — tabela rotas calculo de frete

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 20
- Instâncias DWF: 1
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RotaCalculoFrete | Rotas | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCIDDEST | Cidade Destino | I |  |  | N |
| CODCIDORIG | Cidade Origem | I |  |  | N |
| CODEVENTO | Evento | I |  |  | N |
| CODFORMFRETE | Cód. Forma Frete | I |  |  | N |
| CODPARCDEST | Parceiro Destino | I |  |  | N |
| CODPARCORIG | Parceiro Origem | I |  |  | N |
| CODREGDEST | Região Destino | I |  |  | N |
| CODREGORIG | Região Origem | I |  |  | N |
| DISTANCIA | Distância | F |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| NUCFR | Cód. Rota | I |  |  | N |
| OBRIGATORIO | Obrigatório? | S |  |  | N |
| PERCENTUAL | Percentual | F |  |  | N |
| TEMPO | Tempo | F |  |  | N |
| UFDEST | Estado Destino | I |  |  | N |
| UFORIG | Estado Origem | I |  |  | N |
| UNIDTEMPO | Unidade de Tempo | S |  |  | N |
| VALOR | Valor | F |  |  | N |
| VIA | Via | S |  |  | N |
| VLRMIN | Vlr Mínimo de Frete | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDDEST->CODCID | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | CidadeOrigem / [TSICID](TSICID.md) | CODCIDORIG->CODCID | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | EventoVeiculo / [TSIEVE](TSIEVE.md) | CODEVENTO->CODEVENTO | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | ParceiroOrigem / [TGFPAR](TGFPAR.md) | CODPARCORIG->CODPARC | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | RegiaoCalculoFreteDestino / [TGFCFRC](TGFCFRC.md) | CODREGDEST->CODREG; NUCFR->NUCFR | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | RegiaoCalculoFreteOrigem / [TGFCFRC](TGFCFRC.md) | CODREGORIG->CODREG; NUCFR->NUCFR | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | UnidadeFederativaDestino / [TSIUFS](TSIUFS.md) | UFDEST->CODUF | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | UnidadeFederativaOrigem / [TSIUFS](TSIUFS.md) | UFORIG->CODUF | I |
| TabelaCalculoFrete / [TGFCFR](TGFCFR.md) | RotaCalculoFrete / [TGFRCF](TGFRCF.md) | NUCFR->NUCFR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
