package br.com.zaya.events;

import java.math.BigDecimal;
import java.util.Collection;

import org.jdom.Element;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.XMLUtils;

import br.com.sankhya.dwf.services.ServiceUtils;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.extensions.flow.ContextoTarefa;
import br.com.sankhya.extensions.flow.TarefaJava;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.mgeserv.model.services.ProspectSP;
import br.com.sankhya.mgeserv.model.services.ProspectSPHome;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.ws.ServiceContext;
import br.com.zaya.models.Endereco;
import br.com.zaya.models.EnderecoProcessor;

public class ConverterProspectTask implements TarefaJava{

    @Override
    public void executar(ContextoTarefa contexto) throws Exception {
        ProspectSP prospectSP = (ProspectSP) ServiceUtils.getStatelessFacade("mge/servico/ejb/session/ProspectSP",
                ProspectSPHome.class);
        ServiceContext ctx = ServiceContext.getCurrent();
        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
        BigDecimal codParc = BigDecimal.ZERO;
        for (int i = 0; i < (contexto.getLinhasFormulario("VGF_PROCOPO")).length; i++) {
            Registro r = contexto.getLinhasFormulario("VGF_PROCOPO")[i];
            BigDecimal nrOportunidade = (BigDecimal) r.getCampo("NUMOP");
            DynamicVO opoVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("Oportunidade",
                    new Object[] { nrOportunidade });
            BigDecimal codpap = opoVO.asDymamicVO("ParceiroProspect").asBigDecimalOrZero("CODPAP");
            PersistentLocalEntity prospectEntity = null;
            prospectEntity = dwfFacade.findEntityByPrimaryKey("ParceiroProspect", new Object[] { codpap });
            DynamicVO papVO = (DynamicVO) prospectEntity.getValueObject();
            if (papVO != null) {
                FinderWrapper finder = new FinderWrapper("Parceiro", "this.CGC_CPF = ?",
                        new Object[] { papVO.asString("CGC_CPF") });
                Collection<DynamicVO> listaParceiros = dwfFacade.findByDynamicFinderAsVO(finder);
                if (!listaParceiros.isEmpty()) {
                    papVO.setProperty("CODPARC",
                            ((DynamicVO) listaParceiros.iterator().next()).asBigDecimalOrZero("CODPARC"));
                    prospectEntity.setValueObject((EntityVO) papVO);
                }
            }
            if (papVO.asBigDecimalOrZero("CODPARC") == null
                    || papVO.asBigDecimalOrZero("CODPARC").equals(BigDecimal.ZERO)) {
                Element reqElem = buildElem(codpap);
                Element requestBody = ctx.getRequestBody();
                Element bodyElement = null;
                requestBody.removeContent();
                requestBody.addContent(reqElem.detach());
                try {
                    prospectSP.convertProspectIntoParceiro(ctx);
                    bodyElement = ctx.getBodyElement();
                    codParc = BigDecimalUtil
                            .valueOf(bodyElement.getChild("resultado").getAttribute("codParc").getValue());
                    papVO.setProperty("CODPARC", codParc);
                    prospectEntity.setValueObject((EntityVO) papVO);
                } catch (Exception e) {
                    throw new Exception("<b>Erro na ConversProspect : </b></br>" + e.getMessage());
                }
            }
        }
    }
    
    private Element buildElem(BigDecimal codPAP) throws Exception {
        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
        Element prospectElem = new Element("parceiro");
        DynamicVO prospectVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("ParceiroProspect", codPAP);
        DynamicVO ufVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("UnidadeFederativa", prospectVO
                .asBigDecimalOrZero("CODUF"));
        XMLUtils.addAttributeElement(prospectElem, "codigoProspect", codPAP);
        Endereco e = new Endereco();
        e.uf = ufVO.asString("UF");
        e.cep = prospectVO.asString("CEP");
        e.nomecid = prospectVO.asString("NOMECID");
        e.nomebai = prospectVO.asString("NOMEBAI");
        e.nomeend = prospectVO.asString("ENDERECO");
        e.numend = prospectVO.asString("NUMEND");
        EnderecoProcessor processor = new EnderecoProcessor();
        processor.cadastrar(e);
        XMLUtils.addAttributeElement(prospectElem, "endereco", e.codend);
        XMLUtils.addAttributeElement(prospectElem, "bairro", e.codbai);
        XMLUtils.addAttributeElement(prospectElem, "cidade", e.codcid);
        return prospectElem;
    }
}
