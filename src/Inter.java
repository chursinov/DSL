import java.util.*;

public class Inter {

    private LinkedList<Token> infixExpr;
    private Map<String, Double> variables = new HashMap<>();

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
        run();
    }

    private void run() {
        int temp = 0;
        int indexVar = 0;
        for (int i = 0; i < infixExpr.size(); i++) {
            if (infixExpr.get(i).type == "ASSIGNMENT OPERATOR") {
                indexVar = i - 1;
                temp = i + 1;
            }
            if (infixExpr.get(i).type == "ENDLINE") {
                double rez = calc(toPostfix(infixExpr, temp, i));
                variables.put(infixExpr.get(indexVar).type, rez);
            }
        }
    }

    private LinkedList<Token> toPostfix(LinkedList<Token> infixExpr, int start, int end) {
        //	Выходная строка, содержащая постфиксную запись
        LinkedList<Token> postfixExpr = new LinkedList<>();
        //	Инициализация стека, содержащий операторы в виде символов
        Stack<Token> stack = new Stack<>();

        //	Перебираем строку
        for (int i = start; i < end; i++) {
            //	Текущий символ
            Token c = infixExpr.get(i);

            //	Если симовол - цифра
            if (c.type == "DIGIT" || c.type == "VAR") {
                postfixExpr.add(c);
            } else if (c.type == "L_BC") { //	Если открывающаяся скобка
                //	Заносим её в стек
                stack.push(c);
            } else if (c.type == "R_BC") {//	Если закрывающая скобка
                //	Заносим в выходную строку из стека всё вплоть до открывающей скобки
                while (stack.size() > 0 && stack.peek().type != "L_BC")
                    postfixExpr.add(stack.pop());
                //	Удаляем открывающуюся скобку из стека
                stack.pop();
            } else if (c.type == "OPERATOR") { //	Проверяем, содержится ли символ в списке операторов
                Token op = c;
                //	Заносим в выходную строку все операторы из стека, имеющие более высокий приоритет
                while (stack.size() > 0 && (operationPriority(stack.peek()) >= operationPriority(op)))
                    postfixExpr.add(stack.pop());
                //	Заносим в стек оператор
                stack.push(c);
            }
        }
        //	Заносим все оставшиеся операторы из стека в выходную строку
        postfixExpr.addAll(stack);

        //	Возвращаем выражение в постфиксной записи
        return postfixExpr;
    }

    private double calc(LinkedList<Token> postfixExpr) {
        //	Стек для хранения чисел
        Stack<Double> locals = new Stack<>();
        //	Счётчик действий
        int counter = 0;

        //	Проходим по строке
        for (int i = 0; i < postfixExpr.size(); i++) {
            //	Текущий символ
            Token c = postfixExpr.get(i);

            //	Если символ число
            if (c.type == "DIGIT") {
                String number = c.type;
                locals.push(Double.parseDouble(number));
            } else if (c.type == "VAR") {
                locals.push(variables.get(c.type));
            } else if (c.type == "OPERATOR") { //	Если символ есть в списке операторов
                //	Прибавляем значение счётчику
                counter += 1;

                //	Получаем значения из стека в обратном порядке
                double second = locals.size() > 0 ? locals.pop() : 0,
                        first = locals.size() > 0 ? locals.pop() : 0;

                //	Получаем результат операции и заносим в стек
                locals.push(execute(c, first, second));
            }
        }

        //	По завершению цикла возвращаем результат из стека
        return locals.pop();
    }

    public Map<String, Double> getVariables() {
        return variables;
    }
}