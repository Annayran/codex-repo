# Modelos de avisos para Relatório Formatado Sankhya

Todos os arquivos foram preparados para JasperReports/iReport 4.0.1.

- Os modelos de aviso possuem os parâmetros obrigatórios `P_CODEMP` e `P_CODFUNC` como `java.lang.String`, conforme o envio realizado pelo Sankhya.
- A query Oracle converte os valores para número com `TO_NUMBER(TRIM(...))` antes de comparar com `CODEMP` e `CODFUNC`.
- Uma linha-base gerada por `DUAL` garante a exibição do modelo mesmo quando os códigos informados não forem encontrados; nesse caso, os dados cadastrais serão exibidos em branco.
- Cada modelo contém uma consulta Oracle com as tabelas nativas `VFPFUN`, `TSIEMP` e `TSICID`.
- As consultas filtram um único funcionário por `VFPFUN.CODEMP` e `VFPFUN.CODFUNC`.
- Datas de contrato, cessação e opções do aviso permanecem nulas até a definição da fonte do módulo Pessoal.
- Todos os campos são `java.lang.String`.
- Os logotipos dos modelos empresariais estão incorporados no próprio JRXML.

## Campos por modelo

### 01_ped_dem_30d_trab.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`, `DATA_ADMISSAO`, `DATA_FIM_AVISO`

### 01_ped_dem_parc_trab.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`, `DATA_ADMISSAO`, `DATA_LIMITE_CUMPRIMENTO`, `DATA_FIM_AVISO_30_DIAS`

### 01_ped_dem_inden.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`

### 01_ped_quebra_exp_emp.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`, `DATA_INICIO_CONTRATO`, `DATA_PREVISTA_TERMINO`, `DATA_CESSACAO`

### 01_ped_termino_exp_emp.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`, `DATA_INICIO_CONTRATO`, `DATA_PREVISTA_TERMINO`, `DATA_CESSACAO`

### 01_ped_quebra_estagio.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`, `DATA_PREVISTA_TERMINO`, `DATA_CESSACAO`

### 02_av_prev_inden_dsjc.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`

### 02_av_quebra_exp_empresa.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`, `DATA_PREVISTA_TERMINO`, `DATA_CESSACAO`

### 02_av_termino_exp_empresa.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`, `DATA_PREVISTA_TERMINO`

### 02_av_trab_dsjc.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`, `DIAS_AVISO`, `MARCA_REDUCAO_DUAS_HORAS`, `MARCA_AUSENCIA_SETE_DIAS`

Para os campos `MARCA_*`, retorne `X` na opção selecionada e uma string vazia na opção não selecionada.

### 02_av_quebra_estagio_empresa.jrxml

`EMPRESA`, `COLABORADOR`, `CIDADE`, `DATA_DOCUMENTO`, `DATA_PREVISTA_TERMINO`, `DATA_CESSACAO`
