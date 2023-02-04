package com.medicalretrieval.utils;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class Page4Navigator<T> {
    Page<T> pageFromJPA;

    //分页超链数 如[5,6,7,8,9] 即分页超链数为5
    int navigatePages;

//    总页面数
    int totalPages;

//    当前处于第几页
    int number;
//    总共有多少条数据
    long totalElements;
//    一页最多有多少条数据
    int size;
//    当前页有多少条数据
    int numberOfElements;
    List<T> content;
//    是否有内容
    boolean isHasContent;

//    是否是页首
    boolean first;

//    是否是末页
    boolean last;
//    是否有上一页
    boolean isHasNext;
//    是否有下一页
    boolean isHasPrevious;
//    前端展示的超链数如[5,6,7,8,9]
    int[] navigatePageNums;

    public Page4Navigator(){}

    public Page4Navigator(Page<T> pageFromJPA,int navigatePages){
        this.pageFromJPA = pageFromJPA;
        this.navigatePages = navigatePages;
        totalPages=pageFromJPA.getTotalPages();
        number=pageFromJPA.getNumber();
        totalElements = pageFromJPA.getTotalElements();
        size = pageFromJPA.getSize();
        numberOfElements = pageFromJPA.getNumberOfElements();
        content = pageFromJPA.getContent();
        isHasContent = pageFromJPA.hasContent();
        first = pageFromJPA.isFirst();
        last = pageFromJPA.isLast();
        isHasNext = pageFromJPA.hasNext();
        isHasPrevious = pageFromJPA.hasPrevious();
        //计算超链数
        calcNavigatepageNums();
    }

    private void calcNavigatepageNums() {
        //用于保存要显示的超链
        int navigatepageNums[];
        //获得总页数
        int totalPages = getTotalPages();
        //当前处于哪一页
        int num = getNumber();
        //当总页数小于或等于导航超链页码数时
        if (totalPages <= navigatePages) {
            navigatepageNums = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                //超链展示从第一页开始
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时  6  3
            //超链长度即为一页超链数长度
            navigatepageNums = new int[navigatePages]; // 3
            int startNum = num - navigatePages / 2; //1 -3/2  0 or 2 -3/2  1
            int endNum = num + navigatePages / 2; // 1 +3/2  2  2+3/2  3

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    //在第一页，所以要显示的超链为[1,2,....navigatePages]
                    //此处为先赋值后++ 假设navigatePages=3 即显示的超链为[1,2,3]
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > totalPages) {
                endNum = totalPages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
        this.navigatePageNums = navigatepageNums;
    }
}
