var titulo = '[TOPNUTRI] - Importação de Fórmulas';
var entryPoint = 'importacaoFormulas.jsp';
var nuGdg = 'PENDENTE_CONFIGURACAO';
var codAcaoPrevalidar = 'PENDENTE_CONFIGURACAO';
var codAcaoProcessar = 'PENDENTE_CONFIGURACAO';

if (nuGdg.indexOf('PENDENTE_') === 0
        || codAcaoPrevalidar.indexOf('PENDENTE_') === 0
        || codAcaoProcessar.indexOf('PENDENTE_') === 0) {
    contexto.mostraErro('Configure NU_GDG e os códigos das ações Java antes de publicar o importador.');
} else {
    var largura = 1440;
    var altura = 780;
    var headerAltura = 50;
    var url = '/mge/html5component.mge'
        + '?entryPoint=' + encodeURIComponent(entryPoint)
        + '&nuGdg=' + encodeURIComponent(nuGdg)
        + '&codAcaoPrevalidar=' + encodeURIComponent(codAcaoPrevalidar)
        + '&codAcaoProcessar=' + encodeURIComponent(codAcaoProcessar);

    var seletorModal = 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in';
    var seletorDialog = seletorModal + ' > div';
    var seletorContent = seletorDialog + ' > div';
    var seletorHeader = seletorContent + ' > div.modal-header.ng-scope';
    var seletorBody = seletorContent + ' > div.modal-body';
    var seletorFooter = seletorContent + ' > div.modal-footer';
    var mensagem = ''
        + '<style>'
        + seletorDialog + '{width:' + largura + 'px;max-width:calc(100vw - 40px);margin:14px auto 0;}'
        + seletorContent + '{width:100%!important;height:' + altura + 'px!important;max-height:calc(100vh - 40px)!important;display:flex!important;flex-direction:column!important;overflow:hidden!important;border:1px solid rgba(28,58,109,.20)!important;border-radius:10px!important;box-shadow:0 18px 50px rgba(28,58,109,.22)!important;}'
        + seletorFooter + '{display:none!important;}'
        + seletorHeader + '{height:' + headerAltura + 'px!important;min-height:' + headerAltura + 'px!important;padding:0 14px!important;display:flex!important;align-items:center!important;background:#1c3a6d!important;color:#fff!important;border:0!important;border-bottom:4px solid #00aeb9!important;box-sizing:border-box!important;font-family:Poppins,Inter,"Segoe UI",Arial,sans-serif!important;}'
        + seletorHeader + ' sk-hbox{order:1!important;flex:1 1 auto!important;}'
        + seletorHeader + ' h5{margin:0!important;flex:1 1 auto!important;font-size:0!important;line-height:1!important;color:#fff!important;text-shadow:none!important;}'
        + seletorHeader + ' h5 span{display:none!important;}'
        + seletorHeader + ' h5:before{content:"Importação de fórmulas";display:inline-block;padding-left:11px;border-left:4px solid #00aeb9;color:#fff;font-size:14px;font-weight:600;line-height:22px;letter-spacing:.2px;}'
        + seletorHeader + ' button,' + seletorHeader + ' .close{order:2!important;flex:0 0 32px!important;width:32px!important;height:32px!important;min-height:32px!important;margin:0 0 0 auto!important;padding:0!important;border:0!important;border-radius:50%!important;background:transparent!important;color:#fff!important;opacity:.82!important;text-shadow:none!important;}'
        + seletorHeader + ' button:hover,' + seletorHeader + ' .close:hover{background:rgba(0,174,185,.22)!important;opacity:1!important;}'
        + seletorHeader + ' button span,' + seletorHeader + ' .close span,' + seletorHeader + ' button i,' + seletorHeader + ' .close i{display:inline!important;color:#fff!important;opacity:1!important;font-size:20px!important;line-height:30px!important;}'
        + seletorHeader + ' button *,' + seletorHeader + ' .close *,' + seletorHeader + ' .fa-times{color:#fff!important;-webkit-text-fill-color:#fff!important;opacity:1!important;}'
        + seletorHeader + ' button:before,' + seletorHeader + ' button:after,' + seletorHeader + ' .close:before,' + seletorHeader + ' .close:after{color:#fff!important;-webkit-text-fill-color:#fff!important;}'
        + seletorHeader + ' button svg,' + seletorHeader + ' .close svg,' + seletorHeader + ' button svg *,' + seletorHeader + ' .close svg *{fill:#fff!important;stroke:#fff!important;color:#fff!important;}'
        + seletorBody + '{padding:0!important;flex:1 1 auto!important;overflow:hidden!important;}'
        + seletorBody + ' > div,' + seletorBody + ' iframe{width:100%!important;height:100%!important;display:block!important;border:0!important;padding:0!important;}'
        + '</style>'
        + '<iframe title="' + titulo + '" src="' + url + '"></iframe>';

    contexto.setMensagemRetorno(mensagem);
}
