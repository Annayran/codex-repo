# TGMMSG — GM Mensagens

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 24
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Mensagem | GM Mensagens | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODGER | Cód. do Gerente | I |  |  | N |
| CODGRUPOPROD | Cód.Grupo de Produtos | I |  |  | N |
| CODLOCAL | Cód.Local | I |  |  | N |
| CODMETA | Cód.Meta | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODPROD | Cód.Produto | I |  |  | N |
| CODPROJ | Cód.Projeto | I |  |  | N |
| CODREG | Cód.Região | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| CODVEND | Cód.Vendedor | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DTALTER | DTALTER | H |  |  | N |
| DTREF | Data de Referência | H |  |  | N |
| MARCA | Marca | S |  |  | N |
| MSG | Mensagem | S |  |  | N |
| MSGAVISO | Mensagem de Aviso | S |  |  | N |
| PERCAVISO | Percentual de Aviso | F |  |  | N |
| PODEANTECIPAR | Pode Antecipar? | S |  |  | N |
| PODESUPLEMENTAR | Pode Suplementar? | S |  |  | N |
| PODETRANSFSALDO | Pode Transferir Saldo? | S |  |  | N |
| TIPOMSG | Tipo de Mensagem | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | Mensagem / [TGMMSG](TGMMSG.md) | CODMETA->CODMETA | I |
| Mensagem / [TGMMSG](TGMMSG.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
