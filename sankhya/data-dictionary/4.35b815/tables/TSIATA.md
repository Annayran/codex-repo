# TSIATA — Anexo

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Anexo | Anexo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQUIVO | Arquivo anexado | S |  |  | N |
| CODATA | Cód. Anexo | I |  |  | N |
| CODEMP | Cód. Empresa | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONTEUDO | Conteúdo | B |  |  | N |
| DESCRICAO | Descrição do Arquivo | S |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| DTEXPIRA | Data da expiração | H |  |  | N |
| DTINCLUSAO | Data inclusão | H |  |  | N |
| DTVIGOR | Dt. Vigor | D |  |  | N |
| EDITA | Edita | S |  |  | N |
| ENDARQUI | Fim do Arquivo | S |  |  | N |
| IDENTIFICACAOARQUIVO | Identificação Arquivo Físico | S |  |  | N |
| LINK | LINK | S |  |  | N |
| PUBLICO | PUBLICO | S |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| SEQUENCIAPR | SEQUENCIAPR | I |  |  | N |
| TIPO | Tipo do Anexo | S |  |  | N |
| TIPOCONTEUDO | Tipo de Conteúdo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Anexo / [TSIATA](TSIATA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Procedimentos / [TGPPRC](TGPPRC.md) | Anexo / [TSIATA](TSIATA.md) | CODPROCEDIMENTO->CODATA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
