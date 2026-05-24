package roomescape.controller.fixture;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import roomescape.web.dto.reservation.ReservationModifyRequest;
import roomescape.web.dto.reservation.ReservationRequest;

public final class ReservationRequestFixture {

    private ReservationRequestFixture() {
    }

    public static Stream<Arguments> reserveFailRequestFixture() {
        return Stream.of(
                Arguments.of(
                        new ReservationRequest(null, 1L, 1L),
                        "예약 날짜 정보는 필수 값입니다."
                ),
                Arguments.of(
                        new ReservationRequest(LocalDate.now().minusDays(1), 1L, 1L),
                        "이미 지난 날짜는 예약할 수 없습니다."
                ),
                Arguments.of(
                        new ReservationRequest(LocalDate.now(), null, 1L),
                        "테마 식별자는 필수 값입니다."
                ),
                Arguments.of(
                        new ReservationRequest(LocalDate.now(), 0L, 1L),
                        "테마 식별자는 식별 가능한 양수입니다."
                ),
                Arguments.of(
                        new ReservationRequest(LocalDate.now(), 1L, null),
                        "예약 시간 식별자는 필수 값입니다."
                ),
                Arguments.of(
                        new ReservationRequest(LocalDate.now(), 1L, 0L),
                        "예약 시간 식별자는 식별 가능한 양수입니다."
                )
        );
    }

    public static ReservationRequest reserveSuccessRequestFixture() {
        return new ReservationRequest(LocalDate.now().plusDays(1), 1L, 1L);
    }

    public static Stream<Arguments> modifyFailRequestFixture() {
        return Stream.of(
                Arguments.of(
                        new ReservationModifyRequest(null, 1L),
                        "예약 날짜 정보는 필수 값입니다."
                ),
                Arguments.of(
                        new ReservationModifyRequest(LocalDate.now().minusDays(1), 1L),
                        "이미 지난 날짜는 예약할 수 없습니다."
                ),
                Arguments.of(
                        new ReservationModifyRequest(LocalDate.now(), null),
                        "예약 시간 식별자는 필수 값입니다."
                ),
                Arguments.of(
                        new ReservationModifyRequest(LocalDate.now(), 0L),
                        "예약 시간 식별자는 식별 가능한 양수입니다."
                )
        );
    }

    public static ReservationModifyRequest modifySuccessRequestFixture() {
        return new ReservationModifyRequest(LocalDate.now().plusDays(1), 1L);
    }
}
