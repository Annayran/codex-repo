# TWFEVE — Evento de Processo Negocio

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EventoProcessoNegocio | Evento de Processo | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACAO | Ação | S |  |  | N |
| CODPRN | Código do Processo de Négocio | I |  |  | N |
| DESCREVENTO | Nome do Evento | S |  |  | N |
| FORMATO | Formato | S |  |  | N |
| FORMULARIO | Formulário | S |  |  | N |
| IDELEMENTO | Código do Elemento | S |  |  | N |
| NUEVENT | Número do Evento | I |  |  | N |
| ONDE | Onde | S |  |  | N |
| QUANDO | Quando | S |  |  | N |
| REFERENCIA | Referência | S |  |  | N |
| TIPOACAO | Tipo Ação | I |  |  | N |
| VERSAO | Versão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoProcessoNegocio / [TWFEVE](TWFEVE.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | CODPRN->CODPRN; VERSAO->VERSAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
