# TGFAAXNM — Autorização de Acesso ao XML da NF-e p/ Marca

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AutorizacaoAcessoXmlNFeMarca | Autorização de Acesso ao XML da NF-e p/ Marca | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODMARCA | Marca | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AutorizacaoAcessoXmlNFe / [TGFAAXN](TGFAAXN.md) | AutorizacaoAcessoXmlNFeMarca / [TGFAAXNM](TGFAAXNM.md) | CODEMP->CODEMP; CODPARC->CODPARC | I |
| AutorizacaoAcessoXmlNFeMarca / [TGFAAXNM](TGFAAXNM.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| AutorizacaoAcessoXmlNFeMarca / [TGFAAXNM](TGFAAXNM.md) | MarcaProduto / [TGFMAR](TGFMAR.md) | CODMARCA->CODIGO | I |
| AutorizacaoAcessoXmlNFeMarca / [TGFAAXNM](TGFAAXNM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
