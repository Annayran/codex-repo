# TGADIG — Referências agrícolas do produto

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 4
- Instâncias DWF: 8
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DiagnosticoAgricolaProduto | Diagnósticos agrícolas do produto | S | N |
| DiagnosticoClasseAgrotoxico | Classes para agrotóxicos | S | N |
| DiagnosticoClasseToxica | Classes tóxicas para produtos | S | N |
| DiagnosticoGrupoQuimico | Grupos químicos | S | N |
| DiagnosticoPrincipioAtivo | Princípio ativo para produtos | S | N |
| MotivBloqDesbl | Motivos de Bloqueio/Desbloqueio | S | N |
| RefAgricolasDiagnosticos | Referências agrícolas/diagnósticos | S | N |
| ReferenciasAgricolas | Referências agrícolas do produto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CIENTIFICO | Nome Científico | S |  |  | N |
| DESCRDIG | Descrição | S |  |  | N |
| NUDIG | Código | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BloqDesbloqEstoque / [TGABD](TGABD.md) | MotivBloqDesbl / [TGADIG](TGADIG.md) | NUDIG->NUDIG | I |
| DiagnosticosProdutos / [TGADPR](TGADPR.md) | DiagnosticoAgricolaProduto / [TGADIG](TGADIG.md) | NUDIG->NUDIG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
