# TPQPLA — Planejamento

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Planejamento | Planejamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ASSUNTOEMAIL | Assunto do email | S |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODQUEST | Cód. Questionário | I |  |  | N |
| CODSMTP | Conta SMTP | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUAVALIADOR | Cód. Avaliador | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Data/Hora Alteração | H |  |  | N |
| DTFIM | Data Fim | H |  |  | N |
| DTINICIO | Data Inicio | H |  |  | N |
| FORMADEAPLICAR | Forma de Aplicar | S |  |  | N |
| INTERVALOENVIO | Intervalo de envio | I |  |  | N |
| MAXENVIO | Máx. envio notificações | I |  |  | N |
| MODELOEMAIL | Modelo de email | C |  |  | N |
| NUPLA | Cód. Planejamento | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DestinatarioPlanejamento / [TPQDPL](TPQDPL.md) | Planejamento / [TPQPLA](TPQPLA.md) | NUPLA->NUPLA | I |
| EnvioQuestionario / [TPQENV](TPQENV.md) | Planejamento / [TPQPLA](TPQPLA.md) | NUPLA->NUPLA | I |
| Pesquisa / [TPQPES](TPQPES.md) | Planejamento / [TPQPLA](TPQPLA.md) | NUPLA->NUPLA | I |
| Planejamento / [TPQPLA](TPQPLA.md) | DestinatarioPlanejamento / [TPQDPL](TPQDPL.md) | NUPLA->NUPLA | I |
| Planejamento / [TPQPLA](TPQPLA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Planejamento / [TPQPLA](TPQPLA.md) | Questionario / [TPQQUE](TPQQUE.md) | CODQUEST->CODQUEST | I |
| Planejamento / [TPQPLA](TPQPLA.md) | ServidorSMTP / [TSISMTP](TSISMTP.md) | CODSMTP->CODSMTP | I |
| Planejamento / [TPQPLA](TPQPLA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUAVALIADOR->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
