# TSIBTA — Botão de Ação

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BotaoAcao | Botão de ação | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMODULO | Módulo Java | I |  |  | N |
| CONFIG | Configuração | C |  |  | N |
| CONTROLAACESSO | Controla Acesso | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| IDBTNACAO | Código | I |  |  | N |
| NOMEINSTANCIA | Instância | S |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| RESOURCEID | Filtro de telas | S |  |  | N |
| TECLAATALHO | Tecla de Atalho | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormularioFormatado / [TDDINS](TDDINS.md) | BotaoAcao / [TSIBTA](TSIBTA.md) | NOMEINSTANCIA->NOMEINSTANCIA | I |
| Instancia / [TDDINS](TDDINS.md) | BotaoAcao / [TSIBTA](TSIBTA.md) | NOMEINSTANCIA->NOMEINSTANCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
