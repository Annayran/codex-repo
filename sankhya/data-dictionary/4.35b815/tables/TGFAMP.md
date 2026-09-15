# TGFAMP — Vinculo de Amostras por Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VinculoAmostrasProduto | Tipos de Amostra | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODTIPAMOSTRA | Tipo Amostra | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DTALTER | Data e Hora Alteração | H |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| QTDPORAMOSTRA | Quantidade | F |  |  | N |
| REQNOTAENTPA | Requisição de Nota de Entrada de PA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | VinculoAmostrasProduto / [TGFAMP](TGFAMP.md) | CODPROD->CODPROD | I |
| VinculoAmostrasProduto / [TGFAMP](TGFAMP.md) | TipoAmostra /  | CODTIPAMOSTRA->CODTIPAMOSTRA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
