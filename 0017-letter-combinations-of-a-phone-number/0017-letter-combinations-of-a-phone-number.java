class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if(digits.length()==0){
            return result;
        }
        String[] mapping ={
            "",
            "",
            "abc",
            "def",
            "ghi",
            "jkl",
            "mno",
            "pqrs",
            "tuv",
            "wxyz"
        };
        backtrack(digits, 0, new StringBuilder(), result, mapping);
        return result;

    }
    private void backtrack(
        String digits,
        int index,
        StringBuilder current,
        List<String> result,
        String[] mapping
    ){
        if(index == digits.length()){
            result.add(current.toString());
            return;
        }
        int digit = digits.charAt(index) - '0';
        String letters = mapping[digit];
        for(int i = 0;i<letters.length();i++){
            current.append(letters.charAt(i));
            backtrack(digits, index+1, current, result, mapping);
            current.deleteCharAt(current.length()-1);
        }

    }
}