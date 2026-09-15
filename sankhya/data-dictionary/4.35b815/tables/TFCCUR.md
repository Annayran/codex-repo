# TFCCUR — Curso - currículo

- Módulo técnico: [TFC](../modules/TFC.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CursoCurriculo | Formação | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARGAHORARIA | Carga horária em horas | I |  |  | N |
| CODTIPCURSO | Tipo | I |  |  | N |
| DATFIM | Data Fim | D |  |  | N |
| DATINICIO | Data Início | D |  |  | N |
| LOCAL | Instituição | S |  |  | N |
| NUCURRICULO | NUCURRICULO | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TITULO | Curso | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Curriculo / [TFCBCO](TFCBCO.md) | CursoCurriculo / [TFCCUR](TFCCUR.md) | NUCURRICULO->NUCURRICULO | L |
| CursoCurriculo / [TFCCUR](TFCCUR.md) | TipoCurso / [TFCTPC](TFCTPC.md) | CODTIPCURSO->CODTIPCURSO | L |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
