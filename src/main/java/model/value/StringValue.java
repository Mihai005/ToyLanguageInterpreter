package model.value;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue{
    private String value;
    public StringValue(String v)
    {
        this.value = v;
    }
    @Override
    public IType getType() {
        return new StringType();
    }
    public String getValue()
    {
        return this.value;
    }
    public boolean equals(IValue v)
    {
        return v.getType().equals(new StringType()) && ((StringValue) v).getValue().equals(value);
    }
    public String toString()
    {
        return this.value;
    }
}
