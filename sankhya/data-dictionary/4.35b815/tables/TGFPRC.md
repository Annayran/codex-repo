# TGFPRC — Processo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Processo | Tabela de Processos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHFIM | Data Fim | H |  |  | N |
| DHINI | Data Início | H |  |  | N |
| IDPROC | Cód. Processo | S |  |  | N |
| MSGRESULT | Resultado | C |  |  | N |
| NROUNICO | Nro. Único | I |  |  | N |
| PARAMS | Parâmetros | S |  |  | N |
| STATUS | Status | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| XMLRESULT | XMLRESULT | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Processo / [TGFPRC](TGFPRC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
