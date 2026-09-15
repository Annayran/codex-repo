# TFCTPC — Tipo de Curso

- Módulo técnico: [TFC](../modules/TFC.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoCurso | Tipo de Curso | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPCURSO | Cód. Curso | I |  |  | N |
| DESCRTIPCURSO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CursoCurriculo / [TFCCUR](TFCCUR.md) | TipoCurso / [TFCTPC](TFCTPC.md) | CODTIPCURSO->CODTIPCURSO | L |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
