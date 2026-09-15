# TGFGEV — TABLE TGFGEV

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 21
- Instâncias DWF: 1
- Relações DWF: 13
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Verbas | Verbas | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Centro de Resultados | I |  |  | N |
| CODCONT | Contato | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODMODEMAIL | Modelo de E-mail | I |  |  | N |
| CODMODVERB | Modelo Acordo de Verba | I |  |  | N |
| CODNAT | Natureza da Verba | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROJETO | Projeto | I |  |  | N |
| CODRESP | Responsável | I |  |  | N |
| CODUSUULTALT | Usuário Última Alteração | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| DTACORD | Dt. do Acordo | D |  |  | N |
| DTLIMIT | Dt. Limite | D |  |  | N |
| FORMPAGREC | Forma de pagamento/recebimento | I |  |  | N |
| NUREVIS | Nro. Revisão | I |  |  | N |
| NUVERBA | Nro. Verba | I |  |  | N |
| OBSERV | Observação | S |  |  | N |
| OPERAC | Operação | I |  |  | N |
| STATUS | Status | S |  |  | N |
| TIPOOPER | Tipo de Operação | I |  |  | N |
| VLRVERBA | Valor da Verba | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DestinacaoVerbas / [TGFDVV](TGFDVV.md) | Verbas / [TGFGEV](TGFGEV.md) | NUVERBA->NUVERBA | I |
| Verbas / [TGFGEV](TGFGEV.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Verbas / [TGFGEV](TGFGEV.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONT->CODCONTATO; CODPARC->CODPARC | L |
| Verbas / [TGFGEV](TGFGEV.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Verbas / [TGFGEV](TGFGEV.md) | ModeloEmail / [TSIMEM](TSIMEM.md) | CODMODEMAIL->CODMODELO | I |
| Verbas / [TGFGEV](TGFGEV.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Verbas / [TGFGEV](TGFGEV.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Verbas / [TGFGEV](TGFGEV.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJETO->CODPROJ | I |
| Verbas / [TGFGEV](TGFGEV.md) | Relatorio / [TSIRFE](TSIRFE.md) | CODMODVERB->NURFE | I |
| Verbas / [TGFGEV](TGFGEV.md) | Responsavel / [TSIUSU](TSIUSU.md) | CODRESP->CODUSU | I |
| Verbas / [TGFGEV](TGFGEV.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | TIPOOPER->CODTIPOPER | I |
| Verbas / [TGFGEV](TGFGEV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUULTALT->CODUSU | I |
| VinculoVerbaBonificacao / [TGFVVV](TGFVVV.md) | Verbas / [TGFGEV](TGFGEV.md) | NUVERBA->NUVERBA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
