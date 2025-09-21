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
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class ReporteDynamoUseCaseTest {

    @Mock
    private SolicitudDinamoRepository solicitudDinamoRepository;

    @InjectMocks
    private ReporteDynamoUseCase reporteDynamoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLoanSummary_WithInitialData() {
        SolicitudModel solicitud1 = SolicitudModel.builder()
                .id(13L)
                .mount(new BigDecimal("1000"))
                .userDocumentNumber("123456789")
                .termInMonths(12)
                .loanType(LoanType.PERSONAL)
                .status(Status.APPROVED)
                .interestRate(5.0)
                .validationAutomatic(true)
                .build();

        SolicitudModel solicitud2 = SolicitudModel.builder()
                .id(12L)
                .mount(new BigDecimal("2000"))
                .userDocumentNumber("123456789")
                .termInMonths(12)
                .loanType(LoanType.PERSONAL)
                .status(Status.APPROVED)
                .interestRate(5.0)
                .validationAutomatic(true)
                .build();

        when(solicitudDinamoRepository.getAll())
                .thenReturn(Flux.just(solicitud1, solicitud2));

        StepVerifier.create(reporteDynamoUseCase.getLoanSummary())
                .expectNextMatches(reporte ->
                        reporte.getTotalPrestamos() == 2 &&
                                reporte.getValorTotalPrestamos().compareTo(new BigDecimal("3000")) == 0)
                .thenCancel()
                .verify();

        verify(solicitudDinamoRepository).getAll();
    }

    @Test
    void getLoanSummary_EmptyRepository() {
        when(solicitudDinamoRepository.getAll()).thenReturn(Flux.empty());

        StepVerifier.create(reporteDynamoUseCase.getLoanSummary())
                .expectNextMatches(reporte ->
                        reporte.getTotalPrestamos() == 0 &&
                                reporte.getValorTotalPrestamos().compareTo(BigDecimal.ZERO) == 0)
                .thenCancel()
                .verify();

        verify(solicitudDinamoRepository).getAll();
    }

    @Test
    void getLoanSummary_WithSingleLoan() {
        SolicitudModel solicitud = SolicitudModel.builder()
                .id(13L)
                .mount(new BigDecimal("1500"))
                .userDocumentNumber("987654321")
                .termInMonths(12)
                .loanType(LoanType.PERSONAL)
                .status(Status.APPROVED)
                .interestRate(5.0)
                .validationAutomatic(true)
                .build();

        when(solicitudDinamoRepository.getAll())
                .thenReturn(Flux.just(solicitud));

        StepVerifier.create(reporteDynamoUseCase.getLoanSummary())
                .expectNextMatches(reporte ->
                        reporte.getTotalPrestamos() == 1 &&
                                reporte.getValorTotalPrestamos().compareTo(new BigDecimal("1500")) == 0)
                .thenCancel()
                .verify();

        verify(solicitudDinamoRepository).getAll();
    }

    @Test
    void getLoanSummary_WithRepositoryError() {
        when(solicitudDinamoRepository.getAll())
                .thenReturn(Flux.error(new RuntimeException("Database error")));

        StepVerifier.create(reporteDynamoUseCase.getLoanSummary())
                .expectError(RuntimeException.class)
                .verify();

        verify(solicitudDinamoRepository).getAll();
    }




}