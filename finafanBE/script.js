import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomIntBetween, randomString } from "https://jslib.k6.io/k6-utils/1.1.0/index.js";

export let options = {
    scenarios: {
        constant_load: {
            executor: 'constant-vus',
            vus: 300,        // 가상 사용자 수
            duration: '10m', // 테스트 지속 시간
        },
    },
    thresholds: {
        http_req_duration: ['p(95)<500'], // 95% 응답이 500ms 이하여야 함
        http_req_failed: ['rate<0.01'],   // 실패율 1% 미만
    },
};

// Nginx를 통한 로드 밸런싱 사용
const baseUrl = 'http://nginx:80';

export default function () {
    // 랜덤 데이터 생성
    const id = randomIntBetween(1, 1000000);
    const name = randomString(10);
    const age = randomIntBetween(1, 99);
    const address = `Random Street ${randomIntBetween(1, 1000)}`;

    // JSON 바디 구성
    const payload = JSON.stringify({
        id: id,
        name: name,
        age: age,
        address: address
    });

    // HTTP 요청 설정
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // GET 요청 테스트
    let getResponse = http.get(`${baseUrl}/api/v1/test`, params);

    check(getResponse, {
        'GET status is 200': (r) => r.status === 200,
        'GET response time < 200ms': (r) => r.timings.duration < 200,
    });

    // 각 요청 사이에 약간의 대기 시간 (더 현실적인 사용자 행동 시뮬레이션)
    sleep(randomIntBetween(1, 3) * 0.1);
}