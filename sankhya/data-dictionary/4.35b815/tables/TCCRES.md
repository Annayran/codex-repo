# TCCRES — ResidenciasEmpreendimento

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ResidencialEmpreendimento | Residencial Empreendimento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Cód.Centro Resultado Residencial | I |  |  | N |
| CODEMPREE | Empreendimento | I |  |  | N |
| DTPREVISAO | Dt Previsão de Entrega das Chaves | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Empreendimento / [TCCEMP](TCCEMP.md) | ResidencialEmpreendimento / [TCCRES](TCCRES.md) | CODEMPREE->CODEMPREE | I |
| ResidencialEmpreendimento / [TCCRES](TCCRES.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ResidencialEmpreendimento / [TCCRES](TCCRES.md) | Empreendimento / [TCCEMP](TCCEMP.md) | CODEMPREE->CODEMPREE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
