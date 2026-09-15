# TSSITP — ItensPacote

- Módulo técnico: [TSS](../modules/TSS.md)
- Campos: 22
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItensPacote | Itens Pacote | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQUIVO | Arquivo | B |  |  | N |
| CADASTROPADRAO | Cadastro Padrão | C |  |  | N |
| CODIGO | Layout EDI remessa | I |  |  | N |
| CODLIC | Código Licença Mestre | I |  |  | N |
| CODMODNF | Modelo de Impressão | I |  |  | N |
| CODRET | Layout EDI retorno | I |  |  | N |
| DTALTERACAO | Dt. Alteração | H |  |  | N |
| MODULO | Módulo EDI | S |  |  | N |
| NOMEARQUIVO | Arquivo Anexo | S |  |  | N |
| NOMEITEM | Nome | S |  |  | N |
| NUDSB | Dashboard | I |  |  | N |
| NUINSTANCIA | Instância | I |  |  | N |
| NUINSTANCIACAD | Instância | I |  |  | N |
| NUPAC | Nº Pacote | I |  |  | N |
| NURFE | Relatório iReport | I |  |  | N |
| ORDEMPK | Campos PKs do cadastro padrão | S |  |  | N |
| SCRIPT_BD | Script | C |  |  | N |
| SEQITP | Sequência | I |  |  | N |
| SEQUENCIA | Cód. Item | I |  |  | N |
| STOPONERROR | Se ocorrer erro ao exec. script | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPO_BD | Tipo do Banco | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensPacote / [TSSITP](TSSITP.md) | Dashboard / [TSIDSB](TSIDSB.md) | NUDSB->NUDSB | I |
| ItensPacote / [TSSITP](TSSITP.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODIGO->CODIGO | I |
| ItensPacote / [TSSITP](TSSITP.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTANCIA->NUINSTANCIA | I |
| ItensPacote / [TSSITP](TSSITP.md) | InstanciaCadastro / [TDDINS](TDDINS.md) | NUINSTANCIACAD->NUINSTANCIA | I |
| ItensPacote / [TSSITP](TSSITP.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | CODMODNF->CODMODNF | I |
| ItensPacote / [TSSITP](TSSITP.md) | PacotesSankhyaStore / [TSSPAC](TSSPAC.md) | NUPAC->NUPAC | I |
| ItensPacote / [TSSITP](TSSITP.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| ItensPacote / [TSSITP](TSSITP.md) | RetornoMovimentoBancario / [TSIRET](TSIRET.md) | CODRET->CODIGO | I |
| PacotesSankhyaStore / [TSSPAC](TSSPAC.md) | ItensPacote / [TSSITP](TSSITP.md) | NUPAC->NUPAC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
