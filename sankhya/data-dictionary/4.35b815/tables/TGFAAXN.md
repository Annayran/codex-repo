# TGFAAXN — Autorização de Acesso ao XML da NF-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AutorizacaoAcessoXmlNFe | Autorização de Acesso ao XML da NF-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AutorizacaoAcessoXmlNFe / [TGFAAXN](TGFAAXN.md) | AutorizacaoAcessoXmlNFeMarca / [TGFAAXNM](TGFAAXNM.md) | CODEMP->CODEMP; CODPARC->CODPARC | I |
| AutorizacaoAcessoXmlNFe / [TGFAAXN](TGFAAXN.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| AutorizacaoAcessoXmlNFe / [TGFAAXN](TGFAAXN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | AutorizacaoAcessoXmlNFe / [TGFAAXN](TGFAAXN.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
