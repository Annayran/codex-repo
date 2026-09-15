# TCACPR — Precificação do Curso

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 43
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrecificacaoCurso | Precificação do Curso | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANO | Ano | I |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODNATRECEITA | Cód. Natureza Receita | I |  |  | N |
| CONSULTORES | Consultores | I |  |  | N |
| CRPROGRAMA | Cód.Centro de resultado do Programa | I |  |  | N |
| CUSTOCONSULTTOT | Custo Total da Consultoria | F |  |  | N |
| CUSTOCONSULTUNIT | Custo Unitário da Consultoria | F |  |  | N |
| CUSTOSDIR | Custos Diretos | F |  |  | N |
| CUSTOSINDIR | Custos Indiretos | F |  |  | N |
| DEPRECIACAO | Depreciação | F |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DESPADMIN | Despesa Administrativa | F |  |  | N |
| DESPCOM | Despesa Comercial | F |  |  | N |
| DIAS | Dias | I |  |  | N |
| EBIT | EBIT | F |  |  | N |
| EBITDA | EBITDA | F |  |  | N |
| FINANCLIQ | Financeiro Líquido | F |  |  | N |
| HORAS | Horas | I |  |  | N |
| HORASSALA | Horas Sala | I |  |  | N |
| IMPOSTOS | Imposto | F |  |  | N |
| IRCS | IRCS | F |  |  | N |
| LUCBRUT | Lucro Bruto | F |  |  | N |
| MARKUP | Markup | F |  |  | N |
| MES | Mês | I |  |  | N |
| NUCURSO | Número Curso | I |  |  | N |
| NUSALA | Número Sala | I |  |  | N |
| OUTRASDESP | Outras Despesas | F |  |  | N |
| PARTICIPANTES | Participantes | I |  |  | N |
| PERCCOFINS | Percentual FINS | F |  |  | N |
| PERCCSL | Percentual CSL | F |  |  | N |
| PERCINSS | Percentual INSS | F |  |  | N |
| PERCISS | Percentual ISS | I |  |  | N |
| PERCPIS | Percentual PIS | F |  |  | N |
| PERIODOS | Períodos | I |  |  | N |
| PRECOTOTAL | Preço Total | F |  |  | N |
| PROGRAMA | Programa | I |  |  | N |
| PUBLICOALVO | Público Alvo | S |  |  | N |
| RECBRUT | Receita Bruta | F |  |  | N |
| RECLIQ | Receita Líquida | F |  |  | N |
| RESULTADO_LIQ | Resultado Líquido | F |  |  | N |
| TURMAS | Turmas | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensPrecificacao / [TCACPI](TCACPI.md) | PrecificacaoCurso / [TCACPR](TCACPR.md) | NUCURSO->NUCURSO | I |
| PrecificacaoCurso / [TCACPR](TCACPR.md) | CentroResultadoDespesa / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| PrecificacaoCurso / [TCACPR](TCACPR.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| PrecificacaoCurso / [TCACPR](TCACPR.md) | ItensPrecificacao / [TCACPI](TCACPI.md) | NUCURSO->NUCURSO | I |
| PrecificacaoCurso / [TCACPR](TCACPR.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT; CODNATRECEITA->CODNAT | I |
| PrecificacaoCurso / [TCACPR](TCACPR.md) | Sala / [TCASAL](TCASAL.md) | NUSALA->NUSALA | I |
| Sala / [TCASAL](TCASAL.md) | PrecificacaoCurso / [TCACPR](TCACPR.md) | NUSALA->NUSALA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
