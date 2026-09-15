# TGFGVP — Pré-Acordo de Verbas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 23
- Instâncias DWF: 1
- Relações DWF: 12
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PreAcordoVerba | Pré-Acordo de Verbas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Centro de resultados | I |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODPARC | Parceiro do acordo | I |  |  | N |
| CODPARCAPU | Parceiro para apuração | I |  |  | N |
| CODPROJETO | Projeto | I |  |  | N |
| CODRESP | Responsável | I |  |  | N |
| CODUSUULTALT | Usuário última alteração | I |  |  | N |
| DESCRICAO | Observação | S |  |  | N |
| DTNEG | Data e hora da negociação | H |  |  | N |
| DTVIGENCIAFIN | Término da vigência | D |  |  | N |
| DTVIGENCIAINI | Início da vigência | D |  |  | N |
| ERROJOB | Log do processamento | S |  |  | N |
| FORMPAGREC | Forma de pagamento | S |  |  | N |
| MODVERB | Modelo de negociação | S |  |  | N |
| NUPREACORDO | Nro acordo | I |  |  | N |
| NUVERBA | Nro da verba | I |  |  | N |
| OPERAC | Operação | S |  |  | N |
| PROGRESSO | Progresso | S |  |  | N |
| STATUS | Status | S |  |  | N |
| STATUSCRITERIO | Status dos critérios | S |  |  | N |
| VERBAAPURADA | Verba apurada | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | PreAcordoVerba / [TGFGVP](TGFGVP.md) | NUPREACORDO->NUPREACORDO | I |
| CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | PreAcordoVerba / [TGFGVP](TGFGVP.md) | NUPREACORDO->NUPREACORDO | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | NUPREACORDO->NUPREACORDO | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | CODPARCAPU->CODPARC | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJETO->CODPROJ | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODRESP->CODUSU | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUULTALT->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
