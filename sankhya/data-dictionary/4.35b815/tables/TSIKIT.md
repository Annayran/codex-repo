# TSIKIT — Configuração de Kit

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoKit | Configuração de Kit | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CALCIMPKIT | Calcula imposto do componente | S |  |  | N |
| CODCONFKIT | Código | I |  |  | N |
| DESCRCONFKIT | Descrição | S |  |  | N |
| DISTDESCKITCOMP | Distribuir desconto do kit nos componentes | S |  |  | N |
| DISTRIBUIKITCOMP | Distribuir preço do kit nos componentes | S |  |  | N |
| EXPLODCOMP | Explodir componentes na grade de matéria prima da central | S |  |  | N |
| FAKITESTCOMP | Faturar apenas KIT com estoque de todos os componentes | S |  |  | N |
| SOMACUSTOCOMPKIT | Soma custo do componente ao kit | S |  |  | N |
| SOMAICMSCOMPKIT | Soma ICMS do componente ao kit (Art. 42 do RICMS/2002-MG) | S |  |  | N |
| SOMAPRECOCOMPKIT | Soma preço do componente ao kit | S |  |  | N |
| UTILPRECOABACOMP | Utiliza preço sugerido na aba 'Componentes' | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | ConfiguracaoKit / [TSIKIT](TSIKIT.md) | CODCONFKIT->CODCONFKIT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
