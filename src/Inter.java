import java.util.*;
public class Inter {

    private LinkedList<Token> infixExpr;
    private Map<String, Double> variables = new HashMap<>();
    private Map<String, MyLinkedList> LLvariables = new HashMap<>();

    public int iterator;
    public Token cur;
    public boolean result_comparison_bool;
    public MyLinkedList list;
    public String LinkedList_var;

    private int operationPriority(Token op) {
        return switch (op.token) {
            case "(" -> 0;
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> -1;
        };
    }

    private double execute(Token op, double first, double second) {
        return switch (op.token) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "*" -> first * second;
            case "/" -> first / second;
            default -> -1;
        };
    }

    public Inter(LinkedList<Token> infixExpr) {
        this.infixExpr = infixExpr;
        cur = infixExpr.get(0);
        iterator = 0;
        result_comparison_bool = false;
        run();
    }

    private void run() {
        for (; iterator < infixExpr.size(); iterator++) {
            cur = infixExpr.get(iterator);
            switch (cur.type) {
                case "ASSIGNMENT OPERATOR" -> value_inter("ENDLINE");
                case "IF OPERATION" -> if_inter();
                case "WHILE" -> interpret_while();
                case "DO" -> interpret_do_while();
                case "FOR" -> interpret_for();
                case "PRINT" -> interpret_print();
                case "LL" -> interpret_LL();
                case "POINT" -> interpret_LL_operator();
                case "PRTLIST" -> interpret_LL_printlist();
            }
        }
    }

    private void interpret_LL_printlist() {
        cur = infixExpr.get(iterator);
            if (cur.type == "PRTLIST") {
                iterator++;
                cur = infixExpr.get(iterator);
                if (cur.type == "L_BRACKET"){
                    iterator++;
                    cur = infixExpr.get(iterator);
                    if (cur.type == "VAR") {
                        System.out.print("List " + cur.token + ": ");
                        LLvariables.get(cur.token).printLinkList();
                        iterator++;
                    }
                }
            }
    }

    private void interpret_LL_operator() {
        int index_var = iterator - 1;
        LinkedList_var = infixExpr.get(index_var).token;
        int operator_index = iterator + 1;
        cur = infixExpr.get(operator_index);
        switch (cur.type){
            case "LLadd" -> interpret_LL_add();
            case "LLremove" -> interpret_LL_remove();
            case "LLget" -> interpret_LL_get();
            case "LLsize" -> interpret_LL_size();
        }
    }

    private void interpret_LL_size() {
        iterator++;
        cur = infixExpr.get(iterator);
        if (infixExpr.get(iterator + 1).type == "L_BRACKET"){
            System.out.println("Size = " + LLvariables.get(LinkedList_var).size() + "(Linked List " + LinkedList_var + ")");
        }
    }

    private void interpret_LL_get() {
        iterator++;
        cur = infixExpr.get(iterator);
        if (cur.type == "LLget"){
            iterator+=2;
            cur = infixExpr.get(iterator);
            if (cur.type == "DIGIT"){
                System.out.println("element by index: " + cur.token + " = " + LLvariables.get(LinkedList_var).get(Integer.parseInt(cur.token)));
            }
        }
        iterator++;
        cur = infixExpr.get(iterator);
    }

    private void interpret_LL_remove() {
        iterator++;
        cur = infixExpr.get(iterator);
        if (cur.type == "LLremove"){
            iterator+=2;
            cur = infixExpr.get(iterator);
            if (cur.type == "DIGIT"){
                LLvariables.get(LinkedList_var).remove(Integer.parseInt(cur.token));
            }
        }
        iterator++;
        cur = infixExpr.get(iterator);
    }

    private void interpret_LL_add() {
        iterator++;
        cur = infixExpr.get(iterator);
            if (cur.type == "LLadd"){
                iterator++;
                cur = infixExpr.get(iterator);
                if (cur.type == "L_BRACKET"){
                    iterator++;
                    cur = infixExpr.get(iterator);
                    if (cur.type == "DIGIT"){
                        LLvariables.get(LinkedList_var).add(cur.token);
                        iterator++;
                        cur = infixExpr.get(iterator);
                        if (cur.type == "R_BRACKET"){
                            iterator++;
                            cur = infixExpr.get(iterator);
                        }
                    }
                }
            }
        }

    private void interpret_LL() {
        cur = infixExpr.get(iterator);
        while (cur.type != "ENDLINE") {
            if ("LL".equals(cur.type)) {
                list = new MyLinkedList<>();
                System.out.println("empty LinkedList created");
            }
            if ("VAR".equals(cur.type)) {
                LLvariables.put(cur.token, list);
            }
            iterator++;
            cur = infixExpr.get(iterator);
        }
    }

    private void interpret_print() {
        iterator++;
        cur = infixExpr.get(iterator);
        if ("L_BRACKET".equals(cur.type)) {
            Token c = infixExpr.get(iterator + 1);
            switch (c.type) {
                case "DIGIT" -> System.out.println(Double.parseDouble(c.token));
                case "VAR" -> System.out.println(c.token + " = " + variables.get(c.token));
            }
            iterator += 2;
        } else {
            System.out.println(variables);
        }
        iterator++;
    }



    private void interpret_do_while() {
        iterator += 2;
        cur = infixExpr.get(iterator);
        int start_iteration = iterator;

        do {
            value_inter("WHILE");
            condition_check();
            iterator = start_iteration;
            cur = infixExpr.get(iterator);
        } while (result_comparison_bool);
        while (!"ENDLINE".equals(cur.type)) {
            iterator++;
            cur = infixExpr.get(iterator);
        }
    }

    private void interpret_while() {
        int start_iteration = iterator;
        condition_check();
        while (result_comparison_bool) {
            iterator++;
            value_inter("ENDLINE");
            iterator = start_iteration;
            cur = infixExpr.get(iterator);
            condition_check();
        }

        while (!"ENDLINE".equals(cur.type)) {
            iterator++;
            cur = infixExpr.get(iterator);
        }
    }

    public void if_inter(){
        condition_check();
        if (!result_comparison_bool) {
            while (!"ENDLINE".equals(cur.type)) {
                if ("ELSE".equals(cur.type)) {
                    break;
                }
                iterator++;
                cur = infixExpr.get(iterator);
            }
        } else {
            iterator++;
            value_inter("ELSE");
            while (!"ENDLINE".equals(cur.type)) {
                iterator++;
                cur = infixExpr.get(iterator);
            }
        }
    }
    private void interpret_for() {
        iterator += 3;
        cur = infixExpr.get(iterator);
        value_inter("DIV");
        iterator--;
        int condition = iterator;
        condition_check();
        int indexAfterFor = iterator + 1;
        while (result_comparison_bool) {
            while (!"R_BRACKET".equals(cur.type)) {
                iterator++;
                cur = infixExpr.get(iterator);
            }
            iterator += 2;
            cur = infixExpr.get(iterator);
            value_inter("ENDLINE");

            iterator = indexAfterFor;
            cur = infixExpr.get(iterator);
            value_inter("R_BRACKET");

            iterator = condition;
            condition_check();
        }

        while (!"ENDLINE".equals(cur.type)) {
            iterator++;
            cur = infixExpr.get(iterator);
        }
    }
    public void value_inter(String trans){
        int indexVar = iterator - 1;
        int startExpr = iterator + 1;
        while (!trans.equals(cur.type)) {
            iterator++;
            cur = infixExpr.get(iterator);
        }

        double rez = calc(toPostfix(infixExpr, startExpr, iterator));
        variables.put(infixExpr.get(indexVar).token, rez);
    }
    public void condition_check(){
        int first_argument_index = iterator + 2;
        int second_argument_index = iterator + 4;
        int comparison_op_index = iterator + 3;
        Token s = infixExpr.get(second_argument_index);
        double first_argument = variables.get(infixExpr.get(first_argument_index).token);
        double second_argument = switch (s.type){
            case "DIGIT" -> Double.parseDouble(s.token);
            case "VAR" -> variables.get(s.token);
            default -> 0.0;
        };
        iterator = iterator + 6;
        cur = infixExpr.get(iterator);
        result_comparison_bool = compare(infixExpr.get(comparison_op_index), first_argument, second_argument);
    }

    private boolean compare(Token comp, double first_argument, double second_argument) {
        return switch (comp.token){
            case ">" -> Double.compare(first_argument, second_argument) == 1;
            case "<" -> Double.compare(first_argument, second_argument) == -1;
            case "~" -> Double.compare(first_argument, second_argument) == 0;
            default -> throw new IllegalArgumentException("Illegal value: " + comp.token);
        };
    }
    private LinkedList<Token> toPostfix(LinkedList<Token> infixExpr, int start, int end) {
        //	???????????????? ????????????, ???????????????????? ?????????????????????? ????????????
        LinkedList<Token> postfixExpr = new LinkedList<>();
        //	?????????????????????????? ??????????, ???????????????????? ?????????????????? ?? ???????? ????????????????
        Stack<Token> stack = new Stack<>();

        //	???????????????????? ????????????
        for (int i = start; i < end; i++) {
            //	?????????????? ????????????
            Token c = infixExpr.get(i);
            //	???????? ?????????????? - ??????????
            if (c.type == "DIGIT" || c.type == "VAR") {
                postfixExpr.add(c);
            } else if (c.type == "L_BC") { //	???????? ?????????????????????????? ????????????
                //	?????????????? ???? ?? ????????
                stack.push(c);
            } else if (c.type == "R_BC") {//	???????? ?????????????????????? ????????????
                //	?????????????? ?? ???????????????? ???????????? ???? ?????????? ?????? ???????????? ???? ?????????????????????? ????????????
                while (stack.size() > 0 && stack.peek().type != "L_BC")
                    postfixExpr.add(stack.pop());
                //	?????????????? ?????????????????????????? ???????????? ???? ??????????
                stack.pop();
            } else if (c.type == "OPERATOR") { //	??????????????????, ???????????????????? ???? ???????????? ?? ???????????? ????????????????????
                Token op = c;
                //	?????????????? ?? ???????????????? ???????????? ?????? ?????????????????? ???? ??????????, ?????????????? ?????????? ?????????????? ??????????????????
                while (stack.size() > 0 && (operationPriority(stack.peek()) >= operationPriority(op)))
                    postfixExpr.add(stack.pop());
                //	?????????????? ?? ???????? ????????????????
                stack.push(c);
            }
        }
        //	?????????????? ?????? ???????????????????? ?????????????????? ???? ?????????? ?? ???????????????? ????????????
        postfixExpr.addAll(stack);

        //	???????????????????? ?????????????????? ?? ?????????????????????? ????????????
        return postfixExpr;
    }

    private double calc(LinkedList<Token> postfixExpr) {
        //	???????? ?????? ???????????????? ??????????
        Stack<Double> locals = new Stack<>();
        //	?????????????? ????????????????
        int counter = 0;

        //	???????????????? ???? ????????????
        for (int i = 0; i < postfixExpr.size(); i++) {
            //	?????????????? ????????????
            Token c = postfixExpr.get(i);

            //	???????? ???????????? ??????????
            if (c.type == "DIGIT") {
                String number = c.token;
                locals.push(Double.parseDouble(number));
            } else if (c.type == "VAR") {
                locals.push(variables.get(c.token));
            } else if (c.type == "OPERATOR") { //	???????? ???????????? ???????? ?? ???????????? ????????????????????
                //	???????????????????? ???????????????? ????????????????
                counter++;

                //	???????????????? ???????????????? ???? ?????????? ?? ???????????????? ??????????????
                double second = locals.size() > 0 ? locals.pop() : 0,
                        first = locals.size() > 0 ? locals.pop() : 0;

                //	???????????????? ?????????????????? ???????????????? ?? ?????????????? ?? ????????
                locals.push(execute(c, first, second));
            }
        }

        //	???? ???????????????????? ?????????? ???????????????????? ?????????????????? ???? ??????????
        return locals.pop();
    }

    public Map<String, Double> getVariables() {
        return variables;
    }
    public void getLLvariables() {
    }
}