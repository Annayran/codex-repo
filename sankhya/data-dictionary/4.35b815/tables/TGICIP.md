# TGICIP — TGICIP

- Módulo técnico: [TGI](../modules/TGI.md)
- Campos: 31
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoImpressaoProposta | Configuração Impressão Proposta | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMINHOARQIMP | Arquivo Modelo de Impressão | S |  |  | N |
| CARGOCONTATO | Cargo Contato | S |  |  | N |
| CARGOVENDEDOR | Cargo Vendedor | S |  |  | N |
| CELULARVENDEDOR | Celular Vendedor | S |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODPARC | CODPARC | I |  |  | N |
| CODVEND | CODVEND | I |  |  | N |
| DEPTOCONTATO | Departamento Contato | S |  |  | N |
| EMAILCONTATO | Email Contato | S |  |  | N |
| EMAILVENDEDOR | Email Vendedor | S |  |  | N |
| IMPRESSO | Proposta Gerada | S |  |  | N |
| LOGOPARC | Arquivo Logo Cliente | S |  |  | N |
| MODALIDADEREPOSICAO | Modalidade Reposição | S |  |  | N |
| MODALIDADESUPORTE | Modalidade Suporte | S |  |  | N |
| NOMECONTATO | Nome Contato | S |  |  | N |
| NOMEPARC | Nome Cliente | S |  |  | N |
| NOMEVENDEDOR | Nome Vendedor | S |  |  | N |
| NROIMPRESSAO | Número Proposta | I |  |  | N |
| NUPROJETO | NUPROJETO | I |  |  | N |
| PRAZOENTREGA | Prazo Entrega | I |  |  | N |
| PRAZOGARANTIA | Prazo Garantia | I |  |  | N |
| PRAZOINSTALACAO | Prazo Instalação | I |  |  | N |
| PRAZOPAGAMENTO | Prazo Pagamento | I |  |  | N |
| PRAZOSUPORTE | Prazo Suporte | I |  |  | N |
| RESUMOOBJETO | Resumo Objeto | S |  |  | N |
| TELEFONECONTATO | Telefone Contato | S |  |  | N |
| TELEFONEVENDEDOR | Telefone Vendedor | S |  |  | N |
| TEXTOOBJETO | Texto Objeto | C |  |  | N |
| TEXTOOBS | Texto Obs | C |  |  | N |
| TEXTOTREINAMENTO | Texto Treinamento | C |  |  | N |
| VERSAOPROJETO | VERSAOPROJETO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | ConfiguracaoImpressaoProposta / [TGICIP](TGICIP.md) | NUPROJETO->NUPROJETO; VERSAOPROJETO->VERSAOPROJETO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
