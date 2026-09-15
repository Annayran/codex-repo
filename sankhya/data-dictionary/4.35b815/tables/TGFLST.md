# TGFLST — Lista de Serviços

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ListaServicoSintegra | Lista de Serviços | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODLST | Código | I |  |  | N |
| CODLSTPAI | Código Pai | I |  |  | N |
| DESCRLST | Descrição | S |  |  | N |
| DESDNAC | Desdobro Nacional | S |  |  | N |
| GERAVLRTOTREC | A tag "Valor Total Recebido" é obrigatória para NFSe SP? | S |  |  | N |
| GRAU | Grau | I |  |  | N |
| INFORMACODOBRA | Exige Cód. da Obra | S |  |  | N |
| TIPOREPASSE | Tipo de repasse | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaISS / [TGFISS](TGFISS.md) | ListaServicoSintegra / [TGFLST](TGFLST.md) | CODLST->CODLST | I |
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | ListaServicoSintegra / [TGFLST](TGFLST.md) | CODLST->CODLST | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ListaServicoSintegra / [TGFLST](TGFLST.md) | CODLST->CODLST | I |
| Servico / [TGFPRO](TGFPRO.md) | ListaServicoSintegra / [TGFLST](TGFLST.md) | CODLST->CODLST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
