# TSIUFU — Unidades Fiscais por UF

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadesFiscaisPorUF | Unidades Fiscais por UF | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUF | Código Unidade Federativa | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTFIMVALID | Data final validade | D |  |  | N |
| DTINIVALID | Data início validade | D |  |  | N |
| QTDDECIMAIS | Quantidade de casas decimais | I |  |  | N |
| VLRUNID | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| UnidadeFederativa / [TSIUFS](TSIUFS.md) | UnidadesFiscaisPorUF / [TSIUFU](TSIUFU.md) | CODUF->CODUF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
