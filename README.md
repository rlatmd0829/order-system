# 상품 주문 프로그램

## [ 요구사항 ]

1. '주문' 또는 '종료' 를 선택할 수 있도록 입력을 제공한다.
2. '주문' 요청 시 상품 카테고리를 보여주고 선택할 수 있도록 한다.
3. 선택한 카테고리에 해당하는 상품을 모두 보여준다.
    - 데이터를 저장하는 방식은 자유
4. 상품은 상품번호, 상품명, 가격, 재고수량 정보를 가진다.
5. 한 번에 여러 상품을 주문할 수 있다.
6. 주문 시 상품번호와 수량을 입력받는다.
7. 여러 상품을 주문할 수 있도록 위 6번 과정을 반복한다.
8. 상품번호에 빈 값이 입력될 경우 '주문' 또는 '종료' 할 수 있도록 입력을 제공한다.
9. '주문' 요청 시 재고 파악한 후 다음 단계로 넘어간다.
    - 재고가 없는 경우 - SoldOutException 발생
    - 재고가 있는 경우 - 결제가 완료된 것으로 판단
10. 결제가 완료된 경우 주문 내역을 보여준다.
- 총 주문 금액이 5만 원 미만인 경우 배송료 3,000원이 추가된다.
- 주문 내역에는 상품번호, 상품명, 수량, 총 주문 금액, 배송비 정보, 총 결제 금액(배송비 포함), 결제 날짜가 포함된다.
1. 제공하지 않는 값이나 상품을 입력하는 경우에는 적절한 예외를 반환한다.
2. 1번부터의 과정이 반복된다.

## [ 테스트 ]

- 단위 테스트를 작성한다.
- 멀티 스레드 요청으로 SoldOutException 예외가 발생하는지 단위 테스트가 작성되어야 한다.
