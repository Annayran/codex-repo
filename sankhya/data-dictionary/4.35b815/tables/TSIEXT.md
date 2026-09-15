# TSIEXT — Extrator de Dados

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 23
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExtratorDados | Extrator de Dados | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CATEGORIA | Categoria | S |  |  | N |
| CODEXTRACAO | Código | I |  |  | N |
| CODIFICACAO | Codificação | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| COMPACTAR | Compactar arquivo (.zip)? | S |  |  | N |
| CONSULTA | Consulta | C |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DESTINOARQUIVO | Destino | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| DHULTEXEC | Dt. última execução | H |  |  | N |
| EXPGATILHO | Expressão do gatilho | S |  |  | N |
| FONTEDADOS | Fonte de dados | S |  |  | N |
| FORMATO | Formato | S |  |  | N |
| INCLUIRDATA | Incluir data no nome do arquivo? | S |  |  | N |
| INCLUIRHORA | Incluir hora no nome do aquivo? | S |  |  | N |
| LOGERRO | Log de erro | C |  |  | N |
| NOMEARQUIVO | Nome do arquivo | S |  |  | N |
| STATUS | Status | S |  |  | N |
| TEMPOPROCESSAMENTO | Tempo de processamento (seg.) | I |  |  | N |
| TIPGATILHO | Tipo de gatilho | S |  |  | N |
| TIPINTERVALO | Tipo de intervalo | S |  |  | N |
| VLRINTERVALO | Valor | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExtratorDados / [TSIEXT](TSIEXT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
