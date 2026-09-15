# TSIANX — Anexo Sistema

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AnexoSistema | Anexos do Sistema | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVEARQUIVO | Chave arquivo | S |  |  | N |
| CODUSU | Usuário Inclusão | I |  |  | N |
| CODUSUALT | Usuário Alteração | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Modificado em | H |  |  | N |
| DHCAD | Criado em | H |  |  | N |
| LINK | Link | S |  |  | N |
| NOMEARQUIVO | Arquivo | S |  |  | N |
| NOMEINSTANCIA | Instância | S |  |  | N |
| NUATTACH | Código | I |  |  | N |
| PKREGISTRO | Pk Registro | S |  |  | N |
| RESOURCEID | ID tela | S |  |  | N |
| TIPOACESSO | Acesso | S |  |  | N |
| TIPOAPRES | Visível | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnexoSistema / [TSIANX](TSIANX.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AnexoSistema / [TSIANX](TSIANX.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUALT->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
