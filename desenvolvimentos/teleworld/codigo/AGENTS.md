# Evento de Sinal CRM

- Usar sempre Gradle com Wrapper e o template corporativo Zaya para este projeto Java.
- Adotar MVC e responsabilidade unica; nao criar camadas vazias.
- Preservar o FQCN br.com.zaya.teleworld.flow.SinalLiberacaoProcesso como adaptador externo.
- Sinais independentes no afterUpdate: AD_SOLMIN muda para S e AD_LIBPROC muda para N. Instancia em AD_IDNEGOCIO do VO; sem SQL.
- Dependencias proprietarias sao externas e compileOnly; nao versionar JARs do SDK.

