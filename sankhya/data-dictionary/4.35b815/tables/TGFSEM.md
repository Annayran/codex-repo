# TGFSEM — Configurações por Empresa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoEmpresa | Configurações por Empresa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CNAE | CNAE | I |  |  | N |
| CODCLASTRIBNAC | Código Classificação Tributária Nacional | S |  |  | N |
| CODCTACTBEFD | Conta Contábil para EFD | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODINDOPERPOREMP | Código Indicador de Operação de Fornecimento (indoper) | S |  |  | N |
| CODLST | Tipo de Serviço | I |  |  | N |
| CODNATOPERISS | Cód. Natureza Oper. ISS (NFS-e) | S |  |  | N |
| CODNBS | Código NBS | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| CODTRIBMUNISS | Cód. Trib. Município NFS-e | S |  |  | N |
| ENQREINTEGRA | Enquadrado no Reintegra/Prev. | I |  |  | N |
| PERCCMTEST | % Carga Média Trib. Estadual | F |  |  | N |
| PERCCMTFED | % Carga Média Trib. Federal | F |  |  | N |
| PERCCMTMUN | % Carga Média Trib. Municipal | F |  |  | N |
| REGIMEESPTRIBISS | Regime esp. tributação ISS (NFS-e) | S |  |  | N |
| TIPOSN | Tipo de Partilha/Anexo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | CodigoIndicadorOperacao / [TGFINDOPER](TGFINDOPER.md) | CODINDOPERPOREMP->CODINDOPER | I |
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | ListaServicoSintegra / [TGFLST](TGFLST.md) | CODLST->CODLST | I |
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | NaturezaOperacao / [TGFNTP](TGFNTP.md) | CODNATOPERISS->CODNATOPERISS | I |
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | PlanoConta /  | CODCTACTBEFD->CODCTACTB | L |
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
