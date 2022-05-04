import java.util.regex.*;
import java.util.*;
public class Main {

    public static void main(String[] args) throws ParseExc {
        String[] exp = {
                "b = 6;"};
        int len = exp.length;
        TokenType lex = new TokenType();
        LinkedList<Token> tokens = new LinkedList<Token>();
        String str_1 = "";
        for (int j = 0; j < exp.length;j++){
            for (int i = 0; i < exp[j].length(); i++) {
                if (exp[j].toCharArray()[i] == ' ') {
                    continue;
                }
                str_1 += exp[j].toCharArray()[i];
                String str_2 = " ";
                if (i < exp[j].length() - 1) {
                    str_2 = str_1 + exp[j].toCharArray()[i + 1];
                }
                for (String key : lex.regexp.keySet()) {
                    Pattern p = Pattern.compile(lex.regexp.get(key));
                    Matcher m_1 = p.matcher(str_1);
                    Matcher m_2 = p.matcher(str_2);
                    if (m_1.find() && !m_2.find()) {
                        tokens.add(new Token(key.toString(), str_1));
                        str_1 = "";
                    }
                }
            }
        }
        for (Token t : tokens) {
            System.out.println("Type of Regular Exp.: "+t.type + "; Token: " + t.token);
        }
        Parser par = new Parser(tokens, len);
        par.lang();
        Inter inter = new Inter(tokens);
        inter.getVariables();
    }
}