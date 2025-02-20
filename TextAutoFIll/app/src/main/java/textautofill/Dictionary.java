package textautofill;

import java.util.ArrayList;

interface Dictionary {

    public void add(String key);

    public boolean contains(String key);

    public boolean containsPrefix(String pre);

    public ArrayList<String> suggest();

    public ArrayList<String> traverse();

}
