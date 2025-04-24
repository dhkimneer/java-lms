# 학습 관리 시스템(Learning Management System)

## [1단계] - 레거시 코드 리팩터링
1. 질문 삭제하기 요구사항
   1. 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경
   2. 삭제 가능한 경우
      - 로그인 사용자와 질문한 사람이 같은 경우
      - 답변이 없는 경우
      - 질문자와 답변 글의 모든 답변자가 같은 경우
   3. 질문을 삭제할 때 답변도 삭제해야 하며, 답변 삭제도 삭제 상태 변경으로 진행
   4. 질문자와 답변자가 다른 경우 답변 삭제 불가
   5. 질문, 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.

2. 리팩터링 요구사항
   1. QnAService의 deleteQuestion 함수에 '단위 테스트 가능 코드'를 도메인 모델 객체에 구현
      - 해당 리팩터링 진행 시 TDD로 구현
      - 도메인 모델로 로직이 이동한 후에도 모든 테스트가 통과하여야 한다.

3. 힌트
   1. 객체 상태 데이터를 꺼내지(get) 말고 메시지를 보낼 것
   2. Question list를 일급 컬렉션으로 구현
   3. 3개 이상의 인스턴스 변수를 가진 클래스를 사용하지 않는다.
   4. 도메인 모델에 setter 메서드를 추가하지 않는다.

4. 리팩터링 진행
   1. questionRepository에서 가져오는 부분을 함수화
   2. '질문 삭제 권한' 관련 함수화
   3. 질문 delete 처리 및 history 추가
   4. 'question'에 해당하는 answer들을 가져와서 delete 처리
      1. deleteHistoryService에 add 요청
      2. Service 계층에서는 domain 객체에게 실질적 로직 실행 요청
   5. deleteHistoryService에 일급 컬렉션 (DeleteHistories) 필드 추가

5. 피드백 사항 반영 및 추가 사항
   1. 기능 요구사항으로 제시된 것에 대해 모두 단위 테스트 검증 실시
      - Question, Answers, Answer, DeleteHistories test 추가
   2. Question 객체에서 NsUser 객체를 직접 참조하는 문제 해결
      - 식별자를 통해 간접 참조하는 방식 (id)
   3. Question 객체 관련
      1. 답변 삭제 가능 여부 판단과 답변 삭제 요청이 서로 다른 곳에서 진행되는 문제
         - question 객체 안에서 전부 해결
         - question.delete() 시 answer 역시 제거되도록 하면 로직이 전부 question으로 들어오게 됨.
      2. getter 사용보다 메시지를 보내는 방식
      3. 일급 컬렉션(answers) 활용
   4. DeleteHistoryService 객체 내부에 상태를 두어서는 안 된다.
      - DeleteHistories 상태변수 제거, 인자로 처리
   5. QnAService 내부 saveAll에서 이전 기록들이 중복해서 들어가는 문제
      - toHistory function을 Question, Answer에 각각 넣음
      - Answers에 toHistories로 각 Answer의 toHistory 결과들 가져옴
      - Question 객체의 toQuestionAndAnswersHistories 함수에서 DeleteHistories 결과 가져옴
      - 이 결과를 saveAll의 인자로 넣음
   6. 객체 내 상태 변수의 수를 줄이기 위해 객체 분리
      - AnswerContents, QuestionContents, BaseEntity
   7. 기타 필요 없는 메서드(setter 등) 정리 및 상태 변수 정리

## [2단계] - LMS (My)
1. 요구사항 정리 
   1) 과정(Course)는 기수 단위로 운영, 여러 개의 강의를 가질 수 있음
   2) 강의는 시작일과 종료일을 가진다.
   3) 강의는 커버 이미지 정보를 가진다.
   4) 이미지 크기는 1MB 이하여야 한다.
   5) 이미지 타입은 GIF, JPG(JPEG), PNG, SVG를 허용한다.
   6) 이미지 width, height은 각각 300px, 200px 이상이어야 하며 width, height 비율은 3:2여야 한다.
   7) 강의는 무료/유료 강의로 나뉜다.
   8) 무료 강의는 최대 수강 인원 제한이 없다.
   9) 유료 강의는 강의 최대 수강 인원을 초과할 수 없다.
   10) 유료 강의는 수강생의 결제 금액과 수강료가 일치할 때 수강 신청이 가능하다.
   11) 강의 상태는 준비 중, 모집 중, 종료 3가지로 나뉜다.
   12) 강의 수강 신청은 강의 상태가 모집 중일 때만 가능하다.
   13) 유료 강의는 결제가 된 것으로 가정하고 과정을 구현한다.
   14) 결제 정보는 Payments 모듈에 두며 해당 정보는 Payment 객체에 담아 반환한다.

2. 객체
   - Course: 과정
   - Sessions: 강의들 (일급 컬렉션)
   - Session: 강의
   - FreeSession: 무료 강의
   - PaidSession: 유료 강의
   - Period: 시작일, 종료일 관련 VO
   - ImageCover: 강의 커버 이미지 관련 VO
   - SessionStatus: 강의 상태 관련 Enum
   - MaxCapacity: 강의 최대 수용 인원 관련 원시 객체
   - TuitionFee: 강의 수강료 관련 원시 객체
   - ImageType: 이미지 확장자 관련 Enum
   - EnrollService: 강의 등록 관련 서비스 클래스
   - SessionRepository: 강의 조회 관련 인터페이스
   - Payment: 결제 정보 관련 객체

3. 구현 방향
   - Course -> Sessions -> Session
   - Session <-(extends) FreeSession, PaidSession
   - Session -> Period, ImageCover, SessionStatus
   - PaidSession -> MaxCapacity, TuitionFee
   - ImageCover -> ImageType
   - EnrollService -> SessionRepository -> Session
   - Payment

4. 학습
   - 객체를 파라미터로 넘기면 해당 객체에 의존하게 되기 때문에, 변경 시 전파된다. 간접 참조 (ex. List<Long>)
     - 도메인에게 무엇을 시킬 때? (객체 간 협력 + 설계 상 책임 분리 시 도메인 클래스 넘길 수 있음)
   - 추상 클래스 사용
   - 엔티티, 도메인 객체 같은 경우, 각 요청마다 새 인스턴스가 생성되므로 공유하지 않음
   - 스프링 빈에 등록된 객체의 경우 싱글톤이라서 공유
   - VO: 값 객체로 의미 있는 값 여러 개를 감싸는 객체 (ex. Period)
   - 원시 객체(Primitive VO): 단 하나의 원시 값을 감싸는 객체 (ex. TuitionFee), VO의 하위 개념
   - 일급 컬렉션: 하나의 컬렉션을 감싸는 객체, VO의 한 종류
   - 정적 팩토리 메서드의 경우 직관적인 인터페이스 제공하는 데 좋음
   - 생성자에는 불변 필수 값만 담는다. 가변 등 X (ex. PaidSession)
   - 객체는 자기 자신과 관련된 검증만 책임 진다. (ex. validateAccomodation)
   - 검증 시점은 행위 발생 직전에 한다. (ex. validEnrollCondition)

## [3단계] - DB 적용 (my)
1. 요구사항 정리
   1) 2단계에서 구현한 도메인 구조를 유지하면서 DB와 매핑
   2) 객체 구조 유지를 위해 여러 번 DB 쿼리 실행 가능
   3) Payment는 매핑 고려하지 않아도 됨
   4) CRUD

2. 테이블 매핑 : 의존관계 제거/약화에 집중
   1) Session
      - ImageCover 의존성 제거 (ImageCover가 '다'쪽이기도 하고, 종속이어서 뺌)
      - Participants, Participant 의존성 약화 (ex. enroll 시 외부 파라미터를 통한 주입 가능)
      - 의존성 제거, 약화에 집중
      - 원시 객체
   2) ImageCover
      - session_id를 통한 간접 참조 (직접 참조 X) -> repository를 통해 조회하면 됨
      - Session : ImageCover = 1 : 다로 외래키 가짐
   3) Participants
      - 일급 컬렉션 추가
   4) Participant (수강생)
      - 강의, 유저 간 다대다를 해결하기 위한 매핑 테이블

3. CRUD
   - JDBC를 이용한 쿼리 조회
   - 테이블 간 직접 참조를 했다면, 매우 복잡했겠으나 의존성 제거/약화로 조회가 매우 간단해짐
   - 필요한 것은 서비스 단에서 repository 조회를 통해 필요한 것을 주입해주면 끝 (enroll method, 여러 번 DB 쿼리 실행 가능 부분)

4. 학습
   - 상속 구조를 유지하고, 굳이 Session 하나로 통합하지 않아도 됨. 이렇게 되면 기존 코드에 여러 수정이 가해져야 함 + 복잡해짐
     -> DB insert 시 instanceOf를 통한 해결, read 시 Enum type을 통해 분기 처리
     -> 상속 구조를 유지하되, read 시 타입 구별을 위해 SessionType Enum 추가
   - 의존관계 제거/약화하는 것
     -> 로직 작성 시 매우 편해짐
     -> enroll 시 repository에서 조회한 것을 파라미터에 넣어주기만 하면 됨 (외부에서 주입)
   - 생성자 선언 관련 (this를 통한 생성자 추가, 미리 다른 값 설정 등)
     -> 생성자에 무엇을 넣느냐?
   - 원시 객체/VO를 테이블로 구성하는 것 (매핑)
   - 값이 존재하면, 유효해야 한다.
     -> 생성하는 단계에서 유효성 통과시키는 것
   - JDBC template