package com.ohgiraffers.section01.xml;

import com.ohgiraffers.common.SearchCriteria;

import java.util.*;

public class Application {

    public static void main(String[] args) {

        /* 필기. 마이바티스 동적 SQL 확인하기 */
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("============= 마이바티스 동적 SQL =============");
            System.out.println("1. if 확인하기");
            System.out.println("2. choose(when, otherwise) 확인하기");
            System.out.println("3. foreach 확인하기");
            System.out.println("4. trim(where, set) 확인하기");
            System.out.println("9. 종료하기");
            System.out.print("메뉴를 선택해주세요 : ");
            int no = sc.nextInt();

            switch (no) {
                case 1 : ifSubMenu(); break;
                case 2 : chooseSubMenu(); break;
                case 3 : foreachSubMenu(); break;
                case 4 : trimSubMenu(); break;
                case 9 :
                    System.out.println("시스템을 종료합니다.."); return;
            }

        } while (true);

    }

    private static void trimSubMenu() {

        Scanner sc = new Scanner(System.in);
        MenuService menuService = new MenuService();

        do{

            System.out.println("=========trim========");
            System.out.println("1. 검색 조건이 있는 경우 메뉴코드로 조회, 단 없으면 전체 조회");
            System.out.println("2. 메뉴 혹은 카테고리코드로 검색, 단 메뉴와 카테고리 둘 다 일치하는 경우도 검색하며, 검색 조건이 없는경우 전체 조회");
            System.out.println("3. 업데이트하기");
            System.out.println("9. 이전 메뉴로 돌아가기");
            System.out.println("메뉴 번호를 입력해 주세요 : ");
            int no = sc.nextInt();

            switch (no) {

                case 1 : menuService.searchMenuBYCodeOrSearchAll(inputAllorOne()); break;
                case 2 : menuService.searchMenuByNameOrCategory(inputSearchCriteriaMap());break;
                case 3 : menuService.modifyMenu(inputChangeInfo()); break;
                case 9 : return;

            }



        } while (true);




    }

    private static Map<String, Object> inputChangeInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("변경할 메뉴 코드를 입력해주세요 : ");
        int code = sc.nextInt();
        System.out.println("변경할 메뉴 이름을 입력해주세요 : ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("변경 할 카테고리 코드를 입력해 주세요 : ");
        int categoryCode = sc.nextInt();
        System.out.println("판매 여부를 결정해주세요 (Y,N) : ");
        sc.nextLine();
        String orderableStatus = sc.nextLine();

        Map<String,Object> criteria = new HashMap<>();
        criteria.put("code",code);
        criteria.put("name",name);
        criteria.put("categoryCode",categoryCode);
        criteria.put("orderableStatus",orderableStatus);

        return criteria;


    }

    private static Map<String,Object> inputSearchCriteriaMap() {

        Scanner sc = new Scanner(System.in);
        System.out.println("검색할 조건을 입력하세요(category or name or both or null) : ");
        String condition = sc.nextLine();

        Map<String, Object> criteria = new HashMap<>();

        if("category".equals(condition)) {
            System.out.println("검색할 카테고리를 입력하세요 : ");
            int categoryValue = sc.nextInt();

            criteria.put("categoryValue", categoryValue );

        } else if ("name".equals(condition)) {
            System.out.println("검색할 이름을 입력하세요 : ");
            String nameValue = sc.nextLine();

            criteria.put("nameValue", nameValue);
        } else if ("both".equals(condition)) {
            System.out.println("검색할 이름을 입력하세요 : ");
            String nameValue = sc.nextLine();
            System.out.println("검색할 카테고리 코드를 입력하세요 : ");
            int categoryValue = sc.nextInt();

            criteria.put("nameValue", nameValue);
            criteria.put("categoryValue", categoryValue);
        }
        return criteria;
    }

    private static SearchCriteria inputAllorOne() {

        Scanner sc = new Scanner(System.in);
        System.out.println("검색조건을 입력하시겠습니까? (예 or 아니오) : ");
        boolean hasSearchValue = "예".equals(sc.nextLine()) ? true : false;

        SearchCriteria searchCriteria = new SearchCriteria();
        if(hasSearchValue) {
            System.out.println("검색할 메뉴 코드를 입력해주세요 : ");
            String code = sc.nextLine();
            searchCriteria.setCondition("menuCode");
            searchCriteria.setValue(code);
        }
        return searchCriteria;
    }

    private static void foreachSubMenu() {

        Scanner sc = new Scanner(System.in);
        MenuService menuService = new MenuService(); // 서비스에 호출준비

        do {
            System.out.println("=========foreach 서브 메뉴 ========");
            System.out.println("1. 랜덤한 메뉴 5개 조회하기");
            System.out.println("9. 이전메뉴로 돌아가기");
            System.out.println("메뉴 번호를 입력하세요 : ");
            int no = sc.nextInt();

            switch (no) {
                case 1 : menuService.searchMenuByRandomMenuCode(createRandomMenuCodeList()); break;
                case 9 : return;
            }

        }  while (true);



    }

    private static List<Integer> createRandomMenuCodeList() {  // 5개의 정수 리스트를 만들기 때문

        Set<Integer> set = new HashSet<>(); // set 은 중복 x
        while (set.size() < 5 ) {
                // 현재 내 데이터베이스 내의 메뉴 번호는 34번 까지 이다.
            int temp = ((int)(Math.random() * 34)) + 1;
            set.add(temp);  // set


        }

        List<Integer> list = new ArrayList<>(set); // 리스트에 담기
        Collections.sort(list); // sort 라는 정렬 메소드로 랜덤 list 값 정렬

        return list;

    }

    private static void chooseSubMenu() {

        Scanner sc = new Scanner(System.in);
        MenuService menuService = new MenuService();

        do {

            System.out.println("============choose 서브 메뉴==============");
            System.out.println("1. 카테고리 상위 분류별 메뉴 보여주기(식사, 음료, 디저트)");
            System.out.println("9. 이전 메뉴로 돌아가기");
            System.out.print("메뉴 번호를 입력해주세요 : ");
            int no = sc.nextInt();

            switch (no) {
                case 1 : menuService.searchMenuBySupCategory(inputSupCategory());break;
                case 9 : return;
            }

        } while (true);

    }

    private static SearchCriteria inputSupCategory() {

        Scanner sc = new Scanner(System.in);
        System.out.print("상위 분류를 입력해주세요(식사, 음료, 디저트) : ");
        String value = sc.nextLine();

        return new SearchCriteria("category", value);

    }

    private static void ifSubMenu() {

        Scanner sc = new Scanner(System.in);
        MenuService menuService = new MenuService();

        do {
            System.out.println("===========if 서브 메뉴=============");
            System.out.println("1. 원하는 금액대에 적합한 추천 메뉴 목록 보여주기");
            System.out.println("2. 메뉴 이름 or 카테고리명으로 검색하여 메뉴 목록 보여주기");
            System.out.println("9. 이전 메뉴로");
            System.out.print("원하는 메뉴를 입력해주세요 : ");
            int no = sc.nextInt();

            switch (no) {
                case 1 : menuService.selectMenuByPrice(inputPrice()); break;
                case 2 : menuService.searchMenu(inputSearchCriteria());break;
                case 9 : return;
            }

        } while(true);


        // 도아님은 안됨.

    }

    private static SearchCriteria inputSearchCriteria() {

        Scanner sc = new Scanner(System.in);
        System.out.print("검색 기준을 입력해주세요(name or category) : ");
        String condition = sc.nextLine();
        System.out.print("검색어를 입력해주세요 : ");
        String value = sc.nextLine();

        return new SearchCriteria(condition, value);

    }

    private static int inputPrice() {

        Scanner sc = new Scanner(System.in);
        System.out.print("검색하실 가격의 최대 금액을 입력해주세요 : ");
        int price = sc.nextInt();

        return price;
    }

}