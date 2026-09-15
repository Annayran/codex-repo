# TGASAF — Cadastro Safra

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Safra | Safra | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSAF | Código | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| DTFINAL | Data Final | D |  |  | N |
| DTINICIO | Data Início | D |  |  | N |
| STATUS | Ativo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Safra / [TGASAF](TGASAF.md) | CODSAF->CODSAF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
