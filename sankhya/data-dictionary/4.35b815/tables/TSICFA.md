# TSICFA — Liberação de Filtros

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CadastroFiltrosAPI | Filtros para API | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALTERADOPOR | Alterado Por | I |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| AUTOR | Autor Filtro | I |  |  | N |
| CODFIL | Cód. Filtro | I |  |  | N |
| DTALTERACAO | Data Alteração | H |  |  | N |
| DTCRIACAO | Data Criação | H |  |  | N |
| EXPRESSAO | Expressão | C |  |  | N |
| NOMEFILTRO | Nome do Filtro | S |  |  | N |
| NUINSTANCIA | Instância | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroFiltrosAPI / [TSICFA](TSICFA.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTANCIA->NUINSTANCIA | I |
| CadastroFiltrosAPI / [TSICFA](TSICFA.md) | Usuario / [TSIUSU](TSIUSU.md) | AUTOR->CODUSU | I |
| CadastroFiltrosAPI / [TSICFA](TSICFA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | ALTERADOPOR->CODUSU | I |
| CadastroFiltrosAPI / [TSICFA](TSICFA.md) | UsuarioFiltroAPI / [TSIUFA](TSIUFA.md) | CODFIL->CODFIL | I |
| UsuarioFiltroAPI / [TSIUFA](TSIUFA.md) | CadastroFiltrosAPI / [TSICFA](TSICFA.md) | CODFIL->CODFIL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
