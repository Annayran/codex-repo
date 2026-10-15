package org.projects.NotaMovInterna.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ItemNotaOS {

    private BigDecimal NUMITEM;
    private BigDecimal NUMOS;
    private BigDecimal CODUSURESP;
    private BigDecimal CODSERV;
    private Timestamp DATA;
    private BigDecimal HRINICIAL;
    private BigDecimal HRFINAL;
    private BigDecimal INTERVALO;
    private Timestamp DHAGENDA;
    private Timestamp DHALTER;
    private BigDecimal CODUSUALTER;
    private BigDecimal VALORSERVICO;
    private BigDecimal CODFUNC;
    private String MOTIVOINTERVALO;
    private BigDecimal CODEMP;
    private BigDecimal HRINICIAL2;
    private BigDecimal HRFINAL2;
    private BigDecimal NUAPONT;
    private BigDecimal CODEMPCAB;
    private BigDecimal AD_NROPEDVENDA;
    private Timestamp DHAGENDAFIM;
    private String AGENDAR;
    private BigDecimal NUEVENTO;
    private BigDecimal CODTIPOTEMPO;
    private BigDecimal IDINTEGRACAO;
    private BigDecimal QTDNEG;
    private BigDecimal HORAAPONTAMENTOS;
    private BigDecimal HORASVENDIDAS;
    private String OBSERVACAOSERV;
    private BigDecimal TOTHORASAPONTDEC;
    private String TOTHORASAPONT_TEXTO;
    private BigDecimal HORASVENDIDASDEC;
    private String HORASVENDIDASTEXTO;
    private BigDecimal HORA_TEXTO;
    private BigDecimal HSTRABALHADAS;
    private BigDecimal NUNOTA;
    private String DESCONSIDERAGERPED;
    private BigDecimal CODPARCRESP;
    private BigDecimal CODPARCRESP2;
    private String TIPO;
    private BigDecimal CODPARCRESP3;
    private String CODVOL;

    public BigDecimal getNUMITEM() {
        return NUMITEM;
    }

    public void setNUMITEM(BigDecimal NUMITEM) {
        this.NUMITEM = NUMITEM;
    }

    public BigDecimal getNUMOS() {
        return NUMOS;
    }

    public void setNUMOS(BigDecimal NUMOS) {
        this.NUMOS = NUMOS;
    }

    public BigDecimal getCODUSURESP() {
        return CODUSURESP;
    }

    public void setCODUSURESP(BigDecimal CODUSURESP) {
        this.CODUSURESP = CODUSURESP;
    }

    public BigDecimal getCODSERV() {
        return CODSERV;
    }

    public void setCODSERV(BigDecimal CODSERV) {
        this.CODSERV = CODSERV;
    }

    public Timestamp getDATA() {
        return DATA;
    }

    public void setDATA(Timestamp DATA) {
        this.DATA = DATA;
    }

    public BigDecimal getHRINICIAL() {
        return HRINICIAL;
    }

    public void setHRINICIAL(BigDecimal HRINICIAL) {
        this.HRINICIAL = HRINICIAL;
    }

    public BigDecimal getHRFINAL() {
        return HRFINAL;
    }

    public void setHRFINAL(BigDecimal HRFINAL) {
        this.HRFINAL = HRFINAL;
    }

    public BigDecimal getINTERVALO() {
        return INTERVALO;
    }

    public void setINTERVALO(BigDecimal INTERVALO) {
        this.INTERVALO = INTERVALO;
    }

    public Timestamp getDHAGENDA() {
        return DHAGENDA;
    }

    public void setDHAGENDA(Timestamp DHAGENDA) {
        this.DHAGENDA = DHAGENDA;
    }

    public Timestamp getDHALTER() {
        return DHALTER;
    }

    public void setDHALTER(Timestamp DHALTER) {
        this.DHALTER = DHALTER;
    }

    public BigDecimal getCODUSUALTER() {
        return CODUSUALTER;
    }

    public void setCODUSUALTER(BigDecimal CODUSUALTER) {
        this.CODUSUALTER = CODUSUALTER;
    }

    public BigDecimal getVALORSERVICO() {
        return VALORSERVICO;
    }

    public void setVALORSERVICO(BigDecimal VALORSERVICO) {
        this.VALORSERVICO = VALORSERVICO;
    }

    public BigDecimal getCODFUNC() {
        return CODFUNC;
    }

    public void setCODFUNC(BigDecimal CODFUNC) {
        this.CODFUNC = CODFUNC;
    }

    public String getMOTIVOINTERVALO() {
        return MOTIVOINTERVALO;
    }

    public void setMOTIVOINTERVALO(String MOTIVOINTERVALO) {
        this.MOTIVOINTERVALO = MOTIVOINTERVALO;
    }

    public BigDecimal getCODEMP() {
        return CODEMP;
    }

    public void setCODEMP(BigDecimal CODEMP) {
        this.CODEMP = CODEMP;
    }

    public BigDecimal getHRINICIAL2() {
        return HRINICIAL2;
    }

    public void setHRINICIAL2(BigDecimal HRINICIAL2) {
        this.HRINICIAL2 = HRINICIAL2;
    }

    public BigDecimal getHRFINAL2() {
        return HRFINAL2;
    }

    public void setHRFINAL2(BigDecimal HRFINAL2) {
        this.HRFINAL2 = HRFINAL2;
    }

    public BigDecimal getNUAPONT() {
        return NUAPONT;
    }

    public void setNUAPONT(BigDecimal NUAPONT) {
        this.NUAPONT = NUAPONT;
    }

    public BigDecimal getCODEMPCAB() {
        return CODEMPCAB;
    }

    public void setCODEMPCAB(BigDecimal CODEMPCAB) {
        this.CODEMPCAB = CODEMPCAB;
    }

    public BigDecimal getAD_NROPEDVENDA() {
        return AD_NROPEDVENDA;
    }

    public void setAD_NROPEDVENDA(BigDecimal AD_NROPEDVENDA) {
        this.AD_NROPEDVENDA = AD_NROPEDVENDA;
    }

    public Timestamp getDHAGENDAFIM() {
        return DHAGENDAFIM;
    }

    public void setDHAGENDAFIM(Timestamp DHAGENDAFIM) {
        this.DHAGENDAFIM = DHAGENDAFIM;
    }

    public String getAGENDAR() {
        return AGENDAR;
    }

    public void setAGENDAR(String AGENDAR) {
        this.AGENDAR = AGENDAR;
    }

    public BigDecimal getNUEVENTO() {
        return NUEVENTO;
    }

    public void setNUEVENTO(BigDecimal NUEVENTO) {
        this.NUEVENTO = NUEVENTO;
    }

    public BigDecimal getCODTIPOTEMPO() {
        return CODTIPOTEMPO;
    }

    public void setCODTIPOTEMPO(BigDecimal CODTIPOTEMPO) {
        this.CODTIPOTEMPO = CODTIPOTEMPO;
    }

    public BigDecimal getIDINTEGRACAO() {
        return IDINTEGRACAO;
    }

    public void setIDINTEGRACAO(BigDecimal IDINTEGRACAO) {
        this.IDINTEGRACAO = IDINTEGRACAO;
    }

    public BigDecimal getQTDNEG() {
        return QTDNEG;
    }

    public void setQTDNEG(BigDecimal QTDNEG) {
        this.QTDNEG = QTDNEG;
    }

    public BigDecimal getHORAAPONTAMENTOS() {
        return HORAAPONTAMENTOS;
    }

    public void setHORAAPONTAMENTOS(BigDecimal HORAAPONTAMENTOS) {
        this.HORAAPONTAMENTOS = HORAAPONTAMENTOS;
    }

    public BigDecimal getHORASVENDIDAS() {
        return HORASVENDIDAS;
    }

    public void setHORASVENDIDAS(BigDecimal HORASVENDIDAS) {
        this.HORASVENDIDAS = HORASVENDIDAS;
    }

    public String getOBSERVACAOSERV() {
        return OBSERVACAOSERV;
    }

    public void setOBSERVACAOSERV(String OBSERVACAOSERV) {
        this.OBSERVACAOSERV = OBSERVACAOSERV;
    }

    public BigDecimal getTOTHORASAPONTDEC() {
        return TOTHORASAPONTDEC;
    }

    public void setTOTHORASAPONTDEC(BigDecimal TOTHORASAPONTDEC) {
        this.TOTHORASAPONTDEC = TOTHORASAPONTDEC;
    }

    public String getTOTHORASAPONT_TEXTO() {
        return TOTHORASAPONT_TEXTO;
    }

    public void setTOTHORASAPONT_TEXTO(String TOTHORASAPONT_TEXTO) {
        this.TOTHORASAPONT_TEXTO = TOTHORASAPONT_TEXTO;
    }

    public BigDecimal getHORASVENDIDASDEC() {
        return HORASVENDIDASDEC;
    }

    public void setHORASVENDIDASDEC(BigDecimal HORASVENDIDASDEC) {
        this.HORASVENDIDASDEC = HORASVENDIDASDEC;
    }

    public String getHORASVENDIDASTEXTO() {
        return HORASVENDIDASTEXTO;
    }

    public void setHORASVENDIDASTEXTO(String HORASVENDIDASTEXTO) {
        this.HORASVENDIDASTEXTO = HORASVENDIDASTEXTO;
    }

    public BigDecimal getHORA_TEXTO() {
        return HORA_TEXTO;
    }

    public void setHORA_TEXTO(BigDecimal HORA_TEXTO) {
        this.HORA_TEXTO = HORA_TEXTO;
    }

    public BigDecimal getHSTRABALHADAS() {
        return HSTRABALHADAS;
    }

    public void setHSTRABALHADAS(BigDecimal HSTRABALHADAS) {
        this.HSTRABALHADAS = HSTRABALHADAS;
    }

    public BigDecimal getNUNOTA() {
        return NUNOTA;
    }

    public void setNUNOTA(BigDecimal NUNOTA) {
        this.NUNOTA = NUNOTA;
    }

    public String getDESCONSIDERAGERPED() {
        return DESCONSIDERAGERPED;
    }

    public void setDESCONSIDERAGERPED(String DESCONSIDERAGERPED) {
        this.DESCONSIDERAGERPED = DESCONSIDERAGERPED;
    }

    public BigDecimal getCODPARCRESP() {
        return CODPARCRESP;
    }

    public void setCODPARCRESP(BigDecimal CODPARCRESP) {
        this.CODPARCRESP = CODPARCRESP;
    }

    public BigDecimal getCODPARCRESP2() {
        return CODPARCRESP2;
    }

    public void setCODPARCRESP2(BigDecimal CODPARCRESP2) {
        this.CODPARCRESP2 = CODPARCRESP2;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public BigDecimal getCODPARCRESP3() {
        return CODPARCRESP3;
    }

    public void setCODPARCRESP3(BigDecimal CODPARCRESP3) {
        this.CODPARCRESP3 = CODPARCRESP3;
    }

    public String getCODVOL() {
        return CODVOL;
    }

    public void setCODVOL(String CODVOL) {
        this.CODVOL = CODVOL;
    }
}
