package br.zaya.telecontrol.serie.application;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.zaya.telecontrol.serie.domain.SeriePendente;
import br.zaya.telecontrol.serie.infrastructure.mapper.ParseSerie;
import br.zaya.telecontrol.serie.infrastructure.repository.SerieRepository;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.infrastructure.http.Api;
import br.zaya.telecontrol.shared.utils.LogUtil;
import java.util.List;

public class EnviaSerieService {
    public static void postSerie() throws Exception {
        LogUtil.SYNC = "Serie";
        JdbcWrapper jdbc = null;

        try {
            EntityFacade facade = EntityFacadeFactory.getDWFFacade();
            jdbc = facade.getJdbcWrapper();
            jdbc.openSession();

            ConfigTelecontrol config = new ConfigTelecontrol();
            String url = String.valueOf(config.url) + config.epSerie;
            LogUtil.URL = url;

            SerieRepository repository = new SerieRepository();
            repository.sincronizarViewParaLog(jdbc);
            List<SeriePendente> pendentes = repository.buscarPendentes(jdbc);

            for (SeriePendente pendente : pendentes) {
                LogUtil.REGISTRO = pendente.getSerie();
                if (pendente.getSerie() == null || pendente.getSerie().trim().isEmpty()) {
                    continue;
                }

                String content = ParseSerie.toJson(pendente);
                LogUtil.REQUEST = content;
                try {
                    String response = Api.post(url, content);
                    LogUtil.RESPONSE = response;
                    repository.marcarProcessado(jdbc, pendente);
                } catch (Exception e) {
                    if (isDuplicidadeSerieTelecontrol(e)) {
                        LogUtil.RESPONSE = e.getMessage();
                        repository.marcarProcessado(jdbc, pendente);
                        continue;
                    }
                    LogUtil.saveLog(e);
                }
            }
        } catch (Exception e) {
            LogUtil.saveLog(e);
            throw e;
        } finally {
            if (jdbc != null) {
                jdbc.closeSession();
            }
        }
    }

    private static boolean isDuplicidadeSerieTelecontrol(Exception e) {
        String message = e == null ? null : e.getMessage();
        return message != null
                && (message.contains("UniqueConstraintViolationException")
                || message.contains("tbl_numero_serie_fabrica_produto_serie")
                || message.contains("duplicate key value violates unique constraint"));
    }
}
