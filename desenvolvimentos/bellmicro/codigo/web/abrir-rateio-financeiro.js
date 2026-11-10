var titulo = '[ZAYA.IT] - Rateio de despesas';
var entryPoint = 'RateioDespesas.jsp';
var nuGdg = '315';

// Informar os códigos cadastrados no Sankhya:
var codAcaoSimular = 'CODIGO_SIMULAR';
var codAcaoRecalcular = 'CODIGO_RECALCULAR';
var codAcaoEfetivar = 'CODIGO_EFETIVAR';

// API de seleção deve ser confirmada no contexto JavaScript instalado.
var selecionadas = typeof contexto.getLinhas === 'function' ? contexto.getLinhas()
    : (typeof getLinhas === 'function' ? getLinhas() : []);
var nufin = selecionadas && selecionadas.length === 1
    ? String(typeof selecionadas[0].getCampo === 'function'
        ? selecionadas[0].getCampo('NUFIN') : selecionadas[0].NUFIN) : '';

if (![codAcaoSimular, codAcaoRecalcular, codAcaoEfetivar].every(function (id) {
    return /^[1-9][0-9]{0,9}$/.test(id);
}) || codAcaoSimular === codAcaoRecalcular || codAcaoSimular === codAcaoEfetivar
        || codAcaoRecalcular === codAcaoEfetivar) {
    contexto.mostraErro('Configure os três códigos distintos das ações Java de rateio.');
} else if (!/^[1-9][0-9]{0,9}$/.test(nufin)) {
    contexto.mostraErro('Selecione um único título financeiro. Confira a API de seleção do contexto JavaScript.');
} else {
    var largura = 1440;
    var altura = 780;
    var headerAltura = 50;

    var url = '/mge/html5component.mge'
        + '?entryPoint=' + encodeURIComponent(entryPoint)
        + '&nuGdg=' + encodeURIComponent(nuGdg)
        + '&codAcaoSimular=' + encodeURIComponent(codAcaoSimular)
        + '&codAcaoRecalcular=' + encodeURIComponent(codAcaoRecalcular)
        + '&codAcaoEfetivar=' + encodeURIComponent(codAcaoEfetivar)
        + '&nufin=' + encodeURIComponent(nufin);

    var seletorModal =
        'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in';

    var seletorDialog = seletorModal + ' > div';
    var seletorContent = seletorDialog + ' > div';
    var seletorHeader =
        seletorContent + ' > div.modal-header.ng-scope';
    var seletorBody = seletorContent + ' > div.modal-body';
    var seletorFooter = seletorContent + ' > div.modal-footer';

    var mensagem = ''
        + '<style>'

        /* Estrutura do modal */
        + seletorDialog + '{'
        + 'width:' + largura + 'px;'
        + 'max-width:calc(100vw - 40px);'
        + 'margin:14px auto 0;'
        + '}'

        + seletorContent + '{'
        + 'width:100%!important;'
        + 'height:' + altura + 'px!important;'
        + 'max-height:calc(100vh - 40px)!important;'
        + 'display:flex!important;'
        + 'flex-direction:column!important;'
        + 'overflow:hidden!important;'
        + 'border:1px solid rgba(28,58,109,.20)!important;'
        + 'border-radius:10px!important;'
        + 'box-shadow:0 18px 50px rgba(28,58,109,.22)!important;'
        + '}'

        /* Remove o rodapé padrão */
        + seletorFooter + '{'
        + 'display:none!important;'
        + '}'

        /* Cabeçalho do rateio */
        + seletorHeader + '{'
        + 'height:' + headerAltura + 'px!important;'
        + 'min-height:' + headerAltura + 'px!important;'
        + 'padding:0 14px!important;'
        + 'display:flex!important;'
        + 'align-items:center!important;'
        + 'background:#1c3a6d!important;'
        + 'color:#fff!important;'
        + 'border:0!important;'
        + 'border-bottom:4px solid #00aeb9!important;'
        + 'box-sizing:border-box!important;'
        + 'font-family:Poppins,Inter,"Segoe UI",Arial,sans-serif!important;'
        + '}'

        + seletorHeader + ' sk-hbox{'
        + 'order:1!important;'
        + 'flex:1 1 auto!important;'
        + '}'

        /* Substitui "Informação" pelo título do rateio */
        + seletorHeader + ' h5{'
        + 'margin:0!important;'
        + 'flex:1 1 auto!important;'
        + 'font-size:0!important;'
        + 'line-height:1!important;'
        + 'color:#fff!important;'
        + 'text-shadow:none!important;'
        + '}'

        + seletorHeader + ' h5 span{'
        + 'display:none!important;'
        + '}'

        + seletorHeader + ' h5:before{'
        + 'content:"Rateio de despesas";'
        + 'display:inline-block;'
        + 'padding-left:11px;'
        + 'border-left:4px solid #00aeb9;'
        + 'color:#fff;'
        + 'font-size:14px;'
        + 'font-weight:600;'
        + 'line-height:22px;'
        + 'letter-spacing:.2px;'
        + '}'

        /* Botão X */
        + seletorHeader + ' button,'
        + seletorHeader + ' .close{'
        + 'order:2!important;'
        + 'flex:0 0 32px!important;'
        + 'width:32px!important;'
        + 'height:32px!important;'
        + 'min-height:32px!important;'
        + 'margin:0 0 0 auto!important;'
        + 'padding:0!important;'
        + 'border:0!important;'
        + 'border-radius:50%!important;'
        + 'background:transparent!important;'
        + 'color:#fff!important;'
        + 'opacity:.82!important;'
        + 'text-shadow:none!important;'
        + '}'

        + seletorHeader + ' button:hover,'
        + seletorHeader + ' .close:hover{'
        + 'background:rgba(0,174,185,.22)!important;'
        + 'opacity:1!important;'
        + '}'

        /* Força o X para branco */
        + seletorHeader + ' button span,'
        + seletorHeader + ' .close span,'
        + seletorHeader + ' button i,'
        + seletorHeader + ' .close i{'
        + 'display:inline!important;'
        + 'color:#fff!important;'
        + '-webkit-text-fill-color:#fff!important;'
        + 'opacity:1!important;'
        + 'font-size:20px!important;'
        + 'line-height:30px!important;'
        + '}'

        + seletorHeader + ' button *,'
        + seletorHeader + ' .close *,'
        + seletorHeader + ' .fa-times{'
        + 'color:#fff!important;'
        + '-webkit-text-fill-color:#fff!important;'
        + 'opacity:1!important;'
        + '}'

        + seletorHeader + ' button svg,'
        + seletorHeader + ' .close svg,'
        + seletorHeader + ' button svg *,'
        + seletorHeader + ' .close svg *{'
        + 'fill:#fff!important;'
        + 'stroke:#fff!important;'
        + 'color:#fff!important;'
        + '}'

        /* Conteúdo HTML5 */
        + seletorBody + '{'
        + 'padding:0!important;'
        + 'flex:1 1 auto!important;'
        + 'overflow:hidden!important;'
        + '}'

        + seletorBody + ' > div,'
        + seletorBody + ' iframe{'
        + 'width:100%!important;'
        + 'height:100%!important;'
        + 'display:block!important;'
        + 'border:0!important;'
        + 'padding:0!important;'
        + '}'

        + '</style>'

        + '<iframe'
        + ' width="100%" height="700" style="width:100%;height:calc(100vh - 140px);min-height:450px;border:0;display:block;"'
        + ' title="' + titulo + '"'
        + ' src="' + url + '"'
        + '></iframe>';

    contexto.setMensagemRetorno(mensagem);
}