# TIMPPV — Proposta de Venda

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 25
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimPropostaVenda | Proposta de Venda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| PPVCELULAR | Celular | S |  |  | N |
| PPVCIDADE | Cidade | I |  |  | N |
| PPVCODIGO | Código | I |  |  | N |
| PPVCORGERENTE | Corretor Gerente | I |  |  | N |
| PPVCORRETOR | Corretor | I |  |  | N |
| PPVCORSUPERVISOR | Corretor Supervisor | I |  |  | N |
| PPVCPFCNPJ | CPF | S |  |  | N |
| PPVDHALTERACAO | Dh. Alteração | H |  |  | N |
| PPVDHDES | Dh. Desistência | H |  |  | N |
| PPVDHEXPIRACAO | Dt. Expiração | H |  |  | N |
| PPVDHINCLUSAO | Dh. Inclusão | H |  |  | N |
| PPVDTPROPOSTA | Dt. Proposta | D |  |  | N |
| PPVEMAIL | E-Mail | S |  |  | N |
| PPVESTAGIO | Estágio | S |  |  | N |
| PPVIMOBILIARIA | Imobiliária | I |  |  | N |
| PPVLOTE | Lote | I |  |  | N |
| PPVMOTIVODES | Motivo Desistência | S |  |  | N |
| PPVOBSERVACAO | Observação | S |  |  | N |
| PPVORIGEM | Origem | S |  |  | N |
| PPVPROSPECT | Nome Prospect | S |  |  | N |
| PPVSEXO | Sexo | S |  |  | N |
| PPVTELEFONE1 | Telefone | S |  |  | N |
| PPVUSUALTERACAO | Usuário Alteração | I |  |  | N |
| PPVUSUINCLUSAO | Usuário Inclusão | I |  |  | N |
| PPVVALORPROPOSTA | Vlr. Proposta | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | Cidade / [TSICID](TSICID.md) | PPVCIDADE->CODCID | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | TimCorretor / [TIMCOR](TIMCOR.md) | PPVCORRETOR->CORCODIGO | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | TimCorretorGerente / [TIMCOR](TIMCOR.md) | PPVCORGERENTE->CORCODIGO | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | TimCorretorGerente2 / [TIMCOR](TIMCOR.md) | PPVCORSUPERVISOR->CORCODIGO | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | PPVIMOBILIARIA->IMBCODIGO | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | TimLote / [TIMLTE](TIMLTE.md) | PPVLOTE->LTECODIGO | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | Usuario / [TSIUSU](TSIUSU.md) | PPVUSUINCLUSAO->CODUSU | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | Usuario2 / [TSIUSU](TSIUSU.md) | PPVUSUALTERACAO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
