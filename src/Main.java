import structures.Atominomicon;
import java.io.IOException;
import javax.script.ScriptException;

public class Main {

        public static Atominomicon atominomicon;

        public static void main(String[] args) throws IOException, ScriptException {

            try {
                atominomicon = new Atominomicon();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Does this work?");
            System.out.println(WebService.getFormula("ethyl methyl ether"));
            System.out.println(WebService.getName("HCl"));
        }
}
