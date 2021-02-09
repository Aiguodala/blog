import java.util.Scanner;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-05 19:38
 **/
public class Test {
    
    @org.junit.jupiter.api.Test 
    public void test01() {
        int n = 4;
        int x = 1;
        for(int i = 1;i <= n; i++)
        {
            for(x = 1;x <= n-i; x++)
                System.out.print(" ");//空格
            for(x = 1;x < i; x++)
                System.out.print(x);//顺序数字
            for(int y = i;y >= 1; y--)
                System.out.print(y);//倒叙数字
            System.out.println();
        }
    }

}
