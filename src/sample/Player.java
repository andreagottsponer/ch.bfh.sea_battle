package sample;

/**
 * Created by 1-7353 on 28.04.2015.
 */
public class Player {
    protected String name;
    protected int type;
    protected Field field;

    public Player(String name, Field field) {
        this.name = name;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
