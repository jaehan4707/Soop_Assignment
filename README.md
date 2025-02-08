# 2025 SOOP Android 사전 과제

```text
Github API를 이용한 레포지토리, 유저 조회 애플리케이션
```

## 🗂️ 폴더 구조
```java
app
├── 📁data
│   ├── 📁di
│   ├── 📁local
│   ├── 📁network
│   │   ├── 📁api
│   │   ├── 📁error
│   │   └── 📁response
│   └── 📁repositoryimpl
├── 📁domain
│   ├── 📁model
│   └── 📁repository
└── 📁ui
│    ├── 📁componenet
│    ├── 📁navigation
│    ├── 📁screen
│    │   ├── 📁detail
│    │   └── 📁home
│    ├── 📁theme
│    └── 📁uti
└─ 📁test 
```

## 🛠주요 기능

|1️⃣ 레포지토리 검색|2️⃣ 레포지토리 상세 정보 조회|3️⃣ 유저 정보 조회|
|:---:|:---:|:---:|
<img src="https://github.com/user-attachments/assets/958bebf4-0c3d-49fb-ba1f-85f63cc70eee" width=200 height=400/>|<img src="https://github.com/user-attachments/assets/a4ef9380-add6-4b0c-b0d6-c300e8c9cf47" width=200 height=400/>|<img src="https://github.com/user-attachments/assets/cc519258-cb8c-40ed-9374-9417fc75408d" width=200 height=400/>


### 1️⃣ 레포지토리 검색
- 사용자가 검색어를 입력하면 관련된 레포지토리 목록이 리스트 형태로 표시됩니다.
- 데이터의 양이 많기 때문에 한 번의 호출로 모든 데이터를 가져오는 것이 어려워, `Paging3` 라이브러리를 활용하여 페이지당 30개의 데이터를 로드하고, 페이지의 마지막 아이템에 도달하면 자동으로 화면에 반영되도록 구현했습니다.
- 리스트의 아이템을 클릭한다면 해당 레포지토리의 상세페이지로 이동합니다.
- 검색의 편의성을 위해, 검색어를 전체 지울 수 있는 x 버튼 기능을 추가했습니다.
- 키보드의 검색 버튼을 누르거나 화면을 클릭하면 포커스가 해제되어 키보드가 화면에서 사라집니다.

### 2️⃣ 레포지토리 상세 정보 조회
- 레포지토리의 상세 정보(레포지토리 이름,토픽,Star 개수,Watchers 수,Fork 수,유저 이미지,유저 이름,설명)를 확인할 수 있습니다.
- `FlowRow`를 활용한 Topic 구현
  - 레포지토리의 Topic은 LazyRow 대신 FlowRow를 사용해 구현했습니다.
  - `LazyRow`를 사용할 경우, 토픽이 많아지면 가로로만 아이템이 배치되며 가로 스크롤을 통해 확인해야 합니다.
  - `FlowRow`는 레이아웃의 가로 공간이 부족할 경우 자동으로 줄 바꿈을 수행하여 아이템을 배치합니다. 이는 유동적인 개수의 아이템을 표시할 때 효율적입니다
- 최대 높이 및 스크롤 처리
  - Topic의 개수가 많아질 경우 높이가 길어질 수 있으므로, 최대 높이를 지정하여 이를 제한했습니다. 최대 높이를 초과하면 세로 스크롤을 통해 나머지 아이템을 확인할 수 있도록 설계했습니다.
- 텍스트 길이에 따른 제한
  - `FlowRow`는 각 아이템 크기가 유동적이므로, 텍스트가 길 경우 레이아웃 크기가 과도하게 커질 가능성이 있습니다. 이를 방지하기 위해 텍스트의 최대 길이를 제한하고, 일정 길이를 초과하면 텍스트를 잘라 표시하도록 구현했습니다.


### 3️⃣ 유저 정보 조회
- 유저의 정보(유저 이미지,이름,팔로워 수,팔로잉 수,언어,레포지토리 수,bio)를 확인할 수 있습니다.
- 레포지토리의 언어가 `nullable`이고, 중복이 발생했기에 `mapNotNull` 과 `Set`을 사용해서 중복을 제거했습니다.

### 4️⃣ 다크 모드 지원
- 시스템 테마에 따라 자동으로 라이트 모드와 다크 모드를 지원하고 있습니다.

## 💪성능 개선

### ⛔️상황
`more` 버튼을 누를 경우 네트워크 호출(사용자 정보 로딩, 사용자 레포지토리 목록 검색)이 발생합니다.   
처음에는 해당 화면에 진입하는 시점에서 모든 네트워크 호출을 처리할지 고민을 했지만, `more` 버튼을 누르지 않는다면 불필요한 비용이 발생했기 때문에 `more 버튼`을 누를 때만 데이터를 로드하도록 설계했습니다.    
그러나 `more` 버튼을 반복적으로 누를 경우 **변경된 데이터가 없더라도**, 추가적인 리컴포지션과 네트워크 호출이 발생하는 문제가 있었습니다.

![Image](https://github.com/user-attachments/assets/6e7a3aa2-559e-410d-8031-898eff8a4577)
네트워크 호출과 같은 비동기 함수가 자주 호출되고, 데이터가 자주 업데이트되면 불필요한 리컴포지션이 유발될 가능성이 있었습니다.
![Image](https://github.com/user-attachments/assets/5334d011-c701-41cf-a2cf-6088915598a9)

### 🔨과제

같은 화면에서 반복적으로 `more` 버튼이 클릭되는 상황을 구분하고, 불필요한 네트워크 호출을 방지하며 리컴포지션을 최소화해야 했습니다.

### 🔑행동

#### 기존 코드
기존 코드는 `more` 버튼이 클릭될 때 마다 `BottomSheet`의 출력 상태를 변경하고 동시에 `getUserInfoAndRepositories(네트워크 호출)`를 실행했습니다.
``` kotlin
var isOpenBottomSheet by remember { mutableStateOf(false) } // BottomSheet 상태
...
UserProfile(
    modifier = Modifier,
    userProfileImage = userProfileImage,
    userName = userName,
    onClickedMore = {
        isOpenBottomSheet = true // BottomSheet 상태 변경
        getUserInfoAndRepositories(it) // 네트워크 호출
    },
)
 
```
동일한 `UserName` 값으로 호출한다면 유의미한 데이터 차이가 없을것이라고 판단하여, 데이터가 이미 로드되었는지를 기준으로 재호출 여부를 결정하도록 수정했습니다.


#### 수정된 코드
```kotlin
DetailScreen(
    //
    isLoaded : Boolean, // 데이터 로드 여부
)
if (isOpenBottomSheet && isLoaded.not()) { // BottomSheet가 열려있고, 데이터가 로드되지 않은 상황 
  LaunchedEffect(userName) { //userName 변경을 감지
    getUserInfoAndRepositories(userName)
  }
}
```

`BottomSheet`에서 유저 정보가 이미 로드되었는지를 나타내는 변수를 사용하여 데이터를 다시 호출할 지 여부를 결정합니다.
만약 화면의 `UserName`이 변경되면, 해당 값을 기준으로 유저 정보와 레포지토리를 다시 가져오는 함수가 실행됩니다.
이를 통해 불필요한 데이터 호출을 방지하고 필요한 경우에만 데이터를 갱신하도록 설계했습니다.

### 🔓결과
![Image](https://github.com/user-attachments/assets/38dd4bb3-8931-49ed-8a51-5f8a97f7104b)
![Image](https://github.com/user-attachments/assets/8cde6b11-c298-4b77-9f85-3059b7ed2fc7)

데이터가 이미 로드되었는지를 기준으로 로직을 구현함으로써, 동일한 데이터를 로드하더라도 네트워크 호출을 최소화할 수 있었습니다. 또한, 데이터 로드 여부를 명확히 관리하여 불필요한 리컴포지션을 최소화할 수 있었습니다. 이를 통해 성능을 최적화하고, 사용자 경험을 더욱 향상시킬 수 있었습니다

## ‼️에러 처리 세분화

### ⛔️상황

![Image](https://github.com/user-attachments/assets/1e84bf60-d184-41aa-9ccc-a562641da7ae)

기존에는 API 호출 실패 시 반환된 메시지를 그대로 Toast로 출력했습니다. 하지만 이러한 방식으로는 개발자인 저초자도 어떤 에러인지 판단하기 어려웠습니다.
따라서 [github api에 에러 문서](https://docs.github.com/ko/enterprise-server@3.10/rest/using-the-rest-api/troubleshooting-the-rest-api?apiVersion=2022-11-28)를 참고하여 에러의 원인을 파악하고, 에러 처리를 보다 세분화하려고 시도했습니다.
특히, Github API는 요청 제한과 같은 특수한 상황에서 발생하는 에러를 명확히 이해하기 어려웠습니다. Toast 메시지와 HTTP 로그만으로 문제의 원인을 파악하기 힘든 상황이었습니다.

### 🔨과제
에러의 원인을 명확히 파악하고, 사용자와 개발자 모두가 이해하기 쉬운 방식으로 에러를 처리해야 했습니다. 이를 위해 Github API의 에러 문서를 참고하여 에러를 세분화하고, 상황에 맞는 적절한 메시지를 제공하는 로직을 구현해야 했습니다.

### 🔑행동

기존의 에러 코드는 다음과 같습니다.
```kotlin
sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    sealed class Error(val errorCode: Int? = null, val errorMessage: String = "") :
        ApiResponse<Nothing>() {
        data class ServerError(val code: Int, val message: String) : Error(code, message)
        data class NetworkError(val message: String) : Error(errorMessage = message)
        data class UnknownError(val message: String) : Error(errorMessage = message)
    }
}

suspend fun <T> safeApiCall(
  apiCall: suspend () -> Response<T>,
  default: T,
): ApiResponse<T> {
  return runCatching {
    val response = apiCall()
    if (response.isSuccessful) {
      ApiResponse.Success(data = response.body() ?: default)
    } else {
      val errorMessage = response.errorBody()?.string() ?: "Unknown error"
      if (errorMessage == "Unknown error") {
        ApiResponse.Error.UnknownError(errorMessage)
      } else {
        ApiResponse.Error.ServerError(response.code(), errorMessage)
      }
    }
  }.getOrElse {
    ApiResponse.Error.NetworkError(it.message ?: "Network error")
  }
}

```
기존의 에러 클래스는 `Server`, `Network`, `Unknown`와 같이 추상적인 개념이었습니다.
에러 메시지도 정해지지 않고, 서버에서 받은 메시지 그대로 처리했습니다.

이를 개선하기 위해 우선 에러 메시지를 상수화했습니다.
```kotlin
object ErrorMessages {
    const val SERVER_ERROR_MESSAGE = "서버에서 문제가 발생했습니다. \n다시 시도해주세요."
    const val FORBIDDEN_ERROR_MESSAGE = "접근이 금지되었습니다. \n잠시 후 다시 시도해주세요."
    const val TOO_MANY_REQUESTS_ERROR_MESSAGE = "요청이 너무 많습니다. \n 잠시 후 다시 시도해주세요."
    const val UNKNOWN_ERROR_MESSAGE = "알 수 없는 오류가 발생했습니다."
}
```
- 에러 메시지를 상수로 정의 해 코드의 중복을 줄이고, 메시지를 일관되게 관리할 수 있도록 했습니다.
- 각 상황에 맞는 메시지를 제공하여 사용자 경험을 개선하고자 했습니다.

#### 에러 타입 세분화
```kotlin
data class ServerError(
        val code: Int,
        val message: String = ErrorMessages.SERVER_ERROR_MESSAGE
    ) : Error(code, message)

data class ForbiddenError(val message: String = ErrorMessages.FORBIDDEN_ERROR_MESSAGE) :
    Error(errorCode = 403, errorMessage = message)

data class TooManyRequestError(val message: String = ErrorMessages.TOO_MANY_REQUESTS_ERROR_MESSAGE) :
    Error(errorCode = 429, errorMessage = message)

data class UnknownError(val message: String = ErrorMessages.UNKNOWN_ERROR_MESSAGE) :
    Error(errorMessage = message)
```
- `ServerError`, `ForbiddenError`, `TooManyRequestError`, `UnknownError` 등 에러 타입을 구체적으로 분류했습니다.
- 각 에러 타입은 상태 코드와 기본 메시지를 포함하도록 해 에러 상황을 명확하게 구분할 수 있도록 했습니다.

#### 메서드를 통한 에러 생성
```kotlin
companion object {
    fun fromCode(code: Int, message: String = ""): Error {
        return when (code) {
            403 -> ForbiddenError()
            429 -> TooManyRequestError()
            else -> ServerError(code, message)
        }
    }
}
```
- HTTP 상태 코드를 기반으로 적절한 에러 객체를 생성하는 `fromCode` 메서드를 추가했습니다.
- 이를 통해 서버에서 반환된 상태 코드에 따라 적합한 에러 타입을 동적으로 생성할 수 있었습니다.

### 🔓결과
![Image](https://github.com/user-attachments/assets/2693d82f-14eb-4a0e-9367-da69d080e76c)

그 결과, 서버 에러, 네트워크 에러, 요청 제한 등을 구체적으로 분류해 상황별 처리가 가능해졌습니다.
모든 에러 처리가 fromCode 메서드로 통합되어 중복 코드를 제거했으며, 새로운 에러 타입의 추가 시에도 쉽게 확장할 수 있는 구조가 되었습니다. 


## 📚기술 스택

##### Architecture
- ![MVVM](https://img.shields.io/badge/MVVM-4CAF50?style=flat-square&logo=android&logoColor=white)
##### Android
- ![Compose](https://img.shields.io/badge/Compose-4285F4?style=flat-square&logo=Jetpack-Compose&logoColor=white) ![Jetpack Navigation](https://img.shields.io/badge/Jetpack%20Navigation-3F51B5?style=flat-square&logo=android&logoColor=white) ![Paging](https://img.shields.io/badge/Paging3-3F5A55?style=flat-square&logo=android&logoColor=white)  ![Glide](https://img.shields.io/badge/Glide-AF51B5?style=flat-square&logo=android&logoColor=white)
##### asynchronous
- ![Coroutine](https://img.shields.io/badge/Coroutine-0095D5?style=flat-square&logo=kotlin&logoColor=white)
##### Network
- ![Retrofit](https://img.shields.io/badge/Retrofit-FF6B6B?style=flat-square&logo=android&logoColor=white) ![OkHttp](https://img.shields.io/badge/OkHttp-4CAF50?style=flat-square&logo=square&logoColor=white)
##### DI
- ![Hilt](https://img.shields.io/badge/Hilt-FF6F00?style=flat-square&logo=android&logoColor=white)
##### Test
- ![Junit](https://img.shields.io/badge/Junit4-25A162?style=flat-square&logo=junit5&logoColor=white)


### ⚙️개발 환경

- language : Kotlin
- minSdk : 26
- targetSdk : 34
- compileSdk : 34

