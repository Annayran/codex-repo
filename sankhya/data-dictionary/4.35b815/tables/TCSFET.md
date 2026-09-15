# TCSFET — Etapa de Projeto

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 24
- Instâncias DWF: 3
- Relações DWF: 18
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EtapaPai | Etapa pai | N | N |
| EtapaProjeto | Etapa de Projeto | S | N |
| EtapaProjeto2 | Etapa Projeto 2 | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVEIMP | Chave para importação do Project | I |  |  | N |
| CODPROD | Serviço | I |  |  | N |
| CONCLUIDA | Concluída | S |  |  | N |
| CONCLUSAOAUTOMATICA | Concluir ao fechar Sub-OS | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTCEDOFIMPREV | Fim | D |  |  | N |
| DTCEDOINIPREV | Início | D |  |  | N |
| DTTARDEFIMPREV | Fim Limite | D |  |  | N |
| DTTARDEINIPREV | Início Limite | D |  |  | N |
| EXIGEREQ | Exigir requisito em Sub-OS | S |  |  | N |
| NUFAP | Número da FAP | I |  |  | N |
| NUMETAPA | Número da Etapa | I |  |  | N |
| NUMETAPAPAI | Número da Etapa Pai | I |  |  | N |
| NUMMODELO | Número do Modelo | I |  |  | N |
| NUMSEQ | Sequência | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| SUPLEMENTA | Empresta | S |  |  | N |
| SUPLEMENTADO | Pega Emprestado | S |  |  | N |
| TIPVALOR | Tipo de Valor | S |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRSUPLEMENTA | Hrs. Suplementa | F |  |  | N |
| VLRSUPLEMENTADO | Hrs. Suplementado | F |  |  | N |
| VLRSUPLEMENTADOREA | Hrs. Suplementado Real | F |  |  | N |
| VLRSUPLEMENTAREA | Hrs. Suplementa Real | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Alerta / [TCSALE](TCSALE.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| EquipeEtapa / [TCSFEE](TCSFEE.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| EtapaPorRequisicao / [TCSEXR](TCSEXR.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| EtapaPredecessora / [TCSEPD](TCSEPD.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPAPRED->NUMETAPA | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | EquipeEtapa / [TCSFEE](TCSFEE.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | EtapaPai / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPAPAI->NUMETAPA | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | EtapaPredecessora / [TCSEPD](TCSEPD.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPAPAI | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | HistoricoEtapa / [TCSEHI](TCSEHI.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | InsumoEtapa / [TCSFPE](TCSFPE.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | ServicoEtapa / [TCSESA](TCSESA.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| HistoricoSuplementacao / [TCSHSE](TCSHSE.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPAORIG->NUMETAPA | I |
| HistoricoSuplementacao / [TCSHSE](TCSHSE.md) | EtapaProjeto2 / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPADEST->NUMETAPA | I |
| InsumoEtapa / [TCSFPE](TCSFPE.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP | I |
| RequisitoEtapa / [TCSREE](TCSREE.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
