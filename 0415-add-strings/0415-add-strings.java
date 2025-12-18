class Solution {
    public String addStrings(String num1, String num2) {
        return add(num1,num1.length()-1, num2, num2.length()-1, 0);

    }
    private String add(String n1, int i, String n2, int j, int carry){
        if(i<0 && j<0 && carry==0)  return "";
        int sum = carry;
        if(i>=0)    sum+=n1.charAt(i--) - '0';
        
        if(j>=0)    sum+=n2.charAt(j--) - '0';
        return add(n1,i,n2,j,sum/10) + (sum%10);
    }
}