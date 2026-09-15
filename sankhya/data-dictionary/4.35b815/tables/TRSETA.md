# TRSETA — TRSETA

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 9
- Instâncias DWF: 2
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EtapaCurriculo | Etapa do Currículo | N | N |
| EtapasProcessoSelecao | Etapas Processo de Seleção | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQMODEMAIL | Modelo de e-mail p/ Seleção | S |  |  | N |
| CODETAPA | Etapa | I |  |  | N |
| CODQUESTIONARIO | Questionário | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DESCRETAPA | Descr. Etapa | S |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| NUCURRICULO | NUCURRICULO | I |  |  | N |
| NUREQUISICAO | NUREQUISICAO | I |  |  | N |
| NUSELECAO | NUSELECAO | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CargosEtapasProcesso / [TRSECG](TRSECG.md) | EtapasProcessoSelecao / [TRSETA](TRSETA.md) | CODETAPA->CODETAPA | I |
| EmpresasEtapasProcesso / [TRSECG](TRSECG.md) | EtapasProcessoSelecao / [TRSETA](TRSETA.md) | CODETAPA->CODETAPA | I |
| EtapaPersonalizada / [TRSEPE](TRSEPE.md) | EtapasProcessoSelecao / [TRSETA](TRSETA.md) | CODETAPA->CODETAPA | I |
| EtapasCargoEmpresa / [TRSECG](TRSECG.md) | EtapasProcessoSelecao / [TRSETA](TRSETA.md) | CODETAPA->CODETAPA | I |
| EtapasProcessoSelecao / [TRSETA](TRSETA.md) | Candidato / [TRSCAN](TRSCAN.md) | NUCURRICULO->NUCURRICULO; NUREQUISICAO->NUREQUISICAO; NUSELECAO->NUSELECAO | I |
| EtapasProcessoSelecao / [TRSETA](TRSETA.md) | EtapasSelecaoCargo / [TRSECG](TRSECG.md) | CODETAPA->CODETAPA | I |
| EtapasProcessoSelecao / [TRSETA](TRSETA.md) | EtapasSelecaoEmpresa / [TRSECG](TRSECG.md) | CODETAPA->CODETAPA | I |
| EtapasProcessoSelecao / [TRSETA](TRSETA.md) | StatusEtapas / [TRSSTE](TRSSTE.md) | CODETAPA->CODETAPA | I |
| PontuacaoEtapaCurriculo / [TRSPON](TRSPON.md) | EtapaCurriculo / [TRSETA](TRSETA.md) | CODETAPA->CODETAPA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
