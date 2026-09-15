# TGFGRD — Tabela Grade Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoGradeProduto | Configuração Grade de Produtos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUNUVERSAO | Atualizar versão | S |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DHCAD | Dh. cadastro | H |  |  | N |
| IDGRADE | Nro. único | I |  |  | N |
| NOMGRD | Nome Grade | S |  |  | N |
| NUVERSAO | Versão | I |  |  | N |
| USAMASCARA | Usar máscara no campo controle? | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoGradeProduto / [TGFGRD](TGFGRD.md) | DimensaoGrade / [TGFATB](TGFATB.md) | IDGRADE->IDGRADE | I |
| DimensaoGrade / [TGFATB](TGFATB.md) | ConfiguracaoGradeProduto / [TGFGRD](TGFGRD.md) | IDGRADE->IDGRADE | I |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | ConfiguracaoGradeProduto / [TGFGRD](TGFGRD.md) | IDGRADE->IDGRADE | I |
| Produto / [TGFPRO](TGFPRO.md) | ConfiguracaoGradeProduto / [TGFGRD](TGFGRD.md) | IDGRADE->IDGRADE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
