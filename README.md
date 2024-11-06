# SejongAuth

SejongAuth는 세종대학교 통합 로그인 페이지에 접근하여 사용자 인증과 프로필 정보를 가져오는 Java 기반의 패키지입니다. `Sj`를 통해 세종대학교 포털에 간편하게 로그인하고 사용자의 학적 정보를 조회할 수 있습니다.

## 주요 기능

- **로그인 기능**: `LoginReq`를 사용하여 세종대학교 포털에 로그인하고 `JSESSIONID`를 획득합니다.
- **프로필 조회 기능**: 로그인 성공 시 `ProfileService`를 통해 사용자의 학적 정보를 가져옵니다.

---

## 설치 방법

gradle
```properties
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
dependencies {
	        implementation 'com.github.urinaner:sejong-auth:Tag'
	}
```
maven
```bash
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
</repositories>

<dependency>
	    <groupId>com.github.urinaner</groupId>
	    <artifactId>sejong-auth</artifactId>
	    <version>Tag</version>
</dependency>
```


---

## 프로젝트 구조

```
SejongAuth/
├── src/
│   ├── main/
│   │   ├── java/org/yj/sejongauth/controller/Sj.java
│   │   ├── java/org/yj/sejongauth/domain/AuthService.java
│   │   ├── java/org/yj/sejongauth/domain/LoginReq.java
│   │   ├── java/org/yj/sejongauth/domain/ProfileRes.java
│   │   └── java/org/yj/sejongauth/domain/ProfileService.java
│   └── test/
│       └── java/org/yj/sejongauth/controller/AuthControllerTest.java
└── build.gradle
```

---

## 사용법


### 1. 로그인 및 프로필 조회

`Sj.login()` 메서드를 사용하여 로그인 및 프로필 정보를 조회할 수 있습니다.

```java
SjProfile profile = Sj.login("학번", "비밀번호");
System.out.println("User profile: " + profile);
```

---

## 예제

아래는 `Sj.login` 메서드를 호출하여 간단히 로그인과 프로필 조회를 수행하는 예제입니다.

```java

public class Main {
    public static void main(String[] args) {

        try {
            ProfileRes profile = Sj.login("학번", "비밀번호");
            System.out.println("Login successful. User profile: " + profile);
        } catch (RuntimeException e) {
            System.err.println("Login failed: " + e.getMessage());
        }
    }
}
```

---

## 테스트

`AuthService` 클래스에서 로그인에 대한 테스트를 실행할 수 있습니다.


---

## 주의사항

- **네트워크 연결**: 이 프로그램은 네트워크 연결이 필요합니다.
- **예외 처리**: 네트워크 오류, 로그인 실패 등의 상황에서 `RuntimeException`이 발생할 수 있습니다.
