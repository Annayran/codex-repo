# TGFTER — Classe terapêutica

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ClasseTerapeutica | Classe terapêutica | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTER | Código | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTALTER | DTALTER | H |  |  | N |
| DTEXE | DTEXE | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ClasseTerapeutica / [TGFTER](TGFTER.md) | SubClasseTerapeutica / [TGFSTE](TGFSTE.md) | CODTER->CODTER | I |
| ClasseTerapeutica / [TGFTER](TGFTER.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Produto / [TGFPRO](TGFPRO.md) | ClasseTerapeutica / [TGFTER](TGFTER.md) | CODTER->CODTER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
