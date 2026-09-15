# TCILOC — Local

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Local | Local | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Cód.Bem | S |  |  | N |
| CODDEPTO | Cód.Departamento | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODPROD | Cód.Produto | I |  |  | N |
| CODUSU | Cód.Usuario | I |  |  | N |
| DTENTRADA | Data de entrada | H |  |  | N |
| NUNOTA | Número de nota | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BemNotafiscal / [TCIIBE](TCIIBE.md) | Local / [TCILOC](TCILOC.md) | CODBEM->CODBEM; CODPROD->CODPROD; NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | Local / [TCILOC](TCILOC.md) | CODBEM->CODBEM; CODDEP->CODDEPTO; CODPROD->CODPROD | I |
| Local / [TCILOC](TCILOC.md) | BemNotafiscal / [TCIIBE](TCIIBE.md) | CODBEM->CODBEM; CODPROD->CODPROD; NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| Local / [TCILOC](TCILOC.md) | Departamento /  | CODDEPTO->CODDEP | I |
| Local / [TCILOC](TCILOC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Local / [TCILOC](TCILOC.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Local / [TCILOC](TCILOC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Local / [TCILOC](TCILOC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
