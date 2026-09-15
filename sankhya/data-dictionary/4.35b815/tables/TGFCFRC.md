# TGFCFRC — Regiao Rota Calculo Frete

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 3
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegiaoCalculoFrete | Região p/ Tabela de Frete | N | N |
| RegiaoCalculoFreteDestino | Região de Calculo Frete Destino | N | N |
| RegiaoCalculoFreteOrigem | Região de Calculo Frete Origem | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODREG | Cód. Região | I |  |  | N |
| NOMEREG | Região | S |  |  | N |
| NUCFR | Cód. Rota | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegiaoCalculoFrete / [TGFCFRC](TGFCFRC.md) | CidadeRegiaoFrete / [TGFCFC](TGFCFC.md) | CODREG->CODREG; NUCFR->NUCFR | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | RegiaoCalculoFreteDestino / [TGFCFRC](TGFCFRC.md) | CODREGDEST->CODREG; NUCFR->NUCFR | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | RegiaoCalculoFreteOrigem / [TGFCFRC](TGFCFRC.md) | CODREGORIG->CODREG; NUCFR->NUCFR | I |
| TabelaCalculoFrete / [TGFCFR](TGFCFR.md) | RegiaoCalculoFrete / [TGFCFRC](TGFCFRC.md) | NUCFR->NUCFR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
