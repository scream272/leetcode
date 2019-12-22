 /**
  * leetcode-394 字符串解码
  * 这个题直接用暴力方法解决挺简单的，但是有一个坑就是给出的样例中：
  *      数值都是小于10的重复次数，很容易觉得只要读一位就可以转成数值
  * 其实隐藏的逻辑是：遇到字母，只要压栈就好了，但是遇到数值一定要读全
  * （暴力解法真的是基础，只要能暴力解决问题，后面的优化总能慢慢想出来，但是要是思路一开始就不清晰或者错误比如接雨水那个题，就很麻烦）
  * */
    public String decodeString(String s){
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length();i++) {
            Character c = s.charAt(i);
            if (!c.equals(']')) {
                stack.push(c);
            } else{
                Character tmp = stack.pop();
                String s1 = "";
                while (!tmp.equals('[')) {
                    s1 += tmp;
                    tmp = stack.pop();
                }
                String reverse = new StringBuffer(s1).reverse().toString();
                String num = "";

                Character ch = stack.peek();
                while(ch <= 57){
                        num += ch;
                        stack.pop();
                        if(stack.isEmpty())
                            break;
                        else
                            ch = stack.peek();
                }
                String before = new StringBuffer(num).reverse().toString();
                int repeat = Integer.parseInt(before);
                for (int j = 0; j < repeat; j++) {
                    for (int k = 0; k < reverse.length(); k++) {
                        stack.push(reverse.charAt(k));
                    }
                }
            }
        }
        String after = "";
        while(!stack.isEmpty()){
            after += stack.pop();
        }
        System.out.println(after);
        String reverse = new StringBuffer(after).reverse().toString();
        System.out.println("输出反转后的字符串：" + reverse);
        return reverse;
    }

