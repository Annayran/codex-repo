var totalSelecionadas = linhas ? linhas.length : 0;
var totalRevisadas = 0;
var totalJaRevisadas = 0;

if (totalSelecionadas === 0) {

    mensagem = "Selecione pelo menos uma comissão.";

} else {

    var codUsuarioRevisor = getUsuarioLogado();
    var consultaData = getQuery();

    /*
     * Uma única data/hora do banco é usada em todas as linhas
     * selecionadas na mesma execução.
     */
    consultaData.nativeSelect(
        "SELECT SYSDATE AS DHREV FROM DUAL"
    );

    if (!consultaData.next()) {
        mostraErro("Não foi possível obter a data e hora da revisão.");
    }

    var dataHoraRevisao = consultaData.getDate("DHREV");

    for (var i = 0; i < totalSelecionadas; i++) {

        var linha = linhas[i];
        var revisado = linha.getCampo("AD_REVISADO");

        if (String(revisado).toUpperCase() === "S") {

            totalJaRevisadas++;

        } else {

            linha.setCampo("AD_REVISADO", "S");
            linha.setCampo("AD_CODUSUREV", codUsuarioRevisor);
            linha.setCampo("AD_DHREV", dataHoraRevisao);

            totalRevisadas++;
        }
    }

    mensagem =
        totalRevisadas
        + " comissão(ões) marcada(s) como revisada(s). "
        + totalJaRevisadas
        + " já estava(m) revisada(s).";
}
