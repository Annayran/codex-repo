# Publicação do módulo Java

Arquivo: `proauto-fila-reserva.jar`

Compatibilidade de código: Java 8.

Classe da ação agendada:

`br.com.zaya.proauto.filareserva.sankhya.schedulers.AtualizaProvisaoScheduledAction`

## Procedimento

1. Preservar o JAR atualmente publicado, se existir.
2. Publicar o novo JAR no Cadastro de Módulos Java do ambiente TESTE.
3. Cadastrar ou atualizar a ação agendada com a classe acima.
4. Manter a recorrência desabilitada.
5. Executar manualmente uma vez.
6. Conferir log de início e conclusão sem exceção.
7. Habilitar recorrência somente após homologação funcional.

O Java não possui regra de negócio duplicada: ele chama
`STP_ATUALIZA_PROV_FILA_PRA`, a mesma procedure utilizada pelo botão manual.

Observação: o ambiente alvo é Sankhya 4.36b124. A carga e execução no TESTE são
obrigatórias para comprovar compatibilidade do módulo nessa versão.

