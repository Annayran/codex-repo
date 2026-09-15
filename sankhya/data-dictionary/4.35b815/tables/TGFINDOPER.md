# TGFINDOPER — Indicador de Operação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CodigoIndicadorOperacao | Indicador de Operação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CARACFORNEC | Caracteristica do Fornecimento | S |  |  | N |
| CODINDOPER | Código Indicador de Operação | S |  |  | N |
| DESCRINDOPER | Descrição do Indicador de Operação | S |  |  | N |
| LOCALFORNEC | Local do Fornecimento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | CodigoIndicadorOperacao / [TGFINDOPER](TGFINDOPER.md) | CODINDOPERPOREMP->CODINDOPER | I |
| Servico / [TGFPRO](TGFPRO.md) | CodigoIndicadorOperacao / [TGFINDOPER](TGFINDOPER.md) | CODINDOPERPROD->CODINDOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | CodigoIndicadorOperacao / [TGFINDOPER](TGFINDOPER.md) | CODINDOPER->CODINDOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
