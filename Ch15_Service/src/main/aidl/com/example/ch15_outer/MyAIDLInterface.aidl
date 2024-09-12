// MyAIDLInterface.aidl
package com.example.ch15_outer;

/*
자바로 작성한 인터페이스
인터페이스이기 때문에 추상 함수만 선언함
외부와 통신하는데 필요한 함수만 정의되어 있음.
하지만 인터페이스로 추상 함수이기 때문에 어디선가 이 함수의 구체적인 로직을 구현해야 함
-> 서비스 컴포넌트가 구현
AIDL 은 bindService() 를 이용함
*/

interface MyAIDLInterface {
     void funA(String data);
     int funB();
}