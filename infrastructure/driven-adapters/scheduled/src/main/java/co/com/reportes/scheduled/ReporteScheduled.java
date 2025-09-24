package co.com.reportes.scheduled;

import co.com.reportes.model.reportelistener.gateways.ReporteDynamoRepository;
import co.com.reportes.model.reportelistener.gateways.SolicitudDinamoRepository;
import co.com.reportes.model.reportelistener.gateways.SqsSenderReportRepository;
import co.com.reportes.model.reportelistener.gateways.UserExternalRepository;
import co.com.reportes.model.reportelistener.model.ReporteSendReportModel;
import co.com.reportes.model.reportelistener.model.SolicitudModel;
import co.com.reportes.scheduled.mapper.UserMapper;
import lombok.AllArgsConstructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@AllArgsConstructor
public class ReporteScheduled {


    private static final Log log = LogFactory.getLog(ReporteScheduled.class);
    private final ReporteDynamoRepository reporteDynamoRepository;
    private final UserExternalRepository userExternalRepository;
    private SolicitudDinamoRepository solicitudDinamoRepository;
    private final SqsSenderReportRepository sqsSenderReportRepository;
    private final UserMapper userMapper;

    //@Scheduled(cron = "0 */1 * * * ?")
    public void syncUsers() {
        userExternalRepository.getUsersAdmins()
                .flatMap(admins -> {

                    return solicitudDinamoRepository.getAll()
                            .map(SolicitudModel::getMount)
                            .collectList()
                            .map(mounts -> {
                                        BigDecimal sumaPrestamos = mounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                                        int count = mounts.size();
                                        return new ReporteSendReportModel(count, sumaPrestamos, admins);
                                    }
                            ).flatMap(sqsSenderReportRepository::senReportDaly);
                }).subscribe();
    }
}
