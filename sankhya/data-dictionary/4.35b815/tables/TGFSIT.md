# TGFSIT — LocalObraSite

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 24
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Site | Site | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABREVIATURA | Abreviatura | S |  |  | N |
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CELULAR | Celular | S |  |  | N |
| CEP | CEP | S |  |  | N |
| CNPJ | CNPJ | S |  |  | N |
| CODBAI | Cód. Bairro | I |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODEND | Cód. Endereço | I |  |  | N |
| CODSITE | Cód. Site | I |  |  | N |
| CODSITEPAI | Cód. Site pai | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| COMPLEMENTO | Complemento | S |  |  | N |
| DTALTER | Data alteração | H |  |  | N |
| DTCAD | Data cadastro | H |  |  | N |
| EMAIL | EMAIL | S |  |  | N |
| FAX | FAX | S |  |  | N |
| GRAU | Grau | I |  |  | N |
| INSCESTAD | Inscrição estadual | S |  |  | N |
| NOMESITE | Nome Site | S |  |  | N |
| NUMEND | Número endereço | S |  |  | N |
| OBSERVACOES | Observações | S |  |  | N |
| RAMAL | Ramal | I |  |  | N |
| TELEFONE | Telefone | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Site / [TGFSIT](TGFSIT.md) | CODSITE->CODSITE | I |
| CadastroProjetoSite / [TGFPSI](TGFPSI.md) | Site / [TGFSIT](TGFSIT.md) | CODSITE->CODSITE | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | Site / [TGFSIT](TGFSIT.md) | CODSITE->CODSITE | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Site / [TGFSIT](TGFSIT.md) | CODSITE->CODSITE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
