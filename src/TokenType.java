import java.lang.reflect.Array;
import java.util.*;
public class TokenType {
    static Map<String,String> regexp = new HashMap<String,String>();
    public TokenType(){
        regexp.put("WHILE","^[W][H][I][L][E]$");
        regexp.put("COMPARISON_OP", "^[>|<|~]$");
        regexp.put("L_BRACKET","^\\($");
        regexp.put("R_BRACKET","^\\)$");
        regexp.put("L_BRACE","^\\{$");
        regexp.put("R_BRACE","^\\}$");
        regexp.put("IF OPERATION", "^[I][F]$");
        regexp.put("VAR", "^[a-z]+$");
        regexp.put("DIGIT", "^0|[1-9][0-9]*$");
        regexp.put("OPERATOR", "^[-|+|/|*|%]$");
        regexp.put("ASSIGNMENT OPERATOR", "^=$");
        regexp.put("ENDLINE", "^\\;$");
        regexp.put("DO_WHILE", "^[D][O]$");
        regexp.put("DIV","^,$");
        regexp.put("PRINT","^[P][R][I][N][T]$");
        regexp.put("FOR","^[F][O][R]$");
        regexp.put("ELSE", "^[E][L][S][E]$");
        regexp.put("LL", "^LINKEDLIST$");
        regexp.put("POINT", "^\\.$");
        regexp.put("LLadd", "^[A][D][D]$");
        regexp.put("LLremove", "^REMOVE$");
        regexp.put("LLget", "^GET$");
        regexp.put("PRTLIST", "^PRTLIST$");
        regexp.put("LLsize", "^SIZE$");
    }
}