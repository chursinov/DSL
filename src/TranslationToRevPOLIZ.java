import java.util.LinkedList;
import java.util.Stack;

public class TranslationToRevPOLIZ {
    public LinkedList<Token> tokens = new LinkedList<Token>();
    public Stack operationStack = new Stack();
    public Stack DigitsStack = new Stack<>();
    int iterator = 0;
    public LinkedList<String> result = new LinkedList<>();
    TranslationToRevPOLIZ(LinkedList<Token> tokens){
        this.tokens = tokens;
    }
    public void Translate(){
        while (iterator < tokens.size()){
            Token currentToken = tokens.get(iterator);
            iterator++;
            if ((currentToken.type == "DIGIT") | (currentToken.type == "VAR")){
                result.add(currentToken.token);
            }
            if ((currentToken.type == "OPERATOR") | (currentToken.type == "ASSIGNMENT OPERATOR")){
                operationStack.push(currentToken.token);
            }
        }
        while (operationStack.empty() != true){
            result.add(operationStack.pop().toString());
            result.add(operationStack.pop().toString());
        }
    }
    public void Output(){
        System.out.println(result);
    }
}

//stack
//
//public void operation(){
//
//    switch(curr_token)
//
//        case '+'
//            stack <<
//        }
//
//        public void print()
//System.out.println(take_from_stack)