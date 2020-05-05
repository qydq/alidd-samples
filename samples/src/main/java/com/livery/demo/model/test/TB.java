package com.livery.demo.model.test;


import com.sunsta.bear.presenter.BaseNetApi;

public class TB {

    public static void main(String[] args) {
//        FixedApi fixedApi1 = (FixedApi) A.response(FixedApi.class);
//        FixedApi fixedApi2 = (FixedApi) A.getInstance().getTTT(FixedApi.class);
//        FixedApi fixedApi3 = A.response(FixedApi.class).getTTT();
//        FixedApi fixedApi4 = (FixedApi) A.<FixedApi>getInstance().natureT;
//        FixedApi fixedApi5 = (FixedApi) A.getInstance().genericInstance(FixedApi.class);

//        FixedApi fixedApi6 = A.<FixedApi>genericInstance().genericInstance(FixedApi.class);
        BaseNetApi fixedApi7 = A.<BaseNetApi>genericInstance().getGenericClass();

        if (fixedApi7 != null) {
            System.out.println(fixedApi7.toString());
        } else {
            System.out.println("this is null");
        }
    }
}
