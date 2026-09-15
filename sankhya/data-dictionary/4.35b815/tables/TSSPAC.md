# TSSPAC — Pacotes Sankhya Store

- Módulo técnico: [TSS](../modules/TSS.md)
- Campos: 23
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PacotesSankhyaStore | Pacotes Sankhya Place | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQUIVODOC | ARQUIVODOC | B |  |  | N |
| CATEGORIA | Categoria | S |  |  | N |
| CGC_CPF | CNPJ Cliente | S |  |  | N |
| CODUSUALTER | Usuário Alteração | I |  |  | N |
| CODUSUCAD | Usuário Cadastro | I |  |  | N |
| CRIPTOGRAFADO | Solução protegida | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DESCRTECNICA | Descrição Técnica | S |  |  | N |
| DHALTER | Data Alteração | H |  |  | N |
| DHCAD | Data Cadastro | H |  |  | N |
| EXCLUSIVA | Solução exclusiva | S |  |  | N |
| GRATIS | Gratis | S |  |  | N |
| IMAGEM | Imagem | B |  |  | N |
| MODSPT | Modelo de suporte | S |  |  | N |
| NOMEARQUIVO | Documento | S |  |  | N |
| NOMEPAC | Nome | S |  |  | N |
| NUPAC | Nº Pacote | I |  |  | N |
| TERMOUSO | Termos de uso | S |  |  | N |
| TIPOBD | Funciona em | S |  |  | N |
| TIPOPAC | Tipo | S |  |  | N |
| VLRMENSAL | Mensalidade (R$) | F |  |  | N |
| VLRPACOTE | Valor da instalação (R$) | F |  |  | N |
| VLRTRANSACAO | Transações adicionais (R$) | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensPacote / [TSSITP](TSSITP.md) | PacotesSankhyaStore / [TSSPAC](TSSPAC.md) | NUPAC->NUPAC | I |
| PacotesSankhyaStore / [TSSPAC](TSSPAC.md) | ItensPacote / [TSSITP](TSSITP.md) | NUPAC->NUPAC | I |
| PacotesSankhyaStore / [TSSPAC](TSSPAC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUCAD->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
