# ✅ 단 두 줄의 코드로 메일 및 대학 인증 서비스 사용하기 (2월13일 출시예정)

## 🐣 초보자를 위한 UnivCert의 라이브러리 배포 (JitPack)

자세한 설명은 [해당 사이트](https://univcert.com/)에서 확인하실 수 있습니다.

💡 build.gradle에 해당 코드 두 줄 추가

```
repositories{
  ...
  maven{url 'https://jitpack.io'}
}

dependencies{
  ...
  implementation 'com.github.in-seo:UnivCert-JitPack:50c23ec9'
  ...
}
```

✉ 이용자 메일 인증 시작 (인증코드 발송)

**`UnivCert.certify("key", "email", "univName", univ_check(bool));`**

- -> 하단 json 형태로 자동 변환 및 http 전송 POST([univcert.com/api/v1/certify](http://univcert.com/api/v1/certify))

```
{
	“key” : “부여받은 API KEY”,
	"email” : “abc@mail.hongik.ac.kr”,
	“univName” : “홍익대학교”,
	“univ_check” : true
	  (true라면 해당 대학 재학 여부, false라면 메일 소유자 인증만)
}
```

✅ 이용자 메일에 발송된 코드를 전달 받아 인증 받기

**`UnivCert.certifyCode("key", "email", "univName", 인증코드(int));`**

- -> 하단 형태로 자동 변환 및 http 전송 POST([univcert.com/api/v1/certifycode](http://univcert.com/api/v1/certifycode))

```
{
	“key” : “부여받은 API KEY”
	“email” : "abc@mail.hongik.ac.kr”,
	“univName” : “홍익대학교”,
	“code” : 3816
}
```

🆗 응답 성공 시 인증 끝 !

---

> 이외 기능
> 

📂 인증된 이메일인지 확인 기능

`UnivCert.status("key","email");`

- -> 하단 json 형태로 자동 변환 및 http 전송 POST([univcert.com/api/v1/status](http://univcert.com/api/v1/status))

```
{
	“key” : “부여받은 API KEY”,
	"email” : “abc@mail.hongik.ac.kr”
}
```

📜 해당 API 키로 인증된 유저 리스트 출력

`UnivCert.list("key");`
--> 하단 json 형태로 자동 변환 및 http 전송 POST([univcert.com/api/v1/certifiedlist](http://univcert.com/api/v1/certifiedlist))

```
{
	“key” : “부여받은 API KEY”
}
```

👼 상단 certify, certifycode의 메서드로 대학 인증 절차를 간편하게 끝낼 수 있습니다.
