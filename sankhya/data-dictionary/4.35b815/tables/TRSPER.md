# TRSPER — Perfis da Requisição

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PerfilRequisicao | Perfil desejado do candidato | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPERFIL | Cód. Perfil | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| IMPRESCINDIVEL | Imprescindível | S |  |  | N |
| NUREQUISICAO | Requisição | I |  |  | N |
| PESO | Peso | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PerfilRequisicao / [TRSPER](TRSPER.md) | PerfilFolhaPagamento /  | CODPERFIL->CODPERFIL | I |
| PerfilRequisicao / [TRSPER](TRSPER.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | PerfilRequisicao / [TRSPER](TRSPER.md) | NUREQUISICAO->NUREQUISICAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
