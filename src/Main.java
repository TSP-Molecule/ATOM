import javax.script.ScriptException;
import java.io.IOException;

public class Main {

        public static AtomicDictionary atomicDictionary;

        public static void main(String[] args) throws IOException, ScriptException {

            atomicDictionary = new AtomicDictionary();

            System.out.println("Does this work?");
            System.out.println(WebService.getFormula("ethyl methyl ether"));
            System.out.println(WebService.getName("HCl"));
        }
}
