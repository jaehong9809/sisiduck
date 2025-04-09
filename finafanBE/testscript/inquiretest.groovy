import static net.grinder.script.Grinder.grinder
import static org.junit.Assert.*
import static org.hamcrest.Matchers.*
import net.grinder.script.GTest
import net.grinder.script.Grinder
import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.ngrinder.http.HTTPRequest
import org.ngrinder.http.HTTPRequestControl
import org.ngrinder.http.HTTPResponse
import org.ngrinder.http.cookie.Cookie
import org.ngrinder.http.cookie.CookieManager
import java.text.SimpleDateFormat
import java.util.concurrent.atomic.AtomicInteger

/**
 * 계좌 생성 API 성능 테스트
 * institutionTransactionUniqueNo: 유니크한 트랜잭션 ID를 생성
 * 포맷: 날짜(YYYYMMDD) + 시간(HHMMSS) + 6자리 증가 카운터
 */
@RunWith(GrinderRunner)
class TestRunner {
    public static GTest test
    public static HTTPRequest request
    public static Map<String, String> headers = [:]
    public static Map<String, Object> params = [:]
    public static List<Cookie> cookies = []
    // 시작 카운터 값 설정
    public static AtomicInteger counter = new AtomicInteger(900000)

    @BeforeProcess
    public static void beforeProcess() {
        HTTPRequestControl.setConnectionTimeout(30000)
        test = new GTest(1, "계좌 생성 API 테스트")
        request = new HTTPRequest()
        // Set header data
        headers.put("Content-Type", "application/json")
        headers.put("Accept", "application/json")
        grinder.logger.info("성능 테스트 프로세스 초기화 완료")
    }

    @BeforeThread
    public void beforeThread() {
        test.record(this, "test")
        grinder.statistics.delayReports = true
        grinder.logger.info("스레드 #{} 초기화 완료", grinder.threadNumber)
    }

    @Before
    public void before() {
        request.setHeaders(headers)
        CookieManager.addCookies(cookies)
        grinder.logger.info("테스트 실행 준비 완료: 헤더 및 쿠키 설정")
    }

    @Test
    public void test() {
        // 현재 날짜와 시간 가져오기
        def today = new Date()
        def dateStr = new SimpleDateFormat("yyyyMMdd").format(today)
        def timeStr = new SimpleDateFormat("HHmmss").format(today)

        // 고유한 거래 번호 생성
        int currentCount = counter.getAndIncrement()
        String sixDigitCounter = String.format("%06d", currentCount % 1000000)
        String uniqueTransactionNo = dateStr + timeStr + sixDigitCounter

        // 요청 JSON 본문 생성
        def jsonBody = """
        {
            "Header": {
                "apiName": "createDemandDepositAccount",
                "institutionCode": "00100",
                "fintechAppNo": "001",
                "apiServiceCode": "createDemandDepositAccount",
                "institutionTransactionUniqueNo": "${uniqueTransactionNo}",
                "apiKey": "b17e5cc40ec64527bda3621785c457b7",
                "userKey": "0c863c0e-d6cc-47f5-9230-2cae3571723c"
            },
            "accountTypeUniqueNo": "001-1-013936a35b3243"
        }
        """

        // 시간 측정 시작
        long startTime = System.currentTimeMillis()

        // POST 요청 실행
        byte[] bodyBytes = jsonBody.getBytes("UTF-8")
        HTTPResponse response = request.POST("https://j12a702.p.ssafy.io/api/v1/demand-deposit/account", bodyBytes)

        // 시간 측정 종료
        long responseTime = System.currentTimeMillis() - startTime

        // 응답 검증 및 로깅
        if (response.statusCode == 200) {
            grinder.logger.info("계좌 생성 성공 - 응답 시간: {}ms, 거래번호: {}", responseTime, uniqueTransactionNo)
            def responseBody = response.getBodyText()
            grinder.logger.debug("응답 내용: {}", responseBody)
            assertThat(response.statusCode, is(200))
        } else {
            grinder.logger.error("계좌 생성 실패 - 상태 코드: {}, 응답: {}, 거래번호: {}",
                    response.statusCode, response.getBodyText(), uniqueTransactionNo)
            if (response.statusCode == 301 || response.statusCode == 302) {
                grinder.logger.warn("리다이렉트 발생. 응답이 정확하지 않을 수 있습니다. 응답 코드: {}", response.statusCode)
            }
        }
    }
}