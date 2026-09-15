# TGIKIT — TGIKIT

- Módulo técnico: [TGI](../modules/TGI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| KitPropostaComercial | Kit | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARCFORN | Fornecedor Padrão | I |  |  | N |
| DESCRKIT | Descrição | S |  |  | N |
| NUPROJETO | Projeto | I |  |  | N |
| SEQKIT | Sequencia Kit | I |  |  | N |
| TIPKIT | Tipo Kit | S |  |  | N |
| VERSAOPROJETO | Versão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | KitPropostaComercial / [TGIKIT](TGIKIT.md) | NUPROJETO->NUPROJETO; VERSAOPROJETO->VERSAOPROJETO | I |
| KitPropostaComercial / [TGIKIT](TGIKIT.md) | ItemKitPropostaComercial / [TGIITE](TGIITE.md) | NUPROJETO->NUPROJETO; SEQKIT->SEQKIT; TIPKIT->TIPKIT; VERSAOPROJETO->VERSAOPROJETO | I |
| KitPropostaComercial / [TGIKIT](TGIKIT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
