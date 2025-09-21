package co.com.reportes.usecase.reporte;

import co.com.reportes.model.reportelistener.enums.LoanType;
import co.com.reportes.model.reportelistener.enums.Status;
import co.com.reportes.model.reportelistener.gateways.SolicitudDinamoRepository;
import co.com.reportes.model.reportelistener.model.SolicitudModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class SolicitudUseCaseTest {

    @Mock
    private SolicitudDinamoRepository solicitudDinamoRepository;

    @InjectMocks
    private SolicitudUseCase solicitudUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addSolicitud_Success() {
        SolicitudModel solicitud = SolicitudModel.builder()
                .id(1L)
                .mount(new BigDecimal("1000"))
                .userDocumentNumber("123456789")
                .termInMonths(12)
                .loanType(LoanType.PERSONAL)
                .status(Status.APPROVED)
                .interestRate(5.0)
                .validationAutomatic(true)
                .build();

        when(solicitudDinamoRepository.addSolicitud(solicitud))
                .thenReturn(Mono.just(solicitud));

        StepVerifier.create(solicitudUseCase.addSolicitud(solicitud))
                .expectNext(solicitud)
                .verifyComplete();

        verify(solicitudDinamoRepository).addSolicitud(solicitud);
    }

    @Test
    void addSolicitud_Error() {
        SolicitudModel solicitud = SolicitudModel.builder()
                .id(1L)
                .mount(new BigDecimal("1000"))
                .userDocumentNumber("123456789")
                .termInMonths(12)
                .loanType(LoanType.PERSONAL)
                .status(Status.APPROVED)
                .interestRate(5.0)
                .validationAutomatic(true)
                .build();

        when(solicitudDinamoRepository.addSolicitud(solicitud))
                .thenReturn(Mono.error(new RuntimeException("DB Error")));

        StepVerifier.create(solicitudUseCase.addSolicitud(solicitud))
                .expectError(RuntimeException.class)
                .verify();

        verify(solicitudDinamoRepository).addSolicitud(solicitud);
    }

    @Test
    void getAllApproved_CombinesStreams() {
        SolicitudModel existing = SolicitudModel.builder()
                .id(1L)
                .mount(new BigDecimal("1000"))
                .userDocumentNumber("123456789")
                .termInMonths(12)
                .loanType(LoanType.PERSONAL)
                .status(Status.APPROVED)
                .interestRate(5.0)
                .validationAutomatic(true)
                .build();

        SolicitudModel newSolicitud = SolicitudModel.builder()
                .id(2L)
                .mount(new BigDecimal("2000"))
                .userDocumentNumber("123456789")
                .termInMonths(12)
                .loanType(LoanType.PERSONAL)
                .status(Status.APPROVED)
                .interestRate(5.0)
                .validationAutomatic(true)
                .build();

        when(solicitudDinamoRepository.getAll())
                .thenReturn(Flux.just(existing));

        when(solicitudDinamoRepository.addSolicitud(newSolicitud))
                .thenReturn(Mono.just(newSolicitud));

        StepVerifier.create(solicitudUseCase.getAllApproved())
                .expectNext(existing)
                .then(() -> solicitudUseCase.addSolicitud(newSolicitud).subscribe())
                .expectNext(newSolicitud)
                .thenCancel()
                .verify();

        verify(solicitudDinamoRepository).getAll();
        verify(solicitudDinamoRepository).addSolicitud(newSolicitud);
    }
}