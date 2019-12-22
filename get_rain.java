import java.util.Stack;

/**
 * 接雨水：这个题一开始总是在想一个序列应该怎么样会有水，应该怎么计算序列里面的水，把问题
 *         变成了一个处理子序列的题，就很麻烦，总有没想到的情况。
 * 第一种解法：针对每一列，它应该储蓄多少水，最后将每一列的值求和即是解。
 * 第二种解法：针对解法一中的每一列，我们每次都要算一次它左边的最大值和右边的最大值
 *             我们可以做一个优化，用两个数组，max_left[i]代表第i列左边最高的墙的高度
 *             max_right[i]代表第i列右边最高的墙的高度（不包括自身）
 *             对于max_left我们可以这么求：
 *                  max_left[i] = Math.max(max_left[i - 1], height[i - 1]);
 *             从左往右扫过去，当前的这一列的左边最高，等于它左边一个的左边最高，和它左边那一列中更高的那个
 *             对于max_right:
 *                  max_right[i] = Math.max(max_right[i+1], height[i + 1]);
 *             从右往左扫，当前这一列右边的最高，等于它右边一个的右边最高，和它右边那一列中更高的那个
 * 第三种解法：针对动态规划，一般可以通过考虑“必要的存储单元”是多少，用滚动数组，或者双指针的方法进行空间压缩
 *              我们这里只是因为把每列的左边最大值和右边最大值单独拿出来算了个数组，需要用的时候再去查表
 *              但是完全可以只用双指针来解决这个问题：max_left[i]和max_right[i]数组中的元素我们只用一次
 *
 * 第四种解法：利用栈，用法还挺有意思的
 * */

public class get_rain {
    public static void main(String[] args) {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int[] height_1 = {4, 3, 2};
        int[] height_2 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        get_rain ts = new get_rain();
        System.out.println(ts.trap_1(height));
    }

    public int trap_1(int[] height) {
        int sum = 0;
        //最两端的列不用考虑，因为一定不会有水。所以下标从 1 到 length - 2
        for (int i = 1; i < height.length - 1; i++) {
            int max_left = 0;
            //找出左边最高
            for (int j = i - 1; j >= 0; j--) {
                if (height[j] > max_left) {
                    max_left = height[j];
                }
            }
            int max_right = 0;
            //找出右边最高
            for (int j = i + 1; j < height.length; j++) {
                if (height[j] > max_right) {
                    max_right = height[j];
                }
            }
            //找出两端较小的
            int min = Math.min(max_left, max_right);
            //只有较小的一段大于当前列的高度才会有水，其他情况不会有水
            if (min > height[i]) {
                sum = sum + (min - height[i]);
            }
        }
        return sum;
    }
    public int trap_2(int[] height) {
        int sum = 0;
        int[] max_left = new int[height.length];
        int[] max_right = new int[height.length];

        for (int i = 1; i < height.length - 1; i++) {
            max_left[i] = Math.max(max_left[i - 1], height[i - 1]);
        }
        for (int i = height.length - 2; i >= 0; i--) {
            max_right[i] = Math.max(max_right[i + 1], height[i + 1]);
        }
        for (int i = 1; i < height.length - 1; i++) {
            int min = Math.min(max_left[i], max_right[i]);
            if (min > height[i]) {
                sum += (min - height[i]);
            }
        }
        return sum;
    }
    public int trap_3_1(int[] height) {
        //先改造下max_left
        int sum = 0;
        int max_left = 0;
        int[] max_right = new int[height.length];
        for(int i = height.length - 2; i >= 0; i--){
            max_right[i] = Math.max(max_right[i + 1], height[i + 1]);
        }
        for (int i = 1; i < height.length - 1; i++){
            max_left = Math.max(max_left, height[i - 1]);
            int min = Math.min(max_left, max_right[i]);
            if(min > height[i]){
                sum = sum + (min - height[i]);
            }
        }
        return sum;
    }
    public int trap_3_2(int[] height) {
        int sum = 0;
        int max_left = 0;
        int max_right = 0;
        /**
         * 我们成功将 max_left 数组去掉了。但是会发现我们不能同时把 max_right 的数组去掉，因为最后的 for 循环是从左到右遍历的，而 max_right 的更新是从右向左的。
         * 所以这里要用到两个指针，left 和 right，从两个方向去遍历。
         * 那么什么时候从左到右，什么时候从右到左呢？根据下边的代码的更新规则，我们可以知道
         *          max_left = Math.max(max_left, height[i - 1]);
         * height [ left - 1] 是可能成为 max_left 的变量， 同理，height [ right + 1 ] 是可能成为 right_max 的变量。
         * 只要保证 height [ left - 1 ] < height [ right + 1 ] ，那么 max_left 就一定小于 max_right。
         * 因为 max_left 是由 height [ left - 1] 更新过来的，而 height [ left - 1 ] 是小于 height [ right + 1] 的，而 height [ right + 1 ] 会更新 max_right，所以间接的得出 max_left 一定小于 max_right。
         * 反之，我们就从右到左更。
         * */
        int left = 1;
        int right = height.length - 2;
        for(int i = 1; i < height.length - 1; i++){
            //每轮循环处理一列
            if(height[left - 1] < height[right + 1]){ //从左到右更新
                max_left = Math.max(max_left, height[left - 1]);
                int min = max_left;
                if(min > height[left]){ //这里的height[left]其实是这一轮循环正在处理的一列
                    sum = sum + (min - height[left]);
                }
                left++;
            }else { //从右到左更新
                max_right = Math.max(max_right, height[right + 1]);
                int min = max_right;
                if(min > height[right]){
                    sum += sum + (min - height[right]);
                }
                right--;
            }
        }
        return sum;
    }
    public int trap_4(int[] height){
        int sum = 0;
        Stack<Integer> stack = new Stack<>();
        int current = 0;
        while(current < height.length){
            while (!stack.empty() && height[current] > height[stack.peek()]){
                int h = height[stack.peek()];
                stack.pop();
                if (stack.empty()){
                    break;
                }
                int distance = current - stack.peek() - 1;
                int min = Math.min(height[stack.peek()], height[current]);
                sum = sum + distance * (min - h);
            }
            stack.push(current);
            current++;
        }
        return sum;
    }
}