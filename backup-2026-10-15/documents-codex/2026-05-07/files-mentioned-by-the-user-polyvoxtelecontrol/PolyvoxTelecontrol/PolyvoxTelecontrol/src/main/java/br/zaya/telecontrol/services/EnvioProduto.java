package br.zaya.telecontrol.services;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.zaya.telecontrol.models.ProdutoGet;
import br.zaya.telecontrol.parsers.ParseProduto;
import br.zaya.telecontrol.utils.ConfigTelecontrol;
import br.zaya.telecontrol.utils.LogUtil;
import com.google.gson.Gson;
import com.sankhya.util.TimeUtils;
import java.math.BigDecimal;
import java.util.Collection;

public class EnvioProduto {
	public static void postProduto() throws Exception {
		postProduto("(this.DTALTER > this.DHINTEG OR this.DHINTEG IS NULL) ");
	}

	public static void postProduto(String where) throws Exception {
		LogUtil.SYNC = "Produto";
		JdbcWrapper jdbc = null;
		try {
			EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
			jdbc = dwfEntityFacade.getJdbcWrapper();
			jdbc.openSession();
			FinderWrapper finder = new FinderWrapper("AD_TCLPRO", where, new Object[0]);
			finder.setMaxResults(1000);
			finder.setOrderBy("this.DTALTER");
			Collection<PersistentLocalEntity> colletionPLE = dwfEntityFacade.findByDynamicFinder(finder);
			ConfigTelecontrol serv = new ConfigTelecontrol();
			
			for (PersistentLocalEntity itemPLE : colletionPLE) {
				try {
					DynamicVO produtoVO = (DynamicVO) itemPLE.getValueObject();
					LogUtil.REGISTRO = produtoVO.asBigDecimal("CODPROD").toString();
					String result = null;
					String url = String.valueOf(serv.url) + serv.epProdutos;
                    LogUtil.URL = url;

					if (produtoVO.asBigDecimal("COD_TELECONTROL") == null) {
						String content = ParseProduto.toJson(produtoVO);
						LogUtil.REQUEST = content;
						result = Api.post(url, content);
					} else {
						url = String.valueOf(url) + "/referencia/" + produtoVO.asString("REFERENCIA");
						String content = ParseProduto.toJsonPut(produtoVO);
						LogUtil.REQUEST = content;
						result = Api.put(url, content);
					}
					ProdutoGet prodResp = (ProdutoGet) (new Gson()).fromJson(result, ProdutoGet.class);
					produtoVO.setProperty("COD_TELECONTROL", new BigDecimal(prodResp.produto));
					produtoVO.setProperty("DHINTEG", TimeUtils.getNow());
					itemPLE.setValueObject((EntityVO) produtoVO);
				} catch (Exception var16) {
					LogUtil.saveLog(var16);
					var16.printStackTrace();
				}
			}
		} catch (Exception var17) {
			throw var17;
		} finally {
			if (jdbc != null) {
                jdbc.closeSession();
            }
		}
	}
}
