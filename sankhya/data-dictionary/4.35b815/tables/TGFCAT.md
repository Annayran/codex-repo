# TGFCAT — Categoria medicamento

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
| CategoriaMedicamento | Categoria do medicamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCAT | Código | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTALTER | DTALTER | H |  |  | N |
| DTEXE | DTEXE | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CategoriaMedicamento / [TGFCAT](TGFCAT.md) | SubCategoriaMedicamento / [TGFSCA](TGFSCA.md) | CODCAT->CODCAT | I |
| CategoriaMedicamento / [TGFCAT](TGFCAT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Produto / [TGFPRO](TGFPRO.md) | CategoriaMedicamento / [TGFCAT](TGFCAT.md) | CODCAT->CODCAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
