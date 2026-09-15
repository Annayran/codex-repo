# TGFDPQ — Desconto por quantidade

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescontoPorQuantidade | Desconto por quantidade | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUNUVERSAO | ATUNUVERSAO | S |  |  | N |
| CODUSU | Cód. Usuário Alteração | I |  |  | N |
| DTALTER | Data alteração | H |  |  | N |
| NUPROMOCAO | Nro. único | I |  |  | N |
| NUVERSAO | NUVERSAO | I |  |  | N |
| PERCDESC | %Desc./Vlr. Unit. | F |  |  | N |
| QTDE | Quantidade até | F |  |  | N |
| TIPDESC | Tipo Desconto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Desconto / [TGFDES](TGFDES.md) | DescontoPorQuantidade / [TGFDPQ](TGFDPQ.md) | NUPROMOCAO->NUPROMOCAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
