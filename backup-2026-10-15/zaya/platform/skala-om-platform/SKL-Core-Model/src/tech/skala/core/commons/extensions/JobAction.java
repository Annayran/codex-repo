package tech.skala.core.commons.extensions;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.SPBeanUtils;
import br.com.sankhya.ws.ServiceContext;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class JobAction extends Extension implements ScheduledActionSetup {

	public JobAction(String className) {
		super(className);
	}

	@Override
	public void setupContext(String uid) throws SkalaModelException {
		setupContext(BigDecimalUtil.getBigDecimal(uid));
	}

	@Override
	public void setupContext(BigDecimal userId) throws SkalaModelException {
		if (userId == null)
			throw new IllegalArgumentException("Usuário para execução da ação deve ser informado!");

		String msg;
		ServiceContext sctx = new ServiceContext(null);

		AuthenticationInfo auth = null ;
		if (AuthenticationInfo.getCurrent() == null) {
			DynamicVO usuVO;
			try {
				usuVO = (DynamicVO) EntityFacadeFactory.getDWFFacade()
						.findEntityByPrimaryKeyAsVO(DynamicEntityNames.USUARIO, userId);

				auth = new AuthenticationInfo(usuVO.asString("NOMEUSU"), usuVO.asBigDecimal("CODUSU"),
						usuVO.asBigDecimal("CODGRUPO"), usuVO.asBigDecimal("CODUSU").toString().hashCode());
			} catch (Exception e) {
				msg = String.format("Não foi possível autenticar o usuário <i>uid %s</i>.", userId);
//				msg = CbmModelException.buildErrorMessage(e, msg);
//				throw new CbmModelException(msg,e);
				SkalaModelException.throwMe(MESSAGES_BASENAME, "auth_not_possible", e);
			}
		} else {
			auth = AuthenticationInfo.getCurrent();
		}
		
		if (auth == null)
			throw new SkalaModelException(MESSAGES_BASENAME, "auth_not_possible");
		
		auth.makeCurrent();

		msg = String.format("Usuario logado %s-%s", auth.getUserID(), auth.getName());
		this.getLogger().info(msg);
		
		sctx.setAutentication(auth); //pega o auth atual (injetado pelo job) e vincula ao contexto de serviço
		sctx.makeCurrent();
		try {
			//setup de variaveis de ambiente
			SPBeanUtils.setupContext(sctx);
		} catch (Exception e) {
			msg = String.format("Não foi possível alterar ambiente para usuário <i>uid %s</i> %s.", userId, auth.getName());
//			msg = CbmModelException.buildErrorMessage(e, msg);
//			throw new CbmModelException(msg,e);
			SkalaModelException.throwMe(MESSAGES_BASENAME, "setup_context_not_possible", e);
		}

	}

}
