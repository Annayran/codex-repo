# TGFPDV — TGFPDV

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CadastroPDV | Cadastro Pdv | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODCONTAPDV | Conta PDV | I |  |  | N |
| CODCONTATES | Conta Tesouraria | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODPDV | Código do PDV | I |  |  | N |
| CODUSUALTER | Usuário de inclusão/alteração | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTALTER | Data e Hora de inclusão/alteração | H |  |  | N |
| DTINCID | Data da inclusão do ID. da alteração | H |  |  | N |
| IDMAQUINALOCAL | Identificação PDV | S |  |  | N |
| PDVTERCEIRO | PDV Terceiro | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnaliseOcorrencia / [TGFANAOC](TGFANAOC.md) | CadastroPDV / [TGFPDV](TGFPDV.md) | CODPDV->CODPDV | I |
| CadastroPDV / [TGFPDV](TGFPDV.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| CadastroPDV / [TGFPDV](TGFPDV.md) | RelacaoPDVUsuario / [TGFPWU](TGFPWU.md) | CODPDV->CODPDV | I |
| ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | CadastroPDV / [TGFPDV](TGFPDV.md) | CODPDV->CODPDV | I |
| RelacaoPDVUsuario / [TGFPWU](TGFPWU.md) | CadastroPDV / [TGFPDV](TGFPDV.md) | CODPDV->CODPDV | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
