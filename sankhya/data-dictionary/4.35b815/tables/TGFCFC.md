# TGFCFC — CidadeRegiaoFrete

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CidadeRegiaoFrete | Cidades | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCID | Cód. Cidade | I |  |  | N |
| CODREG | CODREG | I |  |  | N |
| NUCFR | NUCFR | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CidadeRegiaoFrete / [TGFCFC](TGFCFC.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| RegiaoCalculoFrete / [TGFCFRC](TGFCFRC.md) | CidadeRegiaoFrete / [TGFCFC](TGFCFC.md) | CODREG->CODREG; NUCFR->NUCFR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
