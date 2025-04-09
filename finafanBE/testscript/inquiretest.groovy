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

@RunWith(GrinderRunner)
class GetAccountTestRunner {
    public static GTest test
    public static HTTPRequest request
    public static Map<String, String> headers = [:]
    public static List<Cookie> cookies = []

    @BeforeProcess
    public static void beforeProcess() {
        HTTPRequestControl.setConnectionTimeout(30000)
        test = new GTest(1, "계좌 조회 API 테스트")
        request = new HTTPRequest()
        headers.put("Accept", "application/json")
        grinder.logger.info("계좌 조회 테스트 초기화 완료")
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
    }

    @Test
    public void test() {
        // 테스트 파라미터 설정
        String userEmail = "kimyejin0044@gmail.com"
        String accountNo = "0018037473008926"

        // URL 생성 (파라미터 포함)
        String url = "https://j12a702.p.ssafy.io/api/v1/demand-deposit/account?userEmail=" +
                URLEncoder.encode(userEmail, "UTF-8") +
                "&accountNo=" + URLEncoder.encode(accountNo, "UTF-8")

        // 시간 측정 시작
        long startTime = System.currentTimeMillis()

        // GET 요청 실행
        HTTPResponse response = request.GET(url)

        // 시간 측정 종료
        long responseTime = System.currentTimeMillis() - startTime

        // 응답 검증 및 로깅
        if (response.statusCode == 200) {
            grinder.logger.info("계좌 조회 성공 - 응답 시간: {}ms", responseTime)
            def responseBody = response.getBodyText()
            grinder.logger.debug("응답 내용: {}", responseBody)
            assertThat(response.statusCode, is(200))
        } else {
            grinder.logger.error("계좌 조회 실패 - 상태 코드: {}, 응답: {}",
                    response.statusCode, response.getBodyText())
        }
    }
}