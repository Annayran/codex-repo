# TIMLGT — Log Registros

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLogRegistros | Log de Registros | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACAO | Ação | S |  |  | N |
| CAMPO | Campo | S |  |  | S |
| CHAVE | Chave Primaria | S |  |  | N |
| DHACAO | Dh. Alter | H |  |  | N |
| IPMAQUINA | IP | S |  |  | N |
| NOMEMAQUINA | Máquina | S |  |  | N |
| NOMETAB | Tabela | S |  |  | N |
| PROGRAMA | Software | S |  |  | N |
| USUBANCO | Usuário (Banco) | S |  |  | N |
| USUREDE | Usuário (Máquina) | S |  |  | N |
| USUSIS | Usuário (Sistema) | S |  |  | N |
| VALOR_NOVO | Valor Novo | S |  |  | N |
| VALOR_VELHO | Valor Antigo | S |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
