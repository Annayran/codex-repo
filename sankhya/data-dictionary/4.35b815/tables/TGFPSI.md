# TGFPSI — Cadastro de Projetos Sites

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CadastroProjetoSite | Cadastro de Projetos Sites | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROJ | Projeto | I |  |  | N |
| CODSITE | Site | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroProjetoSite / [TGFPSI](TGFPSI.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| CadastroProjetoSite / [TGFPSI](TGFPSI.md) | Site / [TGFSIT](TGFSIT.md) | CODSITE->CODSITE | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | CadastroProjetoSite / [TGFPSI](TGFPSI.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
