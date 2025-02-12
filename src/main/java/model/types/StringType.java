package model.types;

import model.value.IValue;
import model.value.StringValue;

public class StringType implements IType{

    @Override
    public boolean equals(IType obj) {
        return obj instanceof StringType;
    }

    public String toString()
    {
        return "String";
    }

    @Override
    public IValue defaultValue()
    {
        return new StringValue("");
    }
}
