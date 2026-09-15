# TGFIMAL — Imagens Alternativas de Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImagemAlternativaProduto | Imagens Alternativas de Produto | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Cód. Produto | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DHALTER | Data / Hora Alteração | H |  |  | N |
| NOMEARQ | Nome do Arquivo | S |  |  | N |
| NUIMG | Cód. Imagem | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImagemAlternativaProduto / [TGFIMAL](TGFIMAL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ImagemAlternativaProduto / [TGFIMAL](TGFIMAL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
