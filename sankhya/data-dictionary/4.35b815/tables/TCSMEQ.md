# TCSMEQ — Membro de Equipe

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MembroEquipe | Membros Equipe | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODEMPFUN | Empresa | I |  |  | N |
| CODFUNCAO | Função | I |  |  | N |
| CODMEMBRO | Membro | I |  |  | N |
| FINALPARTICIPA | Final de Participação | D |  |  | N |
| INICIOPARTICIPA | Início de Participação | D |  |  | N |
| NOME | Nome | S |  |  | N |
| NOMEMEMBRO | Nome do membro | S |  |  | S |
| NUEQUIPE | Nro Equipe | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPOMEMBRO | Tipo do Membro | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Equipe / [TCSEQP](TCSEQP.md) | MembroEquipe / [TCSMEQ](TCSMEQ.md) | NUEQUIPE->NUEQUIPE | I |
| EquipeApontamento / [TAPEAP](TAPEAP.md) | MembroEquipe / [TCSMEQ](TCSMEQ.md) | NUEQUIPE->NUEQUIPE; SEQUENCIA->SEQUENCIA | I |
| MembroEquipe / [TCSMEQ](TCSMEQ.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPFUN->CODEMP | I |
| MembroEquipe / [TCSMEQ](TCSMEQ.md) | Equipe / [TCSEQP](TCSEQP.md) | NUEQUIPE->NUEQUIPE | I |
| MembroEquipe / [TCSMEQ](TCSMEQ.md) | Funcao /  | CODFUNCAO->CODFUNCAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
