import structures.Atominomicon;

import java.io.IOException;

public class Main {

        public static Atominomicon atominomicon;

        public static void main(String[] args) {

            try {
                atominomicon = new Atominomicon();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Does this work?");
        }
}
