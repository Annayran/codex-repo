package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;

/** Authoritative identity/header; monetary base and competence are mapped separately. */
public final class DocumentoOrigem {
    private final OrigemCanonica origem;
    private final int codEmp;
    private final long codNat;
    private final Long codParc;
    private final Long codTipOper;
    public DocumentoOrigem(OrigemCanonica origem,int codEmp,long codNat,Long codParc,Long codTipOper){
        this.origem=origem;this.codEmp=codEmp;this.codNat=codNat;this.codParc=codParc;this.codTipOper=codTipOper;
    }
    public OrigemCanonica getOrigem(){return origem;}
    public int getCodEmp(){return codEmp;}
    public long getCodNat(){return codNat;}
    public Long getCodParc(){return codParc;}
    public Long getCodTipOper(){return codTipOper;}
}
