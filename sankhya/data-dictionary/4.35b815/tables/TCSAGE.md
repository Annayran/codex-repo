# TCSAGE — VIEW TCSAGE

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 21
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Agendamento | Agendamentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATENDENTE | Atendente | I |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODSERV | Serviço | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHPREVISTA | Data Prevista | H |  |  | N |
| DHPREVISTA_FIM | Previsão de Término | H |  |  | N |
| DTPREVFECHAMENTO | Dt. Previsão Fechamento | H |  |  | N |
| EXECUTANTE | Executante | I |  |  | N |
| ID_A | Identificador B | I |  |  | N |
| ID_B | Identificador A | I |  |  | N |
| ITETEMPOGASTO | Tempo Gasto Item | I |  |  | N |
| NOMEPARC | Nome Parceiro | S |  |  | N |
| NUFAP | Número de Projeto (FAP) | I |  |  | N |
| NUMCONTRATO | Num Contrato | I |  |  | N |
| NUMETAPA | Número da etapa do projeto | I |  |  | N |
| RESPONSAVEL | Responsável | I |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| STATUS | Status | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPOOS | Tipo OS | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Agendamento / [TCSAGE](TCSAGE.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Agendamento / [TCSAGE](TCSAGE.md) | Executante / [TSIUSU](TSIUSU.md) | EXECUTANTE->CODUSU | I |
| Agendamento / [TCSAGE](TCSAGE.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| Agendamento / [TCSAGE](TCSAGE.md) | Responsavel / [TSIUSU](TSIUSU.md) | RESPONSAVEL->CODUSU | I |
| Agendamento / [TCSAGE](TCSAGE.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
