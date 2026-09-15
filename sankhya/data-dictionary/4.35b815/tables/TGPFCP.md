# TGPFCP — TABLE TGPFCP

- Módulo técnico: [TGP](../modules/TGP.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Competencias | Competências | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCOMPETENCIA | Cód. Competência | I |  |  | N |
| CODFUNCAO | CODFUNCAO | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DHALTER | DHALTER | D |  |  | N |
| DTFIM | Data Fim | D |  |  | N |
| DTINICIO | Data Início | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Competencias / [TGPFCP](TGPFCP.md) | PerfilRequisito /  | CODCOMPETENCIA->CODPERFIL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
