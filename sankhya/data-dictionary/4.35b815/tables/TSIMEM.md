# TSIMEM — Modelo e-mail

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 2
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ModeloEmail | Modelo de E-mail | N | N |
| ModeloEmail2 | Modelo de E-mail | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ASSUNTO | Assunto | S |  |  | N |
| CODMODELO | Código | I |  |  | N |
| CODSMTP | Conta SMTP | I |  |  | N |
| CODUSUREMET | Cód. Usuário Remetente | I |  |  | N |
| CONTEUDO | Conteúdo | C |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| RESPONDERPARA | Responder para | S |  |  | N |
| TIPO | Tipo de modelo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEmail / [TSIMEM](TSIMEM.md) | MODEMAILLIBLIM->CODMODELO | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEmail2 / [TSIMEM](TSIMEM.md) | CODMODEMAILNFE->CODMODELO | I |
| ModeloEmail / [TSIMEM](TSIMEM.md) | ServidorSMTP / [TSISMTP](TSISMTP.md) | CODSMTP->CODSMTP | I |
| ModeloEmail / [TSIMEM](TSIMEM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUREMET->CODUSU | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | ModeloEmail / [TSIMEM](TSIMEM.md) | CODMODELO->CODMODELO | I |
| Verbas / [TGFGEV](TGFGEV.md) | ModeloEmail / [TSIMEM](TSIMEM.md) | CODMODEMAIL->CODMODELO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
