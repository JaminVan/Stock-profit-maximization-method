import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.MIN_VALUE;

/**
 * 题目描述：
 * 已知股票价格相对前一天的变化，求解股票利润最大化的买进和卖出日；
 * 思考：
 *   （算法导论p38-p42）：
 *    为了获取股票的最大利润，求解最大子数组；
 * 方法：分治算法；
 *   分治策略的思路：分治技术意味着将子数组划分为两个规模尽量相等的子数组。
 *   找到子数组的中央位置，考虑求解两个子数组A[low....mid]和 A[mid+1...high]，则 A[low,..high]的任何连续子数组A[i,..j]所处的位置必然是以下三种情况之一
 *     a.完全位于子数组A[low....mid]中，即low<=i<=j<=mid.
 *     b.完全位于子数组 A[mid+1...high]中，因此mid<i<=j<=high.
 *     c.跨越了中点，因此low<=i<=mid<j<=high。
 *     运行时间
 */
public class FindMaximumSubArray {
    public static void main(String[] args) {

        /*股票浮动数组*/
        int [] list={13,-3, -25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};

        /*此段为测量跨越中点最大数组代码*/
       /* Map<String, Integer> map4 = new HashMap<String, Integer>();
         map4= Find_Max_Crossing_Subarray(list ,0,7,15);
        System.out.print("数组的范围是"+map4.get("max_left")+"--"+map4.get("max_right")+"\n");
        printList(list,map4.get("max_left"),map4.get("max_right"));
        System.out.print("数组的和为"+map4.get("sum")+"-----");*/


        Map<String, Integer> map5 = new HashMap<String, Integer>();
        map5= Find_Maximum_SubArray(list ,0,15);
        printList(list,map5.get("max_left"),map5.get("max_right"));
        System.out.print("数组的范围是"+map5.get("max_left")+"--"+map5.get("max_right")+"\n");
        System.out.print("数组的和为"+map5.get("sum"));
    }

    /**
     * 整体求解最大子数组的递归算法
     * @param list 价格变化数组
     * @param low   低位
     * @param high  高位
     * @return  利润最大化数组
     */
    public static Map<String, Integer> Find_Maximum_SubArray(int[] list,int low,int high){
        Map<String, Integer> map = new HashMap<String, Integer>();
         int mid;
        if(low==high) {  //特殊情况，仅有一个元素
            map.put("max_left", low);
            map.put("max_right", high);
            map.put("sum", list[low]);
            return map;
        }
        else{
            mid=(low+high)/2;
            Map<String, Integer> map1 = new HashMap<String, Integer>();
            Map<String, Integer> map2 = new HashMap<String, Integer>();
            Map<String, Integer> map3 = new HashMap<String, Integer>();

            map1=Find_Maximum_SubArray(list,low,mid);
            map2=Find_Maximum_SubArray(list,mid+1,high);
            map3=Find_Max_Crossing_Subarray(list,low,mid,high);
            if(map1.get("sum")>=map2.get("sum")&&map1.get("sum")>=map3.get("sum")){
                return map1;
            }
            else if(map2.get("sum")>=map1.get("sum")&&map2.get("sum")>=map3.get("sum")){
                return map2;
            }
            else
                return  map3;
        }
    }

    /**
     * 寻找跨越中点的最大子数组
     * @param list 输入数组
     * @param low  低位下标
     * @param mid  中间值
     * @param high  高位下标
     * @return      返回一个下标元祖划定跨越中点的最大子数组的边界，并返回最大子数组中值的和。
     */
    public static Map<String, Integer> Find_Max_Crossing_Subarray(int[] list, int low, int mid, int high){
        Map<String, Integer> map = new HashMap<String, Integer>();

        int left_sum=0;
        int right_sum=0;
        int max_left=MIN_VALUE;
        int max_right=MIN_VALUE;
        int sumleft=0;
        int sumright=0;
        for(int i=mid;i>=low;i--)
        {
            sumleft=sumleft+list[i];
            if(sumleft>left_sum)
            {
                left_sum=sumleft;
                max_left=i;
            }
        }
        for(int j=mid+1;j<=high;j++)
        {
            sumright=sumright+list[j];
            if(sumright>right_sum) {
                right_sum = sumright;
                max_right = j;
            }
        }
        map.put("max_left",max_left);
        map.put("max_right",max_right);
        map.put("sum",left_sum+right_sum);
        return map;
    }

    /**
     * 打印最大子数组包含的值
     * @param list  数组
     * @param low  最大子数组的起始位置
     * @param high  最大子数组的终止位置
     */
    public static void printList(int[] list ,int low,int high){
        for(int i=low;i<=high;i++) {
            System.out.print(list[i]+"---"+"\n");
        }
    }
}
