import java.util.LinkedList;

public class Parser {
    int iterator = 0;
    public LinkedList<Token> tokens = new LinkedList<Token>();
    public int len;
    Parser(LinkedList<Token> tokens, int len) {
        this.tokens = tokens;
        this.len = len;
    }

    public void lang() throws ParseExc {
        for (int i = 0;i < len; i++ ){
            expr_();
        }
    }
    public void expr_() throws ParseExc {
        Token currentToken = tokens.get(iterator);
        if (currentToken.type == "WHILE"){
            while_do(currentToken);
            currentToken = tokens.get(iterator);
        }
        if (currentToken.type == "DO_WHILE"){
            do_while(currentToken);
            currentToken = tokens.get(iterator);
        }
        if (currentToken.type == "IF OPERATION"){
            try {
                IF(currentToken);
            }
            catch (ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
            iterator++;
            currentToken = tokens.get(iterator);
            try{
                LB(currentToken);
            }
            catch (ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
            iterator++;
            currentToken = tokens.get(iterator);
            condition(currentToken);
            currentToken = tokens.get(iterator);
            try{
                RB(currentToken);
            }
            catch (ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
            iterator++;
            currentToken = tokens.get(iterator);
        }
        try {
            var__(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(currentToken, "VAR");
        }
        iterator++;
        currentToken = tokens.get(iterator);
        try{
            assign_op(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        iterator++;
        currentToken = tokens.get(iterator);
        while ((currentToken.type != "ENDLINE") & (currentToken.type != "R_BRACKET") & (currentToken.type != "L_BRACKET") & (currentToken.type != "WHILE")){
            expr_val(currentToken);
            iterator++;
            currentToken = tokens.get(iterator);
        }
        if (currentToken.type == "WHILE"){
            try {
                WHILE(currentToken);
            }
            catch(ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
            iterator++;
            currentToken = tokens.get(iterator);
            if (currentToken.type == "L_BRACKET"){
                try{
                    LB(currentToken);
                }
                catch (ParseExc ex){
                    ex.getMsg(ex.token, ex.expected);
                }
                iterator++;
                currentToken = tokens.get(iterator);
                condition(currentToken);
                currentToken = tokens.get(iterator);
                try{
                    RB(currentToken);
                }
                catch (ParseExc ex){
                    ex.getMsg(ex.token, ex.expected);
                }
                iterator++;
            }
        }
        try{
            currentToken = tokens.get(iterator);
            ENDLINE(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        iterator++;
    }

    public void IF(Token currentToken) throws ParseExc {
        if (currentToken.type != "IF OPERATION")
            throw new ParseExc(currentToken, "IF OPERATION");
    }

    public void var__(Token currentToken) throws ParseExc {
            if (currentToken.type != "VAR")
                throw new ParseExc(currentToken, "VAR");
    }
    public void assign_op(Token currentToken) throws ParseExc {
             if (currentToken.type != "ASSIGNMENT OPERATOR")
             {
                throw new ParseExc(currentToken, "ASSIGNMENT OPERATOR");
             }
    }
    public void expr_val(Token currentToken) throws ParseExc {
        if ((currentToken.type == "VAR") | (currentToken.type == "DIGIT"))
            value(currentToken);
        else
            try {
                OP_VALUE(currentToken);
            }
            catch(ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }

    }
    public void value(Token currentToken) throws ParseExc {
        if (currentToken.type == "VAR")
            var__(currentToken);
        else
            try{
                digit__(currentToken);
            }
            catch (ParseExc ex){
                ex.getMsg(ex.token, ex.expected);
            }
    }
    public void digit__(Token currentToken) throws ParseExc{
        if (currentToken.type != "DIGIT")
            throw new ParseExc(currentToken, "DIGIT");
    }
    public void OP_VALUE(Token currentToken) throws ParseExc{
        if (currentToken.type != "OPERATOR")
            throw new ParseExc(currentToken, "OPERATOR");
    }
    public void while_do(Token currentToken) throws ParseExc {
        WHILE(currentToken);
        iterator++;
        currentToken = tokens.get(iterator);
        try{
            LB(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        iterator++;
        currentToken = tokens.get(iterator);
        condition(currentToken);
        currentToken = tokens.get(iterator);
        try {
            RB(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        iterator++;
    }
    public void LB(Token currentToken) throws ParseExc {
        if (currentToken.type != "L_BRACKET")
            throw new ParseExc(currentToken, "L_BRACKET");
    }
    public void RB(Token currentToken) throws ParseExc{
        if (currentToken.type != "R_BRACKET")
            throw new ParseExc(currentToken, "R_BRAACKET");
    }
    public void condition(Token currentToken) throws ParseExc {
        try {
            var__(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        iterator++;
        currentToken = tokens.get(iterator);
        try {
            COMPARISON_OP(currentToken);
        }
        catch (ParseExc ex){
            ex.getMsg(ex.token, ex.expected);
        }
        iterator++;
        currentToken = tokens.get(iterator);
        expr_val(currentToken);
        iterator++;
    }
    public void COMPARISON_OP (Token currentToken) throws ParseExc{
        if (currentToken.type != "COMPARISON_OP")
            throw new ParseExc(currentToken, "COMPARISON_OP");
    }
    public void WHILE(Token currentToken) throws ParseExc{
        if (currentToken.type != "WHILE")
            throw new ParseExc(currentToken, "WHILE");
    }
    public void ENDLINE(Token currentToken) throws ParseExc{
        if (currentToken.type != "ENDLINE")
            throw new ParseExc(currentToken, "ENDLINE");
    }
    public void do_while(Token currentToken) throws ParseExc{
        DO(currentToken);
        iterator++;
    }
    public void DO(Token currentToken) throws ParseExc{
        if (currentToken.type != "DO_WHILE")
            throw new ParseExc(currentToken, "DO");
    }
}
//lang->expr+
//expr->(if|while_do|do_while) (WHIlE LB condition RB)? ASSIGN_OP (expr_val)+ ENDLINE
//if->IF LB condition RB
//while_do-> WHILE LB condition RB
//do_while->DO
//condition-> VAR COMPARISON_OP (expr_val)+
//expr_val->value | OP_VALUE
//value-> VAR | DIGIT
