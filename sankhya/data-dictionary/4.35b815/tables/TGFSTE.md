# TGFSTE — SubClasse Terapêutica

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SubClasseTerapeutica | SubClasse Terapêutica | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTER | Código | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTALTER | DTALTER | H |  |  | N |
| DTEXE | DTEXE | H |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ClasseTerapeutica / [TGFTER](TGFTER.md) | SubClasseTerapeutica / [TGFSTE](TGFSTE.md) | CODTER->CODTER | I |
| Produto / [TGFPRO](TGFPRO.md) | SubClasseTerapeutica / [TGFSTE](TGFSTE.md) | CODTER->CODTER; SEQSTE->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
